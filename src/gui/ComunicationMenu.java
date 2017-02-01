package gui;

import java.awt.Graphics;

import gui.subMenu.ComlinkWindow;
import menu.AbstractMenu;
import menu.Button;

public class ComunicationMenu extends AbstractMenu{

	private ComlinkWindow uplink;
	private ComlinkWindow downlink;
	
	private Button test;
	
	public ComunicationMenu() {
		super(0,100,1000,800);
		moveAble = false;
		
		uplink = new ComlinkWindow(100, 0, "Uplink");
		downlink = new ComlinkWindow(100, 300, "Downlink");
		add(uplink);
		add(downlink);
		
		test = new Button(100,600,"res/ima/cli/G") {
			int i;
			@Override
			protected void uppdate() {	
			}
			@Override
			protected void isFocused() {	
			}
			@Override
			protected void isClicked() {
				uplink.addText(new String[]{i+++" "+Math.random(), i+++" "+Math.random(), i+++" "+Math.random()},System.currentTimeMillis());
			}
		};
		add(test);
	}

	@Override
	protected void uppdateIntern() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void paintIntern(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	
	public void upLinkString(String[] s, long time){
		uplink.addText(s, time);
	}
	
	public void downLinkString(String[] s, long time){
		downlink.addText(s, time);
	}

}
