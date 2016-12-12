package process.image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import process.PicProcessingStatLoader;
import process.Spindel;
import utility.VectorAdvanced;
import components.FootprintDetectionHints;
import components.FootprintPainter;

public class PartDetector {

	public BufferedImage imToProcess;
	public BufferedImage imToShow;
	public BufferedImage imProcessedToShow;
	
	public final int crX;
	public final int crY;
	
	public double cgX;
	public double cgY;
	public double rotation;
	
	public static boolean newAlgorithm = true;
	
	public PartDetector(int centerRotX, int centerRotY){
		crX = centerRotX;
		crY = centerRotY;
	}
	
	public void look(BufferedImage im, FootprintDetectionHints f, PicProcessingStatLoader pps){
		imToShow = Filter.colorFilter(im, pps.filterColor, pps.contastValue);
		imToProcess = Filter.amplifyContrast(imToShow, 50, pps.cornerNumber);
		
		int i = 0;
		double r = 0.0;
		
		RotationDetector rotDec = new RotationDetector();
		r = rotDec.getRotation(imToProcess, 50);
		debug.Debug.println(" OpenCv R: "+r);
		r = Math.toRadians(r);
		rotation = r;
		
		Point[][][] p = new Point[4][1][1];
		
		if(!newAlgorithm){
			for (int j = 0; j < 4; j++) {
				if(f.direction[j]){
					i++;
					EdgeDetector ed;
					if(j == 0)ed = new EdgeDetector(EdgeDetector.UP, 50);
					else if(j == 1)ed = new EdgeDetector(EdgeDetector.RIGHT, 50);
					else if(j == 2)ed = new EdgeDetector(EdgeDetector.DOWN, 50);
					else ed = new EdgeDetector(EdgeDetector.LEFT, 50);
					p[j] = ed.getEdgeFrome(imToProcess, !f.only27[j]);
					double rf = ed.getAngleToPosition(p[j]);
					
					System.out.println("U "+Math.toDegrees(rf));
					r+=rf;
				}
			}
			r /= i;
			rotation  = r;
			System.out.println("G "+Math.toDegrees(r));
		}
		debug.Debug.println(" Angle: "+Math.toDegrees(r));
		imProcessedToShow = new BufferedImage(imToShow.getWidth(), imToShow.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D)imProcessedToShow.getGraphics();
		g2d.translate(crX, crY);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g2d.rotate(-r);
		g2d.drawImage(imToShow, -crX, -crY, null);
		
		int[] cmg = CgDetector.getAdvCG(imProcessedToShow, 50);
		
		PicturFpComparison pfc = new PicturFpComparison(f.fp, pps.scaling, cmg[0], cmg[1]);
		pfc.search(imProcessedToShow, PicturFpComparison.UP_DOWN, 50);
		pfc.search(imProcessedToShow, PicturFpComparison.LEFT_RIGHT, 50);
		pfc.search(imProcessedToShow, PicturFpComparison.UP_DOWN, 50);
		pfc.search(imProcessedToShow, PicturFpComparison.LEFT_RIGHT, 50);
		
		FootprintPainter fpp = new FootprintPainter(f.fp, pps.scaling, false, Color.red);
		
		//1st painting:
		g2d.rotate(r);
		g2d.setColor(Color.yellow);
		g2d.drawLine(-10, 0, 10, 0);
		g2d.drawLine(0, -10, 0, 10);
		g2d.translate(-crX, -crY);
		g2d.setColor(new Color(100,100,100,100));
		for (int j = 0; j < im.getHeight(); j+=5) {
			g2d.drawLine(0, j, im.getWidth(), j);
		}
		g2d.setColor(Color.blue);
		g2d.drawLine(-10+cmg[0], cmg[1], 10+cmg[0], cmg[1]);
		g2d.drawLine(cmg[0], -10+cmg[1], cmg[0], 10+cmg[1]);
		cmg[0] = pfc.xPos;
		cmg[1] = pfc.yPos;
		g2d.setColor(Color.red);
		g2d.drawLine(-10+cmg[0], cmg[1], 10+cmg[0], cmg[1]);
		g2d.drawLine(cmg[0], -10+cmg[1], cmg[0], 10+cmg[1]);
		g2d.drawImage(fpp.buffer, pfc.xPos-fpp.middleX, pfc.yPos-fpp.middleY, null);
		//1st painting END
		
		VectorAdvanced crToCg = new VectorAdvanced(crX, crY, pfc.xPos, pfc.yPos);
		crToCg.rotate(rotation);
		cgX = crToCg.x2;
		cgY = crToCg.y2;
		
		//2nd painting:
		g2d = (Graphics2D)imToShow.getGraphics();
		if(!newAlgorithm){
			for (int j = 0; j < p.length; j++) {
				if(p[j]==null)continue;
				EdgeDetector.drawEdges(imToShow, p[j]);
			}
		}
		g2d.setColor(Color.yellow);
		g2d.drawLine(-10+crX, crY, 10+crX, crY);
		g2d.drawLine(crX, -10+crY, crX, 10+crY);
		g2d.setColor(Color.red);
		g2d.drawLine(-15+(int)cgX, (int)cgY, 15+(int)cgX, (int)cgY);
		g2d.drawLine((int)cgX, -15+(int)cgY, (int)cgX, 15+(int)cgY);
		
		rotDec.paintResult(g2d);
		
		g2d.translate(crToCg.x2, crToCg.y2);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g2d.rotate(rotation);
		//g2d.drawImage(fpp.buffer, -fpp.middleX, -fpp.middleY, null);
		
	}
}
