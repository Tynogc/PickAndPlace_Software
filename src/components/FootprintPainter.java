package components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

import componetStorage.StoredComponent;

public class FootprintPainter {

	public BufferedImage buffer;
	private final boolean fillMode;
	private final Color color ;
	
	private int xSize;
	private int ySize;
	
	public final int middleX;
	public final int middleY;
	
	public final String name;
	
	public static final int ROTATION_UP = StoredComponent.ORIENTATION_0_DEGREE;
	public static final int ROTATION_RIGHT = StoredComponent.ORIENTATION_90_DEGREE;
	public static final int ROTATION_DOWN = StoredComponent.ORIENTATION_180_DEGREE;
	public static final int ROTATION_LEFT = StoredComponent.ORIENTATION_270_DEGREE;
	
	public FootprintPainter(Footprint p, double scale, boolean fill, Color c){
		this(p,scale,fill,c,ROTATION_UP);
	}
	
	public FootprintPainter(Footprint p, double scale, boolean fill, Color c, int rotation){
		name = p.name;
		
		color = c;
		fillMode = fill;
		
		int[] size = FootprintSize.getSize(p, scale);
		xSize = size[0]*2;
		ySize = size[1]*2;
		
		buffer = new BufferedImage(xSize+3, ySize+3, BufferedImage.TYPE_INT_ARGB);
		Graphics g = buffer.getGraphics();
		middleX = buffer.getWidth()/2;
		middleY = buffer.getHeight()/2;
		g.translate(middleX, middleY);
				
		PadList pad = p.pads;
		do {
			if(pad.pad != null)
				paintPad(pad.pad, scale, g, rotation, p.rotation);
			
			pad = pad.next;
		} while (pad!= null);
	}
	
	private void paintPad(Pad p, double s, Graphics g, int rot, double rotOfFp){
		int[][] xyi = FootprintSize.getPadPosition(p, s, rotOfFp);
		int[] xi = xyi[0];
		int[] yi = xyi[1];
		
		if(rot == ROTATION_UP){}
		else if(rot == ROTATION_LEFT){
			int u;
			for (int i = 0; i < xi.length; i++) {
				u = xi[i];
				xi[i] = yi[i];
				yi[i] = -u;
			}
		}else if(rot == ROTATION_RIGHT){
			int u;
			for (int i = 0; i < xi.length; i++) {
				u = xi[i];
				xi[i] = -yi[i];
				yi[i] = u;
			}
		}else if(rot == ROTATION_DOWN){
			for (int i = 0; i < xi.length; i++) {
				xi[i] = -xi[i];
				yi[i] = -yi[i];
			}
		}
		
		g.setColor(color);
		Polygon poly = new Polygon(xi,yi,4);
		g.setColor(color);
		if(fillMode){
			g.fillPolygon(poly);
		}else{
			g.drawPolygon(poly);
		}
	}
}
