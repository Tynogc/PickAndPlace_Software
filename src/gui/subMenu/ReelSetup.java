package gui.subMenu;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import componetStorage.StoredComponent;
import main.PicLoader;
import menu.Button;
import menu.Container;
import gui.MoveMenu;

public class ReelSetup extends MoveMenu{

	private final StoredComponent storedC;
	
	private Button b_reel;
	private Button b_tube;
	private Button b_tray;
	private Container c_reel;
	private Container c_tube;
	private Container c_tray;
	
	private int current;
	
	public ReelSetup(int x, int y, StoredComponent sc) {
		super(x, y, PicLoader.pic.getImage("res/ima/mbe/m500x200.png"),"Set Container for Part");
		storedC = sc;
		
		c_reel = new Container(10, 60);
		c_tube = new Container(10, 60);
		c_tray = new Container(10, 60);
		
	}

	@Override
	protected void paintSecond(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean close() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void uppdateIntern() {
		// TODO Auto-generated method stub
		
	}
	
	private void toggledType(int t){
		c_reel.setVisible(t==0);
		c_tube.setVisible(t==1);
		c_tray.setVisible(t==2);
		b_reel.setDisabled(t==0);
		b_tray.setDisabled(t==1);
		b_tube.setDisabled(t==2);
	}

}
