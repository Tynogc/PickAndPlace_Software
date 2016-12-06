package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import menu.Button;
import menu.CheckBox;
import menu.OsziDiagramm;
import main.PicLoader;

public class PerformanceMenu extends menu.AbstractMenu{

	private static PerformanceMenu per;
	
	private int[] time;
	private double[] primary;
	private double[] secondary;
	private double[] percentage;
	private int[] max;
	
	private double load;
	private double loadN;
	private double nanosec;
	private double nanosecFull;
	
	private long startTime;
	
	private static final int lenght = 8;
	
	public static final int PlayerAndMap = 0;
	public static final int NPCmovement = 1;
	public static final int UpdateGui = 2;
	public static final int PaintBack = 3;
	public static final int PaintEntity = 4;
	public static final int PaintGui = 5;
	public static final int RedrawBack = 6;
	
	public static int numberOfThreads;
	private static int threadRefresh;
	
	private Color[] cols;
	
	private OsziDiagramm digrammLog;
	private OsziDiagramm diagrammLogRam;
	
	private CheckBox hcm;
	private CheckBox adt;
	
	private static boolean highContrast = false;
	
	public PerformanceMenu() {
		super(100, 100, 160, 100);
		
		time = new int[lenght];
		max = new int[lenght];
		primary = new double[lenght];
		secondary = new double[lenght];
		percentage = new double[lenght];
		
		cols = new Color[]{
				Color.red,
				new Color(234,187,0),
				new Color(102,52,228),
				new Color(0,219,46),
				new Color(0,11,235),
				new Color(250,130,0),
				new Color(0,187,221),
				Color.gray
		};
		
		for (int i = 0; i < lenght; i++) {
			time[i] = 0;
			max[i] = 0;
			primary[i] = 0.0;
			secondary[i] = 0.0;
			percentage[i] = 0.0;
		}
		
		hcm = new CheckBox(10, 40,"res/ima/cli/cbx/CB",100) {
			@Override
			public void changed(boolean b) {
				highContrast = b;
			}
		};
		hcm.setText("High Contrast");
		add(hcm);
		adt = new CheckBox(130, 40,"res/ima/cli/cbx/CB",100) {
			@Override
			public void changed(boolean b) {
			}
		};
		adt.setText("Thread info");
		add(adt);
		
		digrammLog = new OsziDiagramm(10, 75, PicLoader.pic.getImage("res/ima/win/tsk.png"));
		digrammLog.setText("Load:");
		add(digrammLog);
		diagrammLogRam = new OsziDiagramm(120, 75, PicLoader.pic.getImage("res/ima/win/tsk.png"));
		diagrammLogRam.setText("RAM:");
		add(diagrammLogRam);
		
		per = this;
		
		closeOutside = false;
	}
	
	public static void markTime(int pos){
		if(per != null)per.mt(pos);
	}
	
	@Override
	public void longTermUpdate() {
		
	}
	
	private void mt(int pos){
		if(pos<0 || pos>=lenght){
			debug.Debug.println("Error PerformanceMenu01: pos OOB:"+pos,debug.Debug.ERROR);
			return;
		}
		time[pos] = (int)(getTime()-startTime);
	}
	
	public static void startTime(){
		if(per != null)per.st();
		threadRefresh++;
		if(threadRefresh >= 25){
			threadRefresh = 0;
			numberOfThreads = Thread.activeCount();
		}
	}
	private int iSiera;
	private void st(){
		time[lenght-1] = (int)(getTime()-startTime);
		startTime = getTime();
		
		double h = (double)time[lenght-1];
		iSiera++;
		if(iSiera>1000)iSiera = 1000;
		
		double lo = -((double)(time[lenght-1]-time[lenght-2])/(double)time[lenght-1]*100)+100;
		logInDia(lo);
		load = (load*9+(double)lo)/10;
		loadN = (loadN*(iSiera-1)+(double)lo)/iSiera;
		long sum = 0;
		for (int i = 0; i < lenght; i++) {
			if(time[i]==0){
				percentage[i]=0;
				continue;
			}
			time[i]-=sum;
			sum+=time[i];
			double per = (double)time[i]/h;
			percentage[i] = (percentage[i]*9+per)/10;
			secondary[i] = (secondary[i]*(iSiera-1)+per)/(iSiera);
			primary[i] = ((double)time[i]*49+(double)per)/50;
		}
		nanosecFull = (nanosecFull*49+sum)/50;
		nanosec = (nanosec*49+(sum-time[lenght-1]))/50;
	}
	
	private double logData= 0.0;
	private static int logTetta = 0;
	private void logInDia(double lo){
		logTetta++;
		
		logData = (logData*(logTetta-1)+lo)/logTetta;
		
		if(logTetta>20){
			logTetta = 0;
			digrammLog.logData(logData/100.0+0.02);
			logData = 0;
		}
	}
	
	public static void logRam(double r, double d){
		if(per!= null)per.loRam(r, d);
	}
	private void loRam(double r, double d){
		diagrammLogRam.logData(r, d);
	}
	
	private long getTime(){
		return System.nanoTime();
	}
	
	@Override
	protected void uppdateIntern() {
		
	}

	private final int atX = 10; 
	
	@Override
	protected void paintIntern(Graphics g) {
		int x = atX;
		g.setFont(Button.plainFont);
		for (int i = 0; i < lenght; i++) {
			int nx = (int)(percentage[i]*300);
			g.setColor(cols[i]);
			g.fillRect(x, 200, nx, 20);
			x += nx;
			paintString(g, getName(i), atX, 300+i*20,cols[i]);
			paintString(g, ""+etsInt((long)(primary[i]))+"ns ("+(int)(percentage[i]*100)+"%)", atX+100, 300+i*20, cols[i]);
		}
		x = atX;
		for (int i = 0; i < lenght; i++) {
			int nx = (int)(secondary[i]*300);
			g.setColor(cols[i]);
			g.fillRect(x, 240, nx, 20);
			x += nx;
		}
		g.setColor(Color.gray);
		g.drawString("Load: "+(int)load+"% (Last Second)", atX, 199);
		g.drawString("Load: "+(int)loadN+"% (Last 20s)", atX, 238);
		paintString(g, "Time: "+etsInt((long)nanosec)+"/"+etsInt((long)nanosecFull)+"ns", atX, 460, Color.gray);
		paintString(g, "Threads: "+numberOfThreads, atX, 480, Color.gray);
		
		if(adt.getState()){
			paintThl(g, 500, true);
		}
	}
	
	public static String etsInt(long l){
		String s = "";
		if(l<0)return s+l;
		long k = 10000000;
		for (int i = 0; i < 20; i++) {
			if(k<=l)break;
			k/=10;
			s+="0";
		}
		return s+l;
	}
	
	public static void paintString(Graphics g, String s, int x, int y, Color c){
		if(highContrast){
			g.setColor(Color.black);
			g.drawString(s, x-1, y);
			g.drawString(s, x+1, y);
			g.drawString(s, x, y-1);
			g.drawString(s, x, y+1);
		}
		g.setColor(c);
		g.drawString(s, x, y);
	}
	
	private String getName(int i){
		switch (i) {
		case PlayerAndMap:
			return "Player and Map:";
		case NPCmovement:
			return "NPC movement:";
		case PaintEntity:
			return "Paint Entity:";
		case PaintGui:
			return "Paint GUI:";
		case UpdateGui:
			return "Update GUI:";
		case PaintBack:
			return "Paint Back:";
		case RedrawBack:
			return "Redraw All:";
		case lenght-1:
			return "Sleep:";

		default:
			return "???";
		}
	}
	
	private static Semaphore threradSema;
	private static ThreadMesenger thm;
	public static void ThreadCheck(Thread t){
		if(threradSema == null)
			threradSema = new Semaphore(1);
		
		try {
			if(threradSema.tryAcquire(1, 50, TimeUnit.MILLISECONDS)){
				if(thm == null)
					thm = new ThreadMesenger(t);
				else
					thm.loockForTh(t);
				threradSema.release();
			}
		} catch (InterruptedException e) {
			debug.Debug.printExeption(e);
		}
	}
	
	private void paintThl(Graphics g, int y, boolean advanced){
		try {
			threradSema.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(thm != null)
			thm.draw(g, y, advanced);
		threradSema.release();
	}

}

class ThreadMesenger{
	
	public String name;
	
	public Thread th;
	
	public long t;
	
	public long lastT;
	public long maxTime;
	public float avr10;
	
	private ThreadMesenger next;
	
	public ThreadMesenger(Thread t){
		name = t.getName();
		th = t;
		this.t = System.nanoTime();
	}
	
	public void loockForTh(Thread t){
		if(name.compareTo(t.getName())==0){
			long s = System.nanoTime();
			lastT = s-this.t;
			this.t = s;
			
			if(lastT>maxTime || Math.random() <0.01){
				maxTime = lastT;
			}
			avr10 = (avr10*9+lastT)/10f;
			return;
		}
		if(next != null){
			next.loockForTh(t);
		}else{
			next = new ThreadMesenger(t);
		}
	}
	
	public void draw(Graphics g, int y, boolean adv){
		String s = " X";
		if(th.isAlive())s = " Run";
		PerformanceMenu.paintString(g, name+s, 10, y, Color.cyan);
		PerformanceMenu.paintString(g, "TTR:"+PerformanceMenu.etsInt((int)avr10)+"ns", 160, y, Color.gray);
		PerformanceMenu.paintString(g, "MAX:"+PerformanceMenu.etsInt(maxTime)+"ns", 160, y+20, Color.gray);
		
		int o = (int)(lastT/200000);
		if(o>100)o=100;
		g.setColor(Color.cyan);
		g.drawRect(10, y+20, o, 1);
		g.setColor(Color.black);
		g.drawRect(9, y+19, o+2, 3);
		
		o = (int)(maxTime/200000);
		if(o>100)o=100;
		g.setColor(Color.yellow);
		g.drawRect(o+10, y+15, 0, 5);
		g.setColor(Color.black);
		g.drawRect(o+9, y+14, 2, 7);
		
		if(next != null)
			next.draw(g, y+40, adv);
	}
}
