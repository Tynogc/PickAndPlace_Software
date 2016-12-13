package gui;

import java.awt.Color;
import java.awt.Graphics;

import main.SeyprisMain;
import menu.AbstractMenu;
import menu.AlarmkButton;
import menu.Button;
import menu.DataFiled;

public class TopMenu extends AbstractMenu{
	
	private long lastUppdate = 0;
	
	private DataFiled time;
	private DataFiled timeMission;
	private menu.AlarmkButton alarm;
	
	private DataFiled RAMfree;
	private DataFiled RAMtotal;
	private DataFiled RAMmax;
	
	private Button shutDown;
	
	private Runtime runtime;
	
	private long missionTime;

	public TopMenu() {
		super(0,0,SeyprisMain.sizeX(), 70);
		Button b1 = new Button(30,20,MenuConstants.filePathButton+"G") {
			
			@Override
			protected void uppdate() {
				
			}
			
			@Override
			protected void isFocused() {
				
			}
			
			@Override
			protected void isClicked() {
				//userInterface.GuiControle.setSecondMenu(userInterface.GuiControle.comMenu);
			}
		};
		add(b1);
		b1.setText("Setup");
		Button b2 = new Button(200,20,MenuConstants.filePathButton+"B") {
			
			@Override
			protected void uppdate() {
				
			}
			
			@Override
			protected void isFocused() {
				
			}
			
			@Override
			protected void isClicked() {
				//userInterface.GuiControle.setSecondMenu(userInterface.GuiControle.telemetry);
			}
		};
		add(b2);
		b2.setTextColor(Button.gray);
		b2.setText("Project");
		
		Button b3 = new Button(370,20,MenuConstants.filePathButton+"G") {
			
			@Override
			protected void uppdate() {
				
			}
			
			@Override
			protected void isFocused() {
				
			}
			
			@Override
			protected void isClicked() {
				//userInterface.GuiControle.setSecondMenu(userInterface.GuiControle.gpsStat);
			}
		};
		add(b3);
		b3.setText("Calibration");
		
		Button sbn1 = new Button(1200, 20, MenuConstants.filePathButton+"Gs") {
			private boolean ac = true;
			@Override
			protected void uppdate() {
			}
			
			@Override
			protected void isFocused() {
			}
			
			@Override
			protected void isClicked() {
				ac = !ac;
				GuiControle.toggleSideMenu(ac);
			}
		};
		add(sbn1);
		
		alarm = new AlarmkButton(540,20,"res/win/gui/spb/alb/") {
			//private boolean at;
			@Override
			protected void uppdate() {
			}
			@Override
			protected void isFocused() {
			}
			@Override
			protected void isClicked() {
				//userInterface.GuiControle.setSecondMenu(userInterface.GuiControle.warnMenu);
			}
		};
		alarm.setText("Owerview");
		add(alarm);
		
		shutDown = new Button(SeyprisMain.sizeX()-50, 20, "res/ima/cli/001") {
			
			@Override
			protected void uppdate() {
			}
			
			@Override
			protected void isFocused() {}
			
			@Override
			protected void isClicked() {
				GuiControle.addMenu(new ShutdownMenu(shutDown));
			}
		};
		add(shutDown);
		time = new DataFiled(700,10,180, 20, Color.white) {
			@Override
			protected void isClicked() {
			}
			@Override
			protected void uppdate() {
			}
		};
		add(time);
		timeMission = new DataFiled(700,35,180, 20, Color.white) {
			@Override
			protected void isClicked() {
			}
			@Override
			protected void uppdate() {
			}
		};
		add(timeMission);
		
		RAMfree = new DataFiled(900,10,130, 20, Color.blue) {
			@Override
			protected void isClicked() {
			}
			@Override
			protected void uppdate() {
			}
		};
		add(RAMfree);
		RAMtotal = new DataFiled(900,35,130, 20, Color.blue) {
			@Override
			protected void isClicked() {
			}
			@Override
			protected void uppdate() {
			}
		};
		add(RAMtotal);
		RAMmax = new DataFiled(1045,35,130, 20, Color.blue) {
			@Override
			protected void isClicked() {
			}
			@Override
			protected void uppdate() {
			}
		};
		add(RAMmax);
		
		Button gc = new Button(1045,10,"res/win/gui/cli/Gsk") {
			@Override
			protected void uppdate() {
			}
			@Override
			protected void isFocused() {
			}
			@Override
			protected void isClicked() {
				debug.Debug.println("* Starting manual GarbageColector...", debug.Debug.SUBCOM);
				runtime.gc();
				debug.Debug.println(" DONE!", debug.Debug.SUBCOM);
			}
		};
		gc.setText("GC");
		add(gc);
		
		runtime = Runtime.getRuntime();
		
		missionTime = System.currentTimeMillis()/1000;
		missionTime *= 1000;
		
		moveAble = false;
	}
	
	@Override
	protected void uppdateIntern() {
		if(lastUppdate/500 != System.currentTimeMillis()/500){
			lastUppdate = System.currentTimeMillis();
			time.setData(new java.text.SimpleDateFormat("E dd.MM.yy  HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
			timeMission.setData("T= + "+getMissionTime());
			
			RAMfree.setText("Free RAM "+getByteName(runtime.freeMemory()));
			RAMtotal.setText("Total RAM "+getByteName(runtime.totalMemory()));
			RAMmax.setText(" Max RAM "+getByteName(runtime.maxMemory()));
			
			double r = (double)(runtime.totalMemory()-runtime.freeMemory());
			PerformanceMenu.logRam(r/runtime.maxMemory(), r/runtime.totalMemory());
		}
	}

	@Override
	protected void paintIntern(Graphics g) {
		g.setColor(Button.gray);
		g.drawLine(0,60,SeyprisMain.sizeX(),60);
	}
	
	private String getByteName(long b){
		int i = 0;
		while (b>1000) {
			b /= 1000;
			i++;
		}
		switch (i) {
		case 0:
			return b+"Byte";
		case 1:
			return b+"kB";
		case 2:
			return b+"MB";
		case 3:
			return b+"GB";

		default:
			return "ERROR";
		}
	}
	
	private String getMissionTime(){
		long i = System.currentTimeMillis()-missionTime;
		String s = "";
		i /= 1000;
		s = ""+i%10+s; i/=10;
		s = ":"+i%6+s; i/=6;
		s = ""+i%10+s; i/=10;
		s = ":"+i%6+s; i/=6;
		s = ""+i%10+s; i/=10;
		s = ""+i%6+s; i/=10;
		
		return s;
	}
	
	public menu.AlarmkButton getAlb(){
		return alarm;
	}

}
