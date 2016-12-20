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
	private static final int CURRENT_REEL = StoredComponent.CONTAINER_REEL;
	private static final int CURRENT_TUBE = StoredComponent.CONTAINER_TUBE;
	private static final int CURRENT_TRAY = StoredComponent.CONTAINER_TRAY;
	
	public ReelSetup(int x, int y, StoredComponent sc) { 
		super(x, y, PicLoader.pic.getImage("res/ima/mbe/m500x200.png"),"Set Container for Part");
		storedC = sc;
		
		c_reel = new Container(10, 60);
		c_tube = new Container(10, 60);
		c_tray = new Container(10, 60);
		
		b_reel = new Button(30,40,"res/ima/cli/Gsk") {
			@Override
			protected void uppdate() {}
			@Override
			protected void isFocused() {}
			@Override
			protected void isClicked() {
				toggledType(CURRENT_REEL);
			}
		};
		add(b_reel);
		b_tube = new Button(130,40,"res/ima/cli/Gsk") {
			@Override
			protected void uppdate() {}
			@Override
			protected void isFocused() {}
			@Override
			protected void isClicked() {
				toggledType(CURRENT_TUBE);
			}
		};
		add(b_tube);
		b_tray = new Button(230,40,"res/ima/cli/Gsk") {
			@Override
			protected void uppdate() {}
			@Override
			protected void isFocused() {}
			@Override
			protected void isClicked() {
				toggledType(CURRENT_TRAY);
			}
		};
		add(b_tray);
		b_tray.setText("TRAY");
		b_tube.setText("TUBE");
		b_reel.setText("REEL");
		
		toggledType(0);//TODO
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
		current = t;
		c_reel.setVisible(t==CURRENT_REEL);
		c_tube.setVisible(t==CURRENT_TUBE);
		c_tray.setVisible(t==CURRENT_TRAY);
		b_reel.setDisabled(t==CURRENT_REEL);
		b_tray.setDisabled(t==CURRENT_TRAY);
		b_tube.setDisabled(t==CURRENT_TUBE);
	}

}
