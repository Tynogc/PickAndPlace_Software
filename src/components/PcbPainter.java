package components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class PcbPainter {

	private BufferedImage[] bua;
	private Position[] pos;
	
	public PcbPainter(PCB p, double scale, Color c, boolean fill){
		int size = 0;
		FPList f = p.components;
		while (f != null) {
			if(f.fp != null)
				size++;
			f = f.next;
		}
		bua = new BufferedImage[size];
		pos = new Position[size];
		size = 0;
		f = p.components;
		while (f != null) {
			if(f.fp != null){
				FootprintPainter fpp = new FootprintPainter(f.fp, scale, fill, c);
				bua[size] = fpp.buffer;
				pos[size] = new Position(f.fp.xPos*scale, f.fp.yPos*scale, Math.toRadians(f.fp.rotation),
						fpp.middleX, fpp.middleY, f.fp.reference);
				size++;
			}
			f = f.next;
		}
	}
	
	public void paint(Graphics2D g, boolean showName){
		for (int i = 0; i < bua.length; i++) {
			if(pos[i].x == Double.NaN)continue;
			g.translate(pos[i].x, pos[i].y);
			g.rotate(-pos[i].rot);
			g.drawImage(bua[i], -pos[i].midX, -pos[i].midY, null);
			g.rotate(pos[i].rot);
			g.setColor(Color.blue);
			g.drawLine(10, 0, -10, 0);
			g.drawLine(0, -10, 0, 10);
			if(showName){
				g.setColor(Color.white);
				g.drawString(pos[i].name, 1, -1);
				g.setColor(Color.yellow);
				g.drawLine(0,0,(int)(Math.cos(pos[i].rot)*15),(int)(Math.sin(pos[i].rot)*15));
			}
			g.translate(-pos[i].x, -pos[i].y);
		}
	}
}

class Position{
	public double x;
	public double y;
	public double rot;
	public int midX;
	public int midY;
	public String name;
	public Position(double xm, double ym, double rm, int mx, int my, String n){
		x = xm;
		y = ym;
		rot = rm;
		midX = mx;
		midY = my;
		name = n;
	}
}
