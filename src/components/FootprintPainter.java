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
		
		PadList pad = p.pads;
		do {
			if(pad.pad != null)
				paintPad(pad.pad, scale, null);
			
			pad = pad.next;
		} while (pad!= null);
		
		xSize*=2;
		ySize*=2;
		
		buffer = new BufferedImage(xSize+10, ySize+10, BufferedImage.TYPE_INT_ARGB);
		Graphics g = buffer.getGraphics();
		g.translate(buffer.getWidth()/2, buffer.getHeight()/2);
				
		pad = p.pads;
		do {
			if(pad.pad != null)
				paintPad(pad.pad, scale, g);
			
			pad = pad.next;
		} while (pad!= null);
	}
	
	private void paintPad(Pad p, double s, Graphics g){
		double x = p.xPos*s;
		double y = p.yPos*s;
		double xs = p.xSize*s/2;
		double ys = p.ySize*s/2;
		double r = Math.toRadians(p.rotation);
		double ri = Math.toRadians(p.rotation+90);
		
		int[] xi = new int[4];
		int[] yi = new int[4];
		xi[0] = (int)(x+Math.cos(r)*xs+Math.sin(r)*ys);
		yi[0] = (int)(y+Math.cos(ri)*xs+Math.sin(ri)*ys);
		xi[1] = (int)(x-Math.cos(r)*xs+Math.sin(r)*ys);
		yi[1] = (int)(y-Math.cos(ri)*xs+Math.sin(ri)*ys);
		xi[3] = (int)(x+Math.cos(r)*xs-Math.sin(r)*ys);
		yi[3] = (int)(y+Math.cos(ri)*xs-Math.sin(ri)*ys);
		xi[2] = (int)(x-Math.cos(r)*xs-Math.sin(r)*ys);
		yi[2] = (int)(y-Math.cos(ri)*xs-Math.sin(ri)*ys);
		
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
