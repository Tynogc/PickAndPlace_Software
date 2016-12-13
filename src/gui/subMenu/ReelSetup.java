package gui.subMenu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import components.FootprintPainter;
import components.FootprintPainterToShow;
import componetStorage.StoredComponent;
import main.PicLoader;

public class ReelSetup extends gui.MoveMenu{
	
	private StoredComponent reel;
	private FootprintPainterToShow fpp;
	
	//private Dro

	public ReelSetup(int x, int y, BufferedImage i, String t) {
		super(x, y, PicLoader.pic.getImage("res/ima/mbe/m400x600.png"), "Setup Component");
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void longTermUpdate() {
		super.longTermUpdate();
		if(reel.fp != null){
			if(fpp != null){
				if(fpp.name.compareTo(reel.fp.name)==0)
					return;
			}
			opneFpp();
		}
	}
	
	private void opneFpp(){
		fpp = new FootprintPainterToShow(reel.fp, 10,true,Color.red,true,0);
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
		return false;
	}

	@Override
	protected void uppdateIntern() {
		// TODO Auto-generated method stub
		
	}

	
}
