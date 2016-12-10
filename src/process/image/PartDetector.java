package process.image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import process.PicProcessingStatLoader;
import process.Spindel;
import components.FootprintDetectionHints;

public class PartDetector {

	public BufferedImage imToProcess;
	public BufferedImage imToShow;
	public BufferedImage imProcessedToShow;
	
	public final int crX;
	public final int crY;
	
	public double cgX;
	public double cgY;
	
	public PartDetector(int centerRotX, int centerRotY){
		crX = centerRotX;
		crY = centerRotY;
	}
	
	public void look(BufferedImage im, FootprintDetectionHints f, PicProcessingStatLoader pps){
		imToShow = Filter.colorFilter(im, pps.filterColor, pps.contastValue);
		imToProcess = Filter.amplifyContrast(imToShow, 50, pps.cornerNumber);
		
		int i = 0;
		double r = 0.0;
		Point[][][] p = new Point[4][1][1];
		
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
				if(rf>Math.PI/2)
					rf-=Math.PI;
				if(rf<-Math.PI/2)
					rf+=Math.PI;
				System.out.println("U "+Math.toDegrees(rf));
				r+=rf;
			}
		}
		r /= i;
		System.out.println("G "+Math.toDegrees(r));
		debug.Debug.println(" Angle: "+Math.toDegrees(r));
		imProcessedToShow = new BufferedImage(imToShow.getWidth(), imToShow.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D)imProcessedToShow.getGraphics();
		g2d.translate(crX, crY);
		g2d.rotate(-r);
		g2d.drawImage(imToShow, -crX, -crY, null);
		g2d.rotate(r);
		g2d.setColor(Color.green);
		g2d.drawRect(-1, -1, 2, 2);
		g2d.translate(-crX, -crY);
		g2d.setColor(new Color(100,100,100,100));
		for (int j = 0; j < im.getHeight(); j+=5) {
			g2d.drawLine(0, j, im.getWidth(), j);
		}
	}
}
