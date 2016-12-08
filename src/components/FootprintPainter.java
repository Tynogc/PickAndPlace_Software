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
	
	public FootprintPainter(Footprint p, double scale, boolean fill, Color c){
		color = c;
		fillMode = fill;
		
		int[] size = FootprintSize.getSize(p, scale);
		xSize = size[0]*2;
		ySize = size[1]*2;
		
		buffer = new BufferedImage(xSize+2, ySize+2, BufferedImage.TYPE_INT_ARGB);
		Graphics g = buffer.getGraphics();
		g.translate(buffer.getWidth()/2, buffer.getHeight()/2);
				
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
		
		if(g == null){
			for (int i = 0; i < yi.length; i++) {
				if(Math.abs(xi[i])>xSize)
					xSize = Math.abs(xi[i]);
				if(Math.abs(yi[i])>ySize)
					ySize = Math.abs(yi[i]);
			}
			
			return;
		}
		
		Polygon poly = new Polygon(xi,yi,4);
		g.setColor(color);
		if(fillMode){
			g.fillPolygon(poly);
		}else{
			g.drawPolygon(poly);
		}
	}
}
