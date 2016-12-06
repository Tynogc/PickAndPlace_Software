package process.image;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class CgDetector {

	public Point p;
	public Vector v;
	public Vector boundary;
	
	public final int delta;
	
	public CgDetector(BufferedImage im, int del){
		delta = del;
		int[] q = getAdvCG(im, delta);
		this.p = new Point(q[0], q[1]);
		boundary = new Vector(q[2],q[3],q[4],q[5]);
		int[] p = CgDetector.getAdvCG(im, 50, q[2], q[3], q[4], q[1]);
		q = CgDetector.getAdvCG(im, 50, q[2], q[1], q[4], q[5]);
		v = new Vector(p[0], p[1], q[0], q[1]);
	}
	
	public void paintCG(BufferedImage i){
		Graphics g = i.getGraphics();
		g.setColor(Color.green);
		g.drawLine(p.x+30, p.y, p.x-30, p.y);
		g.drawLine(p.x, p.y+30, p.x, p.y-30);
	}
	
	public void paintBoundary(BufferedImage i){
		Graphics g = i.getGraphics();
		g.setColor(Color.red);
		g.drawLine(boundary.p1.x, 0, boundary.p1.x, i.getHeight());
		g.drawLine(0, boundary.p1.y, i.getWidth(), boundary.p1.y);
		g.drawLine(boundary.p2.x, 0, boundary.p2.x, i.getHeight());
		g.drawLine(0, boundary.p2.y, i.getWidth(), boundary.p2.y);
	}
	
	public void paintAdvCG(BufferedImage i){
		Graphics g = i.getGraphics();
		g.setColor(Color.CYAN);
		g.drawLine(v.p1.x, v.p1.y, v.p2.x, v.p2.y);
		g.setColor(Color.blue);
		g.drawLine(v.p1.x+10, v.p1.y, v.p1.x-10, v.p1.y);
		g.drawLine(v.p1.x, v.p1.y+10, v.p1.x, v.p1.y-10);
		g.drawLine(v.p2.x+10, v.p2.y, v.p2.x-10, v.p2.y);
		g.drawLine(v.p2.x, v.p2.y+10, v.p2.x, v.p2.y-10);
	}
	
	public static int[] getAdvCG(BufferedImage b, int n){
		return getAdvCG(b, n, 0, 0, 10000, 10000);
	}
	
	public static int[] getAdvCG(BufferedImage b, int n, int xs, int ys, int xm, int ym){
		int xu = 10000;
		int yu = 10000;
		int xd = 0;
		int yd = 0;
		for (int i = xs; i < b.getWidth() && i<=xm; i++) {
			boolean something = false;
			for (int j = ys; j < b.getHeight()&&j <= ym; j++) {
				if((b.getRGB(i, j)&0xff)>n){
					something = true;
					if(yu>j)yu = j;
					if(yd<j)yd=j;
				}
			}
			if(something){
				if(xu>i)xu = i;
				if(xd<i)xd=i;
			}
		}
		return new int[]{
				(xd-xu)/2+xu,(yd-yu)/2+yu,xu,yu,xd,yd
		};
	}
}
