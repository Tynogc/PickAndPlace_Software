package gui.subMenu;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.PicLoader;

public class ReelSetup extends gui.MoveMenu{

	public ReelSetup(int x, int y, BufferedImage i, String t) {
		super(x, y, PicLoader.pic.getImage("res/ima/mbe/m400x600.png"), "Setup Reel");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void paintSecond(Graphics g) {
		// TODO Auto-generated method stub
		
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
