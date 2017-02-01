package gui.subMenu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import menu.Button;
import menu.Container;
import menu.DataFiled;
import menu.ScrollBar;

public class ComlinkWindow extends Container{
	
	public String[] lastEntries;
	public String[] timeStamp;
	public int pos;
	public static final int stackSize = 50;
	
	private BufferedImage buffer;
	
	private static final int xSize = 400;
	private static final int ySize = 200;
	
	private static final int textSize = 15;
	
	private ScrollBar scr;
	private DataFiled data;
	
	private int scroll;
	
	private static final Color blue = new Color(0,130,255);
	
	private boolean needUpdate;

	public ComlinkWindow(int x, int y, String titel) {
		super(x, y);
		lastEntries = new String[stackSize];
		timeStamp = new String[stackSize];
		for (int i = 0; i < lastEntries.length; i++) {
			lastEntries[i] = "";
			timeStamp[i] = "";
		}
		pos = 0;
		
		scr = new ScrollBar(xSize, 21, ySize-20, ySize/textSize, stackSize);
		data = new DataFiled(0,0,xSize+19,20,Button.blue) {
			@Override
			protected void uppdate() {
				if(scroll != scr.getScrolled())
					repaint();
				if(needUpdate)
					repaint();
			}
			@Override
			protected void isClicked() {
			}
			@Override
			public void paintYou(Graphics g) {
				g.drawImage(buffer, 0, 20, null);
				super.paintYou(g);
			}
		};
		addInContainer(scr);
		addInContainer(data);
		data.setText(titel);
		
		repaint();
	}
	
	public void addText(String t){
		addText(t, System.currentTimeMillis());
	}
	
	public void addText(String[] t, long time){
		for (int i = 0; i < t.length-1; i++) {
			addText(t[i],0);
		}
		addText(t[t.length-1],time);
	}
	
	public void addText(String t, long time){
		if(time != 0){
			timeStamp[pos] = "["+new java.text.SimpleDateFormat("mm:ss.").format(new java.util.Date (time))+
					(time%1000)/100+(time%100)/10+"]";
		}else{
			timeStamp[pos] = "";
		}
		lastEntries[pos] = t;
		pos++;
		if(pos>=stackSize)
			pos = 0;
		needUpdate = true;
	}
	
	public void dublicate(ComlinkWindow k){
		lastEntries = k.lastEntries.clone();
		timeStamp = k.timeStamp.clone();
		pos = k.pos;
		repaint();
	}
	
	private void repaint(){
		buffer = new BufferedImage(xSize, ySize, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D)buffer.getGraphics();
		
		g.setColor(DataFiled.l1);
		g.drawRect(0, 0, xSize-1, ySize-1);
		g.setColor(DataFiled.l2);
		g.drawRect(1, 1, xSize-3, ySize-3);
		
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setColor(Color.white);
		g.setFont(main.Fonts.font10);
		
		int scr = this.scr.getScrolled();
		
		for (int i = 0; i < timeStamp.length; i++) {
			int p = pos-i-scr-1;
			if(p<0)p+=stackSize;
			if(p<0)p+=stackSize;
			if(p<0)p = 0;
			g.drawString(timeStamp[p],3,14+i*15);
		}
		
		g.setColor(blue);
		g.setFont(main.Fonts.font14);
		
		for (int i = 0; i < lastEntries.length; i++) {
			int p = pos-i-scr-1;
			if(p<0)p+=stackSize;
			if(p<0)p+=stackSize;
			if(p<0)p = 0;
			g.drawString(lastEntries[p],65,15+i*15);
		}
		
		scroll = scr;
		needUpdate = false;
	}

}
