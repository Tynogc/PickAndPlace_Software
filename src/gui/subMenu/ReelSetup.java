package gui.subMenu;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import componetStorage.StoredComponent;
import main.PicLoader;
import menu.Button;
import gui.MoveMenu;

public class ReelSetup extends MoveMenu{

	private final StoredComponent storedC;
	
	private Button b_reel;
	private Button b_tube;
	private Button b_tray;
	
	private int current;
	
	public ReelSetup(int x, int y, StoredComponent sc) {
		super(x, y, PicLoader.pic.getImage("res/ima/mbe/m500x200.png"),"Set Container for Part");
		storedC = sc;
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

}
