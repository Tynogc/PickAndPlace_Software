package gui.subMenu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.Semaphore;

import javax.swing.JFileChooser;

import components.Footprint;
import components.FootprintPainter;
import components.FootprintPainterToShow;
import components.kiCad.KiCadFileImport;
import componetStorage.Reel;
import main.PicLoader;
import menu.Button;

public class FootprintSetup extends gui.MoveMenu{

	private Reel reel;
	
	private FootprintPainterToShow fpp;
	private Semaphore sema;
	private File fileChooserReturn;
	private boolean fileChooserPending;
	
	private int footprintSize = 180;
	
	public FootprintSetup(int x, int y, Reel r) {
		super(x, y, PicLoader.pic.getImage("res/ima/mbe/m400x600.png"), "Setup Footprint");
		reel = r;
		sema = new Semaphore(1);
		
		Button loadFp = new Button(230,100,"res/ima/cli/B") {
			
			@Override
			protected void uppdate() {}
			
			@Override
			protected void isFocused() {}
			
			@Override
			protected void isClicked() {
				if(fileChooserPending)
					return;
				final JFileChooser jfc = new JFileChooser();
				new Thread(){
					public void run() {
						try {
							sema.acquire();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						fileChooserPending = true;
						int i = jfc.showDialog(null, "Select Footprint");
						if(i == JFileChooser.APPROVE_OPTION)
							fileChooserReturn = jfc.getSelectedFile();
						if(i == JFileChooser.CANCEL_OPTION)
							fileChooserPending = false;
						sema.release();
					};
				}.start();
			}
		};
		loadFp.setText("Load frome File");
		loadFp.setTextColor(Button.gray);
		add(loadFp);
		
		if(reel.fp!=null)
			fpp = new FootprintPainterToShow(reel.fp, footprintSize, true, Color.red,
					true, FootprintPainter.ROTATION_DOWN);
	}

	@Override
	protected void paintSecond(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(10, 40, 200, 200);
		if(fpp != null)
			g.drawImage(fpp.buffer, 110-fpp.middleX, 140-fpp.middleY, null);
		
	}

	@Override
	protected boolean close() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void uppdateIntern() {
		if(sema.availablePermits()>0){
			if(fileChooserPending){
				fileChooserPending = false;
				String name = fileChooserReturn.getName();
				if(name.endsWith("kicad_mod")){//KiCad Footprint
					Footprint f = new KiCadFileImport(fileChooserReturn).footprint;
					reel.fp = f;
					fpp = new FootprintPainterToShow(reel.fp,footprintSize,true,Color.red,
							true, FootprintPainter.ROTATION_LEFT);
				}else{
					debug.Debug.println("* File withe name "+name+" can't be resolved to Type!",
							debug.Debug.COMERR);
				}
				//TODO add pther import formats here!
			}
		}
		
	}
}
