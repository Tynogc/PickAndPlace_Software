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
	
	public final String name;
	
	public static final int ROTATION_UP = 0;
	public static final int ROTATION_RIGHT = 1;
	public static final int ROTATION_DOWN = 2;
	public static final int ROTATION_LEFT = 3;
	
	public FootprintPainter(Footprint p, double scale, boolean fill, Color c){
		this(p,scale,fill,c,false,ROTATION_UP);
	}
	
	public FootprintPainter(Footprint p, double scale, boolean fill, Color c, boolean strings, int rotation){
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
				paintPad(pad.pad, scale, g, rotation);
			
			pad = pad.next;
		} while (pad!= null);
		
		if(!strings)
			return;
		
		g.setFont(main.Fonts.font12);
		pad = p.pads;
		do {
			if(pad.pad != null)
				drawPadString(pad.pad, scale, g, rotation);
			
			pad = pad.next;
		} while (pad!= null);
	}
	
	private void paintPad(Pad p, double s, Graphics g, int rot){
		int[][] xyi = FootprintSize.getPadPosition(p, s);
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
	
	private void drawPadString(Pad p, double s, Graphics g, int rot){
		String st = p.name;
		try {
			int u = Integer.parseInt(st);
			if(u != 1)
				return;
		} catch (Exception e) {}
		
		int x = (int)(p.xPos*s);
		int y = (int)(p.yPos*s);
		
		if(rot == ROTATION_LEFT){
			int u = x;
			x = y;
			y = -u;
		}
		if(rot == ROTATION_DOWN){
			x = -x;
			y = -y;
		}
		if(rot == ROTATION_RIGHT){
			int u = x;
			x = -y;
			y = u;
		}
		x-=3;
		y+=4;
		g.setColor(Color.white);
		g.drawString(st, x-1, y-1);
		g.drawString(st, x+1, y-1);
		g.drawString(st, x+1, y+1);
		g.drawString(st, x-1, y+1);
		g.setColor(Color.black);
		g.drawString(st, x, y);
	}
}
