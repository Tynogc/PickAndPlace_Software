package gui;

import java.awt.Color;
import java.awt.Graphics;
import main.SeyprisMain;
import menu.AbstractMenu;
import menu.Button;

public class ShutdownMenu extends AbstractMenu{
	
	private Button closehook;
	private boolean stillActiv;
	private Button shut;
	private Button cancel;
	private Button advLog;
	private Button restart;
	
	private final int x;
	private final int y;
	
	private long timeToShutdown;
	private int tts;
	
	public ShutdownMenu(Button b) {
		super(0,0,SeyprisMain.sizeX(),SeyprisMain.sizeY());
		closehook = b;
		stillActiv = true;
		
		x = SeyprisMain.sizeX()/2;
		y = SeyprisMain.sizeY()/2;
		
		shut = new Button(x-55,y-16,"res/ima/cli/001") {
			@Override
			protected void uppdate() {}
			@Override
			protected void isFocused() {}
			@Override
			protected void isClicked() {
				stopSys();
			}
		};
		restart = new Button(x+105,y-16,"res/ima/cli/001") {//TODO other image
			@Override
			protected void uppdate() {}
			@Override
			protected void isFocused() {}
			@Override
			protected void isClicked() {
				main.ExitThread.restart = true;
				stopSys();
			}
		};
		cancel = new Button(x-55,y+20,"res/win/gui/cli/Gsk") {
			@Override
			protected void uppdate() {}
			@Override
			protected void isFocused() {}
			@Override
			protected void isClicked() {
				stillActiv = false;
				closeYou();
			}
		};
		
		advLog = new Button(x+25,y+20,"res/win/gui/cli/Gsk") {
			@Override
			protected void uppdate() {}
			@Override
			protected void isFocused() {}
			@Override
			protected void isClicked() {
				//TODO AdvancedLog schreiben
			}
		};
		advLog.setText("AdvLog");
		cancel.setText("Cancel");
		add(advLog);
		add(cancel);
		add(shut);
		add(restart);
		
		timeToShutdown  =System.currentTimeMillis()+30000;
	}
	
	@Override
	protected void uppdateIntern() {
		if(!closehook.wasLastClicked()){
			stillActiv = false;
		}
		tts = (int)(timeToShutdown-System.currentTimeMillis());
		if(timeToShutdown<System.currentTimeMillis()){
			System.exit(0);
		}
	}

	@Override
	protected void paintIntern(Graphics g) {
		/*int q = tts-25000;
		q /= 20;
		q = 200-q;
		if(q>200)q = 200;
		if(q<0)q=0;
		if(tts == 0)q = 0;
		g.setColor(new Color(100,100,100,q));
		g.fillRect(0, 0, MainFrame.frameX, MainFrame.frameY);*/
		
		g.setColor(Color.white);
		g.setFont(Button.boldFont14);
		g.drawString("Shutdown in "+tts/1000+" s", x, y+7);
	}
	
	public boolean stillActiv(){
		return stillActiv;
	}
	
	private void stopSys(){
		//TODO handle stop
		System.exit(0);
	}

}
