package gui;

import java.awt.Color;
import java.awt.Graphics;

import debug.Debug;
import main.Fonts;
import main.SeyprisMain;
import menu.Button;
import menu.DataFiled;

public class BottomMenu extends menu.AbstractMenu{
	
	private Button run;
	private Button stop;
	
	private Button com;
	
	private Button map;
	
	private Button tele;
	
	private Button prog;
	
	private Button reset;
	
	private Button jog;
	
	private Button settings;
	
	private DataFiled looker;
	
	private int fKeyMarker;
	private long fKeyMarkerT;
	
	private boolean runBool;
	
	public BottomMenu() {
		super(0,SeyprisMain.sizeY()-160,800,120);
		
		moveAble = false;
		closeOutside = false;
		
		run = new Button(380,40,"res/ima/cli/spb/RUN") {
			@Override
			protected void uppdate() {
			}
			@Override
			protected void isFocused() {
			}
			@Override
			protected void isClicked() {
				B_runStop();
			}
		};
		add(run);
		run.setSubtext("START Rover Operation");
		stop = new Button(380,40,"res/ima/cli/spb/STOP") {
			@Override
			protected void uppdate() {
			}
			@Override
			protected void isFocused() {
			}
			@Override
			protected void isClicked() {
				B_runStop();
			}
		};
		add(stop);
		stop.setSubtext("STOP Rover Operation");
		
		com = new Button(20,40,"res/ima/cli/spb/E") {
			@Override
			protected void uppdate() {
			}
			@Override
			protected void isFocused() {
			}
			@Override
			protected void isClicked() {
				//B_CommMenu();
			}
		};
		add(com);
		com.setSubtext("Communication Menu");
		
		map = new Button(140,40,"res/ima/cli/spb/E") {
			@Override
			protected void uppdate() {
			}
			@Override
			protected void isFocused() {
			}
			@Override
			protected void isClicked() {
				//B_Map();
			}
		};
		add(map);
		map.setSubtext("Map View");
		
		tele = new Button(260,40,"res/ima/cli/spb/E") {
			@Override
			protected void uppdate() {
			}
			@Override
			protected void isFocused() {
			}
			@Override
			protected void isClicked() {
				//B_Telem();
			}
		};
		add(tele);
		tele.setSubtext("Telemetry");
		
		prog = new Button(500,40,"res/ima/cli/spb/E") {
			@Override
			protected void uppdate() {
			}
			@Override
			protected void isFocused() {
			}
			@Override
			protected void isClicked() {
				//B_ProgMenu();
			}
		};
		add(prog);
		prog.setSubtext("Rover Log");
		
		jog = new Button(620,40,"res/ima/cli/spb/E") {
			@Override
			protected void uppdate() {
			}
			@Override
			protected void isFocused() {
			}
			@Override
			protected void isClicked() {
				//B_Jog();
			}
		};
		add(jog);
		jog.setSubtext("Jog Menu");
		
		settings = new Button(860,40,"res/ima/cli/spb/settings") {
			@Override
			protected void uppdate() {
			}
			@Override
			protected void isFocused() {
			}
			@Override
			protected void isClicked() {
				//B_Settings();
			}
		};
		add(settings);
		settings.setSubtext("Settings");
		
		reset = new Button(740,40,"res/ima/cli/spb/RESET") {
			@Override
			protected void uppdate() {
			}
			@Override
			protected void isFocused() {
			}
			@Override
			protected void isClicked() {
				//B_Reset();
			}
		};
		add(reset);
		reset.setSubtext("SSK Reset");
		
		looker = new DataFiled(2,0,990,20,Color.blue) {
			@Override
			protected void uppdate() {
			}
			@Override
			protected void isClicked() {
				//GuiControle.setSecondMenu(GuiControle.warnMenu);
			}
		};
		add(looker);
		
		swapRunStop(true);
	}
	
	public DataFiled getLooker(){return looker;}

	@Override
	protected void uppdateIntern() {
		
	}

	@Override
	protected void paintIntern(Graphics g) {
		g.setFont(Fonts.font14);
		g.setColor(Color.white);
		g.drawString("F8", 900, 155);
		g.drawString("F7", 780, 155);
		g.drawString("F6", 660, 155);
		g.drawString("F5", 540, 155);
		g.drawString("F4", 420, 155);
		g.drawString("F3", 300, 155);
		g.drawString("F2", 180, 155);
		g.drawString("F1", 60, 155);
		
		if(System.currentTimeMillis()-fKeyMarkerT <600){
			g.setColor(new Color(70,70,255,250-(int)(System.currentTimeMillis()-fKeyMarkerT)/3));
			g.drawRect(19+(120*(fKeyMarker-1)), 39, 102, 102);
			g.drawRect(18+(120*(fKeyMarker-1)), 38, 104, 104);
		}
	}
	
	public void fKeyPressed(int key){
		fKeyMarker = key;
		fKeyMarkerT = System.currentTimeMillis();
		switch (key) {
		case 5:
			//B_ProgMenu();
			break;
		case 2:
			//B_Map();
			break;
		case 3:
			//B_Telem();
			break;
		case 1:
			//B_CommMenu();
			break;
		case 4:
			B_runStop();
			break;
		case 8:
			//B_Settings();
			break;
		case 7:
			//B_Reset();
			break;
		case 6:
			//B_Jog();
			break;

		default:
			break;
		}
		Debug.println("* F-Key Pressed: F"+key, Debug.COM);
	}
	
	private void B_runStop(){
		if(runBool){
			swapRunStop(false);
			//ComunicationControl.com.send(">RUN");
		}else{
			//ComunicationControl.com.send("*STOP");
		}
	}

	public void setDisabled(boolean d){
		run.setDisabled(d);
		stop.setDisabled(d);
		reset.setDisabled(d);
	}
	
	public void swapRunStop(boolean runOn){
		runBool = runOn;
		run.setVisible(runOn);
		stop.setVisible(!runOn);
	}

}
