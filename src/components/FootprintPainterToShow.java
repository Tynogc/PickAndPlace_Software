package components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class FootprintPainterToShow {

	public BufferedImage buffer;
	
	public FootprintPainter fpp;
	
	public final String name;
	
	public FootprintPainterToShow(Footprint f, int size, boolean fill, Color c, boolean strings, int rot){
		int[] i = FootprintSize.getSize(f, 10.0);
		int u = i[0];
		if(i[1]>u)
			u = i[1];
		
		size-=20;
		double s = 10.0;
		if(size>u){
			if(size>u*2)
				s=20.0;
		}else{
			if(size*4>u){
				s=2.5; 
			}if(size*2>u){
				s=5.0;
			}
		}
		size+=20;
		
		fpp = new FootprintPainter(f, s, fill, c, rot);
		
		buffer = new BufferedImage(size, size, 
				BufferedImage.TYPE_INT_ARGB);
		
		Graphics g = buffer.getGraphics();
		g.drawImage(fpp.buffer, size/2-fpp.middleX, size/2-fpp.middleY, null);
		g.setColor(Color.blue);
		g.drawLine(size/2+10, size/2, size/2-10, size/2);
		g.drawLine(size/2, size/2+10, size/2, size/2-10);
		g.setColor(Color.white);
		g.drawString("Scale "+(int)(s*10.0)+"%", 10, 12);
		
		name = fpp.name;
		
		g.translate(size/2, size/2);
		g.setFont(main.Fonts.font12);
		PadList pad = f.pads;
		do {
			if(pad.pad != null)
				drawPadString(pad.pad, s, g, rot);
			
			pad = pad.next;
		} while (pad!= null);
		
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
		
		if(rot == FootprintPainter.ROTATION_LEFT){
			int u = x;
			x = y;
			y = -u;
		}
		if(rot == FootprintPainter.ROTATION_DOWN){
			x = -x;
			y = -y;
		}
		if(rot == FootprintPainter.ROTATION_RIGHT){
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
		g.drawString(st, x, y-1);
		g.drawString(st, x-1, y);
		g.drawString(st, x+1, y);
		g.drawString(st, x, y+1);
		g.setColor(Color.black);
		g.drawString(st, x, y);
	}
}
