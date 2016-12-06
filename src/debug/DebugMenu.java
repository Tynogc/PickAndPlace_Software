package debug;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import menu.AbstractMenu;
import menu.Button;
import menu.DataFiled;
import menu.ScrollBar;

public class DebugMenu extends AbstractMenu{
	
	private BufferedImage buffer;
	private ScrollBar scb;
	
	private static int atX;
	private static int atY;
	private static int sizeX;
	private static int sizeY;
	private static int debugsizeY;
	
	private boolean externalFrameIsVisible = false;
	private Button externalFrame;
	private DataFiled filePath;
	
	public DebugMenu(){
		super(setPos(),atY,sizeX+200,sizeY+200);
		atX = 0;
		atY = 0;
		int scrollbarAmmount = Debug.sizeY/20;
		int scrollbarSAize = sizeY/20;
		
		scb = new ScrollBar(atX+sizeX, atY, sizeY, scrollbarSAize, scrollbarAmmount);
		add(scb);
		externalFrame = new Button(atX,atY+sizeY,"res/ima/cli/G") {
			
			@Override
			protected void uppdate() {
			}
			
			@Override
			protected void isFocused() {
			}
			
			@Override
			protected void isClicked() {
				externalFrameIsVisible = ! externalFrameIsVisible;
				debug.Debug.panel.setVisible(externalFrameIsVisible);
				if(externalFrameIsVisible){
					setText("Reduce External Frame");
				}else{
					setText("Show External Frame");
				}
			}
		};
		externalFrame.setText("Show External Frame");
		externalFrame.setTextColor(Button.blue);
		externalFrame.setBold(false);
		add(externalFrame);
		filePath = new DataFiled(atX+150,atY+sizeY,200,31,Color.black) {
			@Override
			protected void uppdate() {
			}
			@Override
			protected void isClicked() {
			}
		};
		add(filePath);
		filePath.setTextColor(Color.white);
		filePath.setText(Debug.logFilepath);
		Button logRam = new Button(atX+350,atY+sizeY,"res/ima/cli/Gs") {
			
			@Override
			protected void uppdate() {
			}
			@Override
			protected void isFocused() {
			}
			@Override
			protected void isClicked() {
				Debug.knowMemory();
				Debug.displayMemory(Debug.COM);
			}
		};
		logRam.setBold(false);
		logRam.setText("Log RAM");
		add(logRam);
		
		buffer = new BufferedImage(sizeX, debugsizeY, BufferedImage.TYPE_INT_RGB);
		Debug.menu = this;
		paintDebug();
		
		moveAble = false;
	}
	
	public void paintDebug(){
		Graphics g = buffer.getGraphics();
		g.drawImage(Debug.i1, 0, Debug.sizeY-Debug.line*10, null);
		g.drawImage(Debug.i2, 0, -Debug.line*10, null);
		g.drawImage(Debug.i2, 0, Debug.sizeY*2-Debug.line*10, null);
//		g.setColor(Color.black);
//		g.fillRect(0, Debug.sizeY+10, Debug.sizeX, 20);
//		g.setColor(Color.gray);
//		g.drawRect(0, Debug.sizeY+10, Debug.sizeX, 20);
		
	}

	@Override
	protected void uppdateIntern() {
		
	}

	@Override
	protected void paintIntern(Graphics g) {
		if(buffer != null){
			int scroll = scb.getScrolled()*20;
			g.drawImage(buffer.getSubimage(0, debugsizeY-sizeY-scroll, sizeX, sizeY), atX, atY, null);
		}
	}
	
	private static int setPos(){
		sizeX = Debug.sizeX+75;
		sizeY = 200;
		if(main.SeyprisMain.sizeY()>= 1000){
			sizeY = 300;
		}
		atX = main.SeyprisMain.sizeX()-600;
		atY = main.SeyprisMain.sizeY()-sizeY-50;
		debugsizeY = Debug.sizeY+15;
		return atX;
	}
}
