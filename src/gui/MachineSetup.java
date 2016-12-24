package gui;

import gui.subMenu.ComponentLibrary;
import gui.subMenu.ComponentSetup;

import java.awt.Graphics;
import java.io.File;

import utility.FileLoader;
import menu.AbstractMenu;
import menu.Button;

public class MachineSetup extends AbstractMenu{

	public MachineSetup() {
		super(0,70,1000,600);
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
