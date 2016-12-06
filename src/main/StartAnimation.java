package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class StartAnimation {
	
	private BufferedImage buff;
	private long startTime = 0;
	
	private static final int ms_per_pix = 10;
	
	private Line[][] lines;
	private final int linesLenght = 14;
	
	/*private static final Color c1 = new Color(0,166,245,10);
	public static final Color c2 = new Color(30,126,225,10);
	public static final Color c3 = new Color(66,66,210,10);
	public static final Color c4 = new Color(130,230,250,10);*/
	private static final Color c1 = new Color(150,20,240,60);
	public static final Color c2 = new Color(50,250,225,60);
	public static final Color c3 = new Color(66,60,250,60);
	public static final Color c4 = new Color(10,10,200,60);
	private final int adding = 10;
	
	private static final String ich = "(C) Sven T. Schneider";
	
	private Font f1;
	
	private Graphics2D g;
	
	public StartAnimation(boolean full){
		buff = new BufferedImage(370, 300, BufferedImage.TYPE_INT_ARGB);
		buff.getGraphics().drawImage(PicLoader.pic.getImage("res/ima/intro.png"), 0, 0, null);
		
		if(full){
			lines = new Line[][]{
					makeL(c1, 0),
					makeL(c2, adding),
					makeL(c3, adding*2),
					makeL(c4, adding*3),
					makeLE(Color.white, 570)
			};
		}else{
			lines = new Line[][]{
					makeL(Color.white, 0)};
		}
		
		f1 = new Font(Font.DIALOG, 0, 14);
		g = (Graphics2D)buff.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.scale(0.5, 0.5);
	}
	
	private Line[] makeL(Color c, int i){
		Line[] l = new Line[linesLenght];
		//S
		l[0] = new Line(50, 400, 100, true, false, 20+i, c);
		l[1] = new Circ(150, 370, 80, false, 30, 120+i, c);
		l[2] = new Line(150, 340, 70, true, true, 200+i, c);
		l[3] = new Circ(80, 310, 80, true, 30, 270+i, c);
		l[4] = new Line(80, 280, 100, true, false, 350+i, c);
		//T
		//i += 450;
		i+=100;
		l[5] = new Line(180, 280, 270, true, false, i, c);
		l[6] = new Line(250, 280, 120, false, false, 50+i, c);
		l[7] = new Line(250, 400, 70, true, false, 170+i, c);
		//i += 220;
		i+=100;
		int klp = 270;
		//S
		l[8] = new Line(50+klp, 400, 100, true, false, 20+i, c);
		l[9] = new Circ(150+klp, 370, 80, false, 30, 120+i, c);
		l[10] = new Line(150+klp, 340, 70, true, true, 200+i, c);
		l[11] = new Circ(80+klp, 310, 80, true, 30, 270+i, c);
		//l[12] = new Line(80+klp, 280, 100, true, false, 350+i, c);
		
		return l;
	}
	private Line[] makeLE(Color c, int i){
		Line[] l = new Line[linesLenght];
		//S
		l[0] = new Line(50, 400, 100, true, false, i, c);
		l[1] = new Circ(150, 370, 80, false, 30, i, c);
		l[2] = new Line(150, 340, 70, true, true, i, c);
		l[3] = new Circ(80, 310, 80, true, 30, i, c);
		l[4] = new Line(80, 280, 100, true, false, i, c);
		//T
		//i += 450;
		i+=20;
		l[5] = new Line(180, 280, 270, true, false, i, c);
		l[6] = new Line(250, 280, 120, false, false, i, c);
		l[7] = new Line(250, 400, 70, true, false, i, c);
		//i += 220;
		i+=15;
		int klp = 270;
		//S
		l[8] = new Line(50+klp, 400, 100, true, false, i, c);
		l[9] = new Circ(150+klp, 370, 80, false, 30, i, c);
		l[10] = new Line(150+klp, 340, 70, true, true, i, c);
		l[11] = new Circ(80+klp, 310, 80, true, 30, i, c);
		l[12] = new Line(80+klp, 280, 100, true, false, i, c);
		return l;
	}
	
	public void start(){
		startTime = System.currentTimeMillis();
	}
	
	public void paint(Graphics g, int x, int y){
		g.drawImage(buff, x, y, null);
		int t = (int)(System.currentTimeMillis()-startTime)/ms_per_pix;
		//g.drawString("D"+t, x, y+10);
		t -= 480;
		if(t>0){
			g.setColor(Color.lightGray);
			g.setFont(f1);
			t/= 10;
			if(t>=ich.length())t=ich.length();
			g.drawString(ich.substring(0,t), x+30, y+280);
		}
	}
	
	/**
	 * @return true = end of Animation
	 */
	public boolean uppdate(){
		if(startTime == 0)start();
		int t = (int)(System.currentTimeMillis()-startTime)/ms_per_pix;
		
		for (int j = 0; j < lines.length; j++) {
			for (int i = 0; i < linesLenght; i++) {
				if(lines[j][i]!= null)lines[j][i].doIt(t,g);
			}
		}
		
		if(t>900)return true;
		
		return false;
	}

}

class Line{
	protected int x;
	protected int y;
	protected int t;
	protected boolean h;
	protected boolean hr;
	
	protected int last;
	
	protected int startTime;
	
	public boolean done;
	
	protected int circelSize = 34;
	
	protected Color col;
	
	public Line(int xt, int yt, int tt, boolean ht, boolean hrt, int st, Color c){
		x = xt;
		y = yt;
		t = tt;
		h = ht;
		hr = hrt;
		last = 0;
		startTime = st;
		done = false;
		col = c;
		if(col.equals(Color.white)){
			circelSize = 20;
			x+=7;
			y+=7;
		}
		x+=48+48;
		y-=56+56;
	}
	
	public void doIt(int t, Graphics2D g){
		if(t<startTime)return;
		t-= startTime;
		g.setColor(col);
		for (int i = last; i <= t; i++) {
			if(t>this.t)return;
			doIntern(i, g);
		}
		last = t;
	}
	
	protected void doIntern(int t, Graphics2D g){
		int i = 1;
		if(hr)i = -1;
		if(h){
			g.fillOval(x+t*i, y, circelSize, circelSize);
		}else{
			g.fillOval(x, y+t*i, circelSize, circelSize);
		}
		
	}
	
}

class Circ extends Line{
	
	protected int radius;
	
	private double ikrs;

	public Circ(int xt, int yt, int tt, boolean ht, int r, int st, Color c) {
		super(xt, yt, tt, ht, false, st, c);
		radius = r;
		ikrs = 180.0/(double)tt;
	}
	
	protected void doIntern(int t, Graphics2D g){
		int er = 1;
		if(h)er = -1;
		
		double y = Math.toRadians(90-ikrs*t);
		double x = Math.cos(y);
		y = Math.sin(y);
		
		g.fillOval(this.x+(int)(radius*x)*er, this.y+(int)(radius*y), circelSize, circelSize);
	}
	
}
