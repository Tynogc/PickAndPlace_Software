package main;

import javax.swing.JFrame;

public class MainThread extends Thread{

	public static long currentTime = 0;
	
	private SeyprisMain frame;
	
	private boolean isRunning = true;
	
	public static final int timeToFrameUppdate = 33/SeyprisMain.fpsMultiplier;
	
	private int lastFPS;
	private long fpsMarker;
	private int currFps;
	private double frameTimeOVA;
	private double frameTimeOVAsmal;
	
	public MainThread(SeyprisMain f){
		super();
		frameTimeOVA = 50;
		frameTimeOVAsmal = 50;
		frame = f;
	}
	
	public void run(){
		int sleepTime = 0;
		fpsMarker = System.currentTimeMillis();
		while(isRunning){
			gui.PerformanceMenu.ThreadCheck(Thread.currentThread());
			gui.PerformanceMenu.startTime();
			currFps++;
			
			currentTime = System.currentTimeMillis();
			try {
				frame.loop(lastFPS, sleepTime,(int)frameTimeOVAsmal, (int)frameTimeOVA);
			} catch (Exception e) {
				debug.Debug.println("* FATAL: Uncatched Error!", debug.Debug.FATAL);
				debug.Debug.printExeption(e);
				isRunning = false;
				e.printStackTrace();
			}
			
			//FPS uberprufen
			if(currentTime-fpsMarker>500){
				fpsMarker += 500;
				if(Math.abs(fpsMarker-currentTime)>1000){
					fpsMarker = currentTime;
					currFps = 0;
					debug.Debug.println("WARNING: FPS criticaly low!", debug.Debug.WARN);
				}
				lastFPS = currFps*2;
				currFps = 0;
			}
			
			int q = (int)(System.currentTimeMillis()-currentTime);
			frameTimeOVA = (frameTimeOVA*499+q)/500;
			frameTimeOVAsmal = (frameTimeOVAsmal*49+q)/50;
			
			//Schlafen
			sleepTime = timeToFrameUppdate-(int)(System.currentTimeMillis()-currentTime);
			if(sleepTime > 0 ){
				try {
					sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else{
				System.out.println("lag"+sleepTime);
			}
		}
		isRunning = true;
		sleepTime = 60;
		debug.Debug.println("* Restoring to save Mode", debug.Debug.FATAL);
		debug.Debug.print(" Shutting down in "+sleepTime+"s");
		debug.Debug.panel.setVisible(true);
		debug.Debug.panel.getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		while (isRunning) {
			try {
				frame.saveLoop();
			} catch (Exception e) {
				debug.Debug.println("* Exeption still existing... Shutting down!", debug.Debug.FATAL);
				try {
					sleep(30000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				sleepTime = 0;
			}
			
			sleepTime--;
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(sleepTime < 0){
				System.exit(-1);
			}
		}
	}
	
	
	
}
