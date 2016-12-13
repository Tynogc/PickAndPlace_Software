package gui.subMenu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFileChooser;

import components.FootprintPainter;
import components.FootprintPainterToShow;
import componetStorage.StoredComponent;
import main.PicLoader;
import menu.Button;
import menu.DataFiled;
import menu.DropDownButton;
import menu.DropDownMenu;
import menu.TextEnterButton;

public class ComponentSetup extends gui.MoveMenu{
	
	private StoredComponent reel;
	private FootprintPainterToShow fpp;
	
	private DropDownMenu orientation;
	
	private BufferedImage[] imas;
	
	private TextEnterButton name;
	private TextEnterButton hight;
	
	private boolean valOK;

	public ComponentSetup(int x, int y, StoredComponent s) {
		super(x, y, PicLoader.pic.getImage("res/ima/mbe/m400x600.png"), "Setup Component");
		reel = s;
		
		Button loadFp = new Button(230,100,"res/ima/cli/B") {
			
			@Override
			protected void uppdate() {}
			
			@Override
			protected void isFocused() {}
			
			@Override
			protected void isClicked() {
				gui.GuiControle.addMenu(new FootprintSetup(xPos+30, yPos+50, reel));
			}
		};
		loadFp.setText("Load Footprint");
		loadFp.setTextColor(Button.gray);
		add(loadFp);
		
		Button loadTool = new Button(230,200,"res/ima/cli/G") {
			
			@Override
			protected void uppdate() {}
			
			@Override
			protected void isFocused() {}
			
			@Override
			protected void isClicked() {
				//TODO...
			}
		};
		loadTool.setText("SelectTool");
		add(loadTool);
		
		DataFiled toolNumber = new DataFiled(230,170,80,20,Color.blue) {
			@Override
			protected void uppdate() {}
			@Override
			protected void isClicked() {}
			@Override
			public void longTermUpdate() {
				setText("Tool "+reel.toolToUse);
				super.longTermUpdate();
			}
		};
		add(toolNumber);
		toolNumber.setTextColor(Color.white);
		
		orientation = new DropDownMenu(70,260,100) {
			@Override
			protected void changed(int i) {
				opneFpp();
			}
		};
		orientation.addSubButton(new DropDownButton(100, 20, "0 Degree"), StoredComponent.ORIENTATION_0_DEGREE);
		orientation.addSubButton(new DropDownButton(100, 20, "90 Degree"), StoredComponent.ORIENTATION_90_DEGREE);
		orientation.addSubButton(new DropDownButton(100, 20, "180 Degree"), StoredComponent.ORIENTATION_180_DEGREE);
		orientation.addSubButton(new DropDownButton(100, 20, "270 Degree"), StoredComponent.ORIENTATION_270_DEGREE);
	
		imas = new BufferedImage[]{
			PicLoader.pic.getImage("res/ima/gui/par/feederDirection.png"),
			PicLoader.pic.getImage("res/ima/gui/par/1.png"),
			PicLoader.pic.getImage("res/ima/gui/par/2.png"),
			PicLoader.pic.getImage("res/ima/gui/par/3.png"),
			PicLoader.pic.getImage("res/ima/gui/par/tool.png"),
			PicLoader.pic.getImage("res/ima/gui/par/4.png")
		};
		valOK = true;
		
		name = new TextEnterButton(230,70,80,20,Color.white,main.SeyprisMain.getKL()) {
			@Override
			protected void textEntered(String text) {
				
			}
		};
		add(name);
		
		hight = new TextEnterButton(70,340,100,20,Color.white,main.SeyprisMain.getKL()) {
			@Override
			protected void textEntered(String text) {
				checkValue(this);
			}
		};
		add(hight);
		
		
		add(orientation);
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
		if(reel.fp != null)
			fpp = new FootprintPainterToShow(reel.fp, 200,true,Color.red,true,orientation.getState());
	}

	@Override
	protected void paintSecond(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(10, 40, 200, 200);
		g.drawImage(imas[0], 10, 140, null);
		if(fpp != null)
			g.drawImage(fpp.buffer, 10, 40, null);
		
		g.drawImage(imas[1], 10, 260, null);
		g.drawImage(imas[2], 320, 40, null);
		g.drawImage(imas[4], 320, 140, null);
		g.drawImage(imas[5], 10, 420, null);
		g.drawImage(imas[3], 10, 340, null);
		
		g.setFont(main.Fonts.font12);
		g.setColor(Color.white);
		g.drawString("Part Position in Reel", 20, 257);
		g.drawString("Component Hight", 20, 337);
		g.drawString("Reel/Try/Tube - Setup", 20, 417);
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
	
	private void checkValue(TextEnterButton t){
		String s = t.getText();
		try {
			double d = Double.parseDouble(s);
		} catch (Exception e) {
			t.setTextColor(Color.red);
			valOK = false;
			return;
		}
		t.setTextColor(Color.black);
		valOK = true;
	}
	
}
