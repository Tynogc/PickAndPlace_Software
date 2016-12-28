package gui;

import gui.subMenu.ComponentLibrary;
import gui.subMenu.FeederComponentsSetup;

import java.awt.Color;
import java.awt.Graphics;

import process.MachineLayout;
import process.ProcessControle;
import setup.ComponentFeeder;
import setup.SetupControle;
import menu.AbstractMenu;
import menu.Button;
import menu.DataFiled;

public class MachineSetup extends AbstractMenu{

	public DataFiled[] info;
	public Button[] buttons;
	
	private MachineLayout mLayout;
	private SetupControle setup;
	
	public MachineSetup(ProcessControle p) {
		super(0,70,1000,600);
		
		mLayout = p.machineLayout;
		setup = p.setup;
		
		Button neu = new Button(40,0,"res/ima/cli/B") {
			@Override
			protected void uppdate() {}
			@Override
			protected void isFocused() {}
			@Override
			protected void isClicked() {
				GuiControle.addMenu(new ComponentLibrary());
			}
		};
		add(neu);
		neu.setTextColor(Button.gray);
		neu.setText("Edit Component Lib");
		
		int s = setup.reelFeeder.length+setup.trayFeeder.length;
		
		info = new DataFiled[s];
		buttons = new Button[s];
		s = setup.reelFeeder.length;
		for (int i = 0; i < s; i++) {
			info[i] = new DataFiled(40,100+70*i,100,20,Color.blue) {
				@Override
				protected void uppdate() {}
				@Override
				protected void isClicked() {}
			};
			info[i].setTextColor(Color.white);
			info[i].setText("Reel "+i);
			add(info[i]);
			buttons[i] = new ECFbutton(140,100+70*i,setup.reelFeeder[i], "Reel "+i, true);
			add(buttons[i]);
		}
		
		for (int i = s; i < info.length; i++) {
			info[i] = new DataFiled(40,100+70*i,100,20,Color.CYAN) {
				@Override
				protected void uppdate() {}
				@Override
				protected void isClicked() {}
			};
			info[i].setText("Tray "+(i-s));
			add(info[i]);
			buttons[i] = new ECFbutton(140,100+70*i,setup.trayFeeder[i-s], "Tray "+(i-s), false);
			add(buttons[i]);
		}
		
		moveAble = false;
	}

	@Override
	protected void uppdateIntern() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void paintIntern(Graphics g) {
		// TODO Auto-generated method stub
		
	}

}
class ECFbutton extends Button{

	private final ComponentFeeder feeder;
	private String te;
	private boolean be;
	
	public ECFbutton(int x, int y, ComponentFeeder f, String t, boolean b) {
		super(x, y, "res/ima/cli/Gsk");
		setText("Edit");
		feeder = f;
		te = t;
		be = b;
	}

	@Override
	protected void isClicked() {
		gui.GuiControle.addMenu(new FeederComponentsSetup(feeder, te, be));
	}

	@Override
	protected void isFocused() {
	}

	@Override
	protected void uppdate() {
	}
	
}
