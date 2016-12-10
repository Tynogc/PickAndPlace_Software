package process;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import components.FootprintDetectionHints;
import components.FootprintSize;
import components.kiCad.KiCadFileImport;
import process.image.PartDetector;

public class Spindel {

	public final int centerOfRotationX;
	public final int centerOfRotationY;
	
	private BufferedImage visualised;
	
	public byte status;
	public static final byte STATUS_EMPTY = 10;
	public static final byte STATUS_PICKED = 20;
	public static final byte STATUS_ACTIVE = 14;
	public static final byte STATUS_PLACED = 30;
	public static final byte STATUS_ERROR = -22;
	
	private FootprintDetectionHints currentFPDH;
	
	public Spindel(){
		centerOfRotationX = 0;
		centerOfRotationY = 0;//TODO
		
		currentFPDH = new FootprintDetectionHints(
				new KiCadFileImport(new File("SOT-23.kicad_mod")).footprint,
				FootprintDetectionHints.DETECT_BOTH_SIDES_WIDE);
	}
	
	public void paintVisu(Graphics g, int x, int y){
		if(visualised != null)
			g.drawImage(visualised, x, y, null);
	}
	
	public void processImage(BufferedImage i, int offsetX, int offsetY, PicProcessingStatLoader pps){
		int[] mp = FootprintSize.getSize(currentFPDH.fp, pps.scaling);
		int crX = centerOfRotationX+offsetX;
		int crY = centerOfRotationY+offsetY;
		int ax = crX-mp[0];
		int ay = crY-mp[1];
		int bx = mp[0]*2;
		int by = mp[1]*2;
		if(ax<0)ax = 0;
		if(ay<0)ay = 0;
		crX -= ax;
		crY -= ay;
		if(ax+bx>=i.getWidth())bx = i.getWidth()-ax;
		if(ay+by>=i.getHeight())by = i.getHeight()-ay;
		
		BufferedImage worker = i.getSubimage(ax,ay,bx,by);
		
		PartDetector pd = new PartDetector(crX,crY);
		pd.look(worker, currentFPDH, pps);
		
		visualised = pd.imProcessedToShow;
	}
}
