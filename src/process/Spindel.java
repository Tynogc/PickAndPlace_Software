package process;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.Semaphore;

import components.FootprintDetectionHints;
import components.FootprintSize;
import components.kiCad.KiCadFileImport;
import componetStorage.Reel;
import process.image.PartDetector;

public class Spindel {

	public final int centerOfRotationX;
	public final int centerOfRotationY;
	
	private BufferedImage visualised;
	private BufferedImage visualisedWoRc;
	
	public byte status;
	public static final byte STATUS_EMPTY = 10;
	public static final byte STATUS_PICKED = 20;
	public static final byte STATUS_ACTIVE = 14;
	public static final byte STATUS_PLACED = 30;
	public static final byte STATUS_ERROR = -22;
	
	private Semaphore sema;
	
	private Reel reel;
	
	public Spindel(){
		centerOfRotationX = 0;
		centerOfRotationY = 0;//TODO
		
		sema = new Semaphore(1);
		
		reel = new Reel();
		reel.fp = new KiCadFileImport(new File("PLCC-44.kicad_mod")).footprint;
		reel.detectionHint = FootprintDetectionHints.DETECT_ALL_SIDES_PADDED;
		gui.GuiControle.addMenu(new gui.subMenu.FootprintSetup(300, 100, reel));
	}
	
	public void paintVisu(Graphics g, int x, int y){
		try {
			sema.acquire();
		} catch (InterruptedException e) {
			return;
		}
		if(visualised != null)
			g.drawImage(visualised, x, y, null);
		if(visualisedWoRc != null)
			g.drawImage(visualisedWoRc, x, y+500, null);
		sema.release();
	}
	
	public void processImage(BufferedImage i, int offsetX, int offsetY, PicProcessingStatLoader pps){
		FootprintDetectionHints currentFPDH = reel.getDetectionHints();
		int[] mp = FootprintSize.getSize(currentFPDH.fp, pps.scaling);
		int crX = centerOfRotationX+offsetX;
		int crY = centerOfRotationY+offsetY;
		int ax = crX-mp[0]*2;
		int ay = crY-mp[1]*2;
		int bx = mp[0]*4;
		int by = mp[1]*4;
		if(ax<0)ax = 0;
		if(ay<0)ay = 0;
		crX -= ax;
		crY -= ay;
		if(ax+bx>=i.getWidth())bx = i.getWidth()-ax;
		if(ay+by>=i.getHeight())by = i.getHeight()-ay;
		
		BufferedImage worker = i.getSubimage(ax,ay,bx,by);
		
		PartDetector pd = new PartDetector(crX,crY);
		pd.look(worker, currentFPDH, pps);
		
		
		try {
			sema.acquire();
		} catch (InterruptedException e) {
			return;
		}
		visualised = pd.imProcessedToShow;
		visualisedWoRc = pd.imToShow;
		
		//Drawing variables
		Graphics g = visualisedWoRc.getGraphics();
		g.setFont(main.Fonts.font12);
		g.setColor(Color.yellow);
		g.drawString("CR: x"+pd.crX+"px ["
				+utility.DoubleWriter.fixedKommata((double)pd.crX/pps.scaling, 3)+"mm] y"
				+pd.crY+"px ["
				+utility.DoubleWriter.fixedKommata((double)pd.crY/pps.scaling, 3)+"mm]", 5, 12);
		
		g.setColor(Color.white);
		g.drawString("CG: x"+(int)(pd.cgX-pd.crX)+"px ["
				+utility.DoubleWriter.fixedKommata((double)(pd.cgX-pd.crX)/pps.scaling, 3)+"mm] y"
				+(int)(pd.cgY-pd.crY)+"px ["
				+utility.DoubleWriter.fixedKommata((double)(pd.cgY-pd.crY)/pps.scaling, 3)+"mm]", 5, 24);
		g.drawString("Rotation "+utility.DoubleWriter.fixedKommata(Math.toDegrees(pd.rotation),1), 5, 36);
		sema.release();
	}
}
