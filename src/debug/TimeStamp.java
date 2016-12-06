package debug;

import java.awt.Color;
import java.awt.Graphics;

public class TimeStamp {
	
	private static long[] times;
	private static long startTime;
	private static long totalStart;
	private static double[] average;
	private static long[] max;
	
	private static boolean init = false;
	
	private static long[] timesToPrint;
	private static double[] averageToPrint;
	private static long[] maxToPrint;
	private static long totalTimeToPrint;
	
	private static final int arrayLenght = 11;
	
	public static final int GENERATING_BUFFER = 1;
	public static final int PLAYER_ALGORYTHM = 2;
	public static final int REFRESH_LAYERS = 3;
	public static final int REFRESH_PARTICELS = 4;
	public static final int PAINT_LANDSCAPE = 5;
	public static final int PAINT_UI = 6;
	public static final int PAINT_TIMESTAMP = 7;
	public static final int UI_REFRESH = 8;
	public static final int USER_INTETRACTION = 9;
	public static final int PAIN_FRAME = 10;
	
	
	public static void setTimeMark(int timeCode){
		if(!init){
			return;
		}
		if(timeCode >= arrayLenght){
			Debug.println("ERROR Timestamp01: Critical Error", Debug.WARN);
			return;
		}
		long t = System.nanoTime();
		times[timeCode] = (t-startTime);
		startTime = t;
		average[timeCode] = average[timeCode]*0.9+0.1*times[timeCode];
		if(times[timeCode] > max[timeCode]){
			max[timeCode] = times[timeCode];
		}else{
			if(t%100 == 1) max[timeCode] = 0;
		}
	}
	
	public static void start(){
		startTime = System.nanoTime();
		totalStart = startTime;
	}
	
	public static void end(){
		if(!init){
			return;
		}
		for (int i = 0; i < arrayLenght; i++) {
			timesToPrint[i] = times[i];
			times[i]= 0;
			averageToPrint[i] = average[i];
			maxToPrint[i] = max[i];
		}
		totalTimeToPrint = (System.nanoTime()-totalStart);
	}
	
	public TimeStamp(){
		times = new long[arrayLenght];
		average = new double[arrayLenght];
		timesToPrint = new long[arrayLenght];
		averageToPrint = new double[arrayLenght];
		max = new long[arrayLenght];
		maxToPrint = new long[arrayLenght];
		init = true;
	}
	
	public void paintYou(Graphics g, int xMax){
		int sum = 0;
		//double qpr = 200.0/totalTimeToPrint;
		double qpr = 4.160031416557258E-5;
		for (int i = 0; i < arrayLenght; i++) {
			sum+=timesToPrint[i];
			paintALine(getSToNum(i), xMax-270, i*15+20, timesToPrint[i]*qpr, averageToPrint[i]*qpr, maxToPrint[i]*qpr, g);
		}
		
		g.setColor(Color.red);
		g.drawString("Total PC-Cycles: "+sum+"(+"+(totalTimeToPrint-sum)+")", xMax-200, 10);
		
	}
	
	private String getSToNum(int i){
		switch (i) {
		case GENERATING_BUFFER:
			return "Generating Buffer";
		case PLAYER_ALGORYTHM:
			return "Player.move";
		case REFRESH_LAYERS:
			return "GraphicControle.refresh.layer1";
		case REFRESH_PARTICELS:
			return "GraphicControle.refresh.particles";
		case PAINT_LANDSCAPE:
			return "GraphicControle.paint.landscape";
		case PAINT_TIMESTAMP:
			return "GraphicControle.paint.timestamp";
		case PAINT_UI:
			return "GraphicControle.paint.ui";
		case UI_REFRESH:
			return "MainFrame.ui.refresh";
		case USER_INTETRACTION:
			return "MainFrame.checkMouse";
		case PAIN_FRAME:
			return "MainFrame.Panel.paint";
			

		default:
			return "EMPTY";
		}
	}
	
	private void paintALine(String s, int x, int y, double dist, double aver, double max, Graphics g){
		g.setColor(new Color(250,100,100));
		g.fillRect(x, y, (int)dist, 12);
		g.setColor(new Color(250,250,0));
		g.drawLine(x+(int)max, y-2, x+(int)max, y+14);
		g.setColor(Color.black);
		g.drawString(s+"("+(int)aver+")", x, y+10);
		g.drawString("MAX: "+(int)max+"", x+210, y+10);
	}
	
	

}
