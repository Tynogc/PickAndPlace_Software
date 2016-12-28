package gui.subMenu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import componetStorage.StoredComponent;
import main.PicLoader;
import main.SeyprisMain;
import menu.Button;
import menu.CheckBox;
import menu.Container;
import menu.TextEnterButton;
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
	
	private TextEnterButton reelMiddleX;
	private TextEnterButton reelMiddleY;
	private TextEnterButton reelWholes;
	private CheckBox reelHalfWhole;
	
	private TextEnterButton tubeMiddleX;
	private TextEnterButton tubeMiddleY;
	
	private TextEnterButton trayMiddleX;
	private TextEnterButton trayMiddleY;
	private TextEnterButton trayRasterX;
	private TextEnterButton trayRasterY;
	
	private BufferedImage[] imas;
	
	public ReelSetup(int x, int y, StoredComponent sc) { 
		super(x, y, PicLoader.pic.getImage("res/ima/mbe/m500x300.png"),"Set Container for Part");
		storedC = sc;
		
		c_reel = new Container(10, 70);
		c_tube = new Container(10, 70);
		c_tray = new Container(10, 70);
		
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
		
		if(storedC.reel == null)storedC.reel = new componetStorage.Reel();
		if(storedC.tray == null)storedC.tray = new componetStorage.Tray();
		if(storedC.tube == null)storedC.tube = new componetStorage.Tube();
		
		imas = new BufferedImage[]{
				PicLoader.pic.getImage("res/ima/gui/par/setUpReel.png"),
				PicLoader.pic.getImage("res/ima/gui/par/setUpTube.png"),
				PicLoader.pic.getImage("res/ima/gui/par/setUpTray.png")
		};
		///////////////////////////REEL
		reelMiddleX = new TextEnterButton(244,12,100,20,Color.white,SeyprisMain.getKL()) {
			@Override
			protected void textEntered(String text) {
				storedC.reel.middleX = checkValue(this);
			}
		};
		c_reel.addInContainer(reelMiddleX);
		reelMiddleX.setText(""+sc.reel.middleX);
		reelMiddleY = new TextEnterButton(244,54,100,20,Color.white,SeyprisMain.getKL()) {
			@Override
			protected void textEntered(String text) {
				storedC.reel.middleY = checkValue(this);
			}
		};
		c_reel.addInContainer(reelMiddleY);
		reelMiddleY.setText(""+sc.reel.middleY);
		reelWholes = new TextEnterButton(244,96,100,20,Color.white,SeyprisMain.getKL()) {
			@Override
			protected void textEntered(String text) {
				checkWholes();
			}
		};
		c_reel.addInContainer(reelWholes);
		reelWholes.setText(""+sc.reel.wholesPerComp/2);
		reelHalfWhole = new CheckBox(244,126,"res/ima/cli/cbx/CB",100) {
			@Override
			public void changed(boolean b) {
				checkWholes();
			}
		};
		c_reel.addInContainer(reelHalfWhole);
		reelHalfWhole.setText("+ Half Whole");
		reelHalfWhole.setState(sc.reel.wholesPerComp%2 == 1);
		///////////////////////////TUBE
		tubeMiddleX = new TextEnterButton(244,12,100,20,Color.white,SeyprisMain.getKL()) {
			@Override
			protected void textEntered(String text) {
				storedC.tube.middleX = checkValue(this);
			}
		};
		c_tube.addInContainer(tubeMiddleX);
		tubeMiddleX.setText(""+sc.tube.middleX);
		tubeMiddleY = new TextEnterButton(244,54,100,20,Color.white,SeyprisMain.getKL()) {
			@Override
			protected void textEntered(String text) {
				storedC.tube.middleY = checkValue(this);
			}
		};
		c_tube.addInContainer(tubeMiddleY);
		tubeMiddleY.setText(""+sc.tube.middleY);
		///////////////////////////////TRAY
		trayMiddleX = new TextEnterButton(244,12,100,20,Color.white,SeyprisMain.getKL()) {
			@Override
			protected void textEntered(String text) {
				storedC.tray.middleX = checkValue(this);
			}
		};
		c_tray.addInContainer(trayMiddleX);
		trayMiddleX.setText(""+sc.tray.middleX);
		trayMiddleY = new TextEnterButton(244,54,100,20,Color.white,SeyprisMain.getKL()) {
			@Override
			protected void textEntered(String text) {
				storedC.tray.middleY = checkValue(this);
			}
		};
		c_tray.addInContainer(trayMiddleY);
		trayMiddleY.setText(""+sc.tray.middleY);
		trayRasterX = new TextEnterButton(244,96,100,20,Color.white,SeyprisMain.getKL()) {
			@Override
			protected void textEntered(String text) {
				storedC.tray.followRateX = checkValue(this);
			}
		};
		c_tray.addInContainer(trayRasterX);
		trayRasterX.setText(""+sc.tray.followRateX);
		trayRasterY = new TextEnterButton(244,138,100,20,Color.white,SeyprisMain.getKL()) {
			@Override
			protected void textEntered(String text) {
				storedC.tray.followRateY = checkValue(this);
			}
		};
		c_tray.addInContainer(trayRasterY);
		trayRasterY.setText(""+sc.tray.followRateY);
		
		add(c_reel);
		add(c_tray);
		add(c_tube);
		
		toggledType(storedC.container);
	}

	@Override
	protected void paintSecond(Graphics g) {
		g.drawImage(imas[current], 10, 70, null);
		
	}

	@Override
	protected boolean close() {
		storedC.container = current;
		if(current != CURRENT_REEL)storedC.reel = null;
		if(current != CURRENT_TRAY)storedC.tray = null;
		if(current != CURRENT_TUBE)storedC.tube = null;
		return true;
	}

	@Override
	protected void uppdateIntern() {
		// TODO Auto-generated method stub
		
	}
	
	private double checkValue(TextEnterButton t){
		String s = t.getText();
		double d;
		try {
			d = Double.parseDouble(s);
		} catch (Exception e) {
			t.setTextColor(Color.red);
			return 0;
		}
		t.setTextColor(Color.black);
		return d;
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
	
	private void checkWholes(){
		try {
			int u = Integer.parseInt(reelWholes.getText());
			reelWholes.setTextColor(Color.black);
			u*=2;
			if(reelHalfWhole.getState())
				u++;
			storedC.reel.wholesPerComp = u;
		} catch (Exception e) {
			reelWholes.setTextColor(Color.red);
		}
	}

}
