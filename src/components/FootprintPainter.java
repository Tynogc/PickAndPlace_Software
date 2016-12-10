package components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

public class FootprintPainter {

	public BufferedImage buffer;
	private final boolean fillMode;
	private final Color color ;
	
	private int xSize;
	private int ySize;
	
	public final int middleX;
	public final int middleY;
	
	public FootprintPainter(Footprint p, double scale, boolean fill, Color c){
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
				paintPad(pad.pad, scale, g);
			
			pad = pad.next;
		} while (pad!= null);
	}
	
	private void paintPad(Pad p, double s, Graphics g){
		int[][] xyi = FootprintSize.getPadPosition(p, s);
		int[] xi = xyi[0];
		int[] yi = xyi[1];
		
		Polygon poly = new Polygon(xi,yi,4);
		g.setColor(color);
		if(fillMode){
			g.fillPolygon(poly);
		}else{
			g.drawPolygon(poly);
		}
	}
}
