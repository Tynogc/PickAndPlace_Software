package gui.subMenu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import componetStorage.StoredComponent;
import setup.ComponentFeeder;
import main.Fonts;
import main.PicLoader;
import menu.Button;
import menu.DataFiled;
import gui.MoveMenu;

public class FeederComponentsSetup extends MoveMenu{

	private Button[] comps;
	private final ComponentFeeder feeders;
	
	private BufferedImage buf;
	
	public FeederComponentsSetup(ComponentFeeder f, String t, boolean reel) {
		super(50,100, PicLoader.pic.getImage("res/ima/mbe/m400x800.png"), "Feeder Positions");
		feeders = f;
		comps = new Button[feeders.size];
		for (int i = 0; i < comps.length; i++) {
			setButton(i);
		}
		
		DataFiled df = new DataFiled(28,30,100,20,Color.blue) {
			@Override
			protected void uppdate() {}
			@Override
			protected void isClicked() {}
		};
		add(df);
		df.setText(t);
		if(reel){
			df.setTextColor(Color.white);
		}else{
			df.setColor(Color.cyan);
		}
		
		buf = new BufferedImage(32, comps.length*32, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D)buf.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setFont(Fonts.font18);
		for (int i = 0; i < comps.length; i++) {
			if(i%2 == 0){
				g.setColor(Color.black);
			}else{
				g.setColor(Color.white);
			}
			g.fillRect(0,i*32,32,32);
			if(i%2 == 1){
				g.setColor(Color.black);
			}else{
				g.setColor(Color.white);
			}
			if(i<10){
				g.drawString(""+i, 12, 32*i+21);
			}else{
				g.drawString(""+i, 5, 32*i+21);
			}
		}
	}
	
	private void setButton(final int i){
		comps[i] = new Button(60,50+i*32,"res/ima/cli/G") {
			@Override
			protected void uppdate() {}
			
			@Override
			protected void isFocused() {}
			
			@Override
			protected void isClicked() {
				ComponentBrowser sb = new ComponentBrowser(300,130) {
					@Override
					public void loadedComp(StoredComponent s) {
						feeders.holds[i] = s;
					}
				};
				gui.GuiControle.addMenu(sb);
			}
			
			@Override
			public void longTermUpdate() {
				super.longTermUpdate();
				if(feeders.holds[i]!=null){
					setText(feeders.holds[i].name);
					setSecondLine(feeders.holds[i].id);
				}
			}
		};
		add(comps[i]);
	}

	@Override
	protected void paintSecond(Graphics g) {
		g.drawImage(buf, 28, 50, null);
	}

	@Override
	protected boolean close() {
		
		return true;
	}

	@Override
	protected void uppdateIntern() {
		
	}

}
