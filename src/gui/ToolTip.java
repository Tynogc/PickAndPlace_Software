package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;

import menu.AbstractMenu;
import menu.MenuControle;

public class ToolTip extends MenuControle{
	
	private int currentName;
	private int currentPriority;
	
	public static final int MAXIMUM_PRIORITY = 10;
	public static final int NORMAL_PRIORITY = 5;
	
	private static ToolTip tt;
	
	public ToolTip(){
		tt = this;
	}
	
	public void openTT(int name, int priority, int x, int y){
		if(name == currentName)return;
		if(priority<currentPriority||currentPriority == MAXIMUM_PRIORITY){
			debug.Debug.println("* Can't open ToolTip: Other Tooltip is more important!");
			return;
		}
		currentName = name;
		currentPriority = priority;
		try {
			FileReader fr = new FileReader("res/tti/"+currentName+".txt");
		    BufferedReader br = new BufferedReader(fr);
		    int i = 0;
		    String s;
		    do {
				s = br.readLine();
				i++;
			} while (s!=null);
		    br.close();
		    String[] st = new String[i-1];
		    i = 0;
		    fr = new FileReader("res/tti/"+currentName+".txt");
		    br = new BufferedReader(fr);
		    do {
				st[i] = br.readLine();
				i++;
			} while (i<st.length);
		    br.close();
		    BufferedImage b;
		    b = main.PicLoader.pic.getImage("res/tti/smalTT.png");
		    ToolTipMenu m = new ToolTipMenu(x, y, b, st);
		    setActivMenu(m);
		} catch (Exception e) {
			debug.Debug.println("* ERROR opening ToolTip: "+e.toString());
			currentPriority = 0;
		}
	}
	
	@Override
	public void setActivMenu(AbstractMenu m) {
		super.setActivMenu(m);
		if(m == null){
			currentName = 0;
			currentPriority = 0;
		}
	}
	
	public static void openToolTip(int name, int priority, int x, int y){
		if(tt == null)return;
		if(name == tt.currentName)return;
		tt.openTT(name, priority, x, y);
	}
	
	public static void closeToolTip(int name){
		if(tt == null)return;
		if(name == tt.currentName){
			tt.setActivMenu(null);
		}
	}

}

class ToolTipMenu extends MoveMenu{

	private BufferedImage back;
	private String[] str;
	
	public ToolTipMenu(int x, int y, BufferedImage b, String[] str) {
		super(x, y, b, "");
		back = b;
		this.str = str;
		moveAble = false;
		yPos-=b.getHeight();
		if(yPos<0)yPos = 0;
	}

	@Override
	protected void uppdateIntern() {}

	@Override
	protected void paintSecond(Graphics g) {
		g.drawImage(back, 0, 0, null);
		g.setColor(Color.white);
		g.setFont(main.Fonts.font16);
		for (int i = 0; i < str.length; i++) {
			g.drawString(str[i], 15, i*18+16);
		}
	}

	@Override
	protected boolean close() {
		return true;
	}
	
}
