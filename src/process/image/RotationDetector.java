package process.image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.imgproc.Imgproc;

public class RotationDetector {
	
	private RotatedRect rtr;
	
	public int numberOfPixel;

	public RotationDetector(){
		
	}
	
	public double getRotation(BufferedImage img, int d){
		
		int ammount = 0;
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				if((img.getRGB(x, y)&0xff)>d)
					ammount++;
			}
		}
		Point[] p = new Point[ammount];
		numberOfPixel = ammount;
		ammount = 0;
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				if((img.getRGB(x, y)&0xff)>d){
					p[ammount] = new Point(x,y);
					ammount++;
				}
			}
		}
		
		MatOfPoint2f mop = new MatOfPoint2f(p);
		
		rtr = Imgproc.minAreaRect(mop);
		
		return rtr.angle;
	}
	
	public int[] getCoR(){
		return new int[]{
				(int)rtr.center.x,
				(int)rtr.center.y
		};
	}
	
	public void paintResult(Graphics g){
		Point[] p = new Point[4];
		rtr.points(p);
		
		g.setColor(Color.green);
		for (int i = 0; i < p.length; i++) {
			g.drawLine((int)p[i].x, (int)p[i].y, (int)p[(i+1)%4].x, (int)p[(i+1)%4].y);
		}
		
		g.drawLine((int)rtr.center.x-5, (int)rtr.center.y, (int)rtr.center.x+5, (int)rtr.center.y);
		g.drawLine((int)rtr.center.x, (int)rtr.center.y-5, (int)rtr.center.x, (int)rtr.center.y+5);
	}
}
