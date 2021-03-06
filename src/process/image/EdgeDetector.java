package process.image;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class EdgeDetector {

	public static final int UP = 10;
	public static final int DOWN = 20;
	public static final int LEFT = 30;
	public static final int RIGHT = 40;
	private static final int INVERT = 1000;
	private static final int UP_INVERT = UP+INVERT;
	private static final int DOWN_INVERT = DOWN+INVERT;
	private static final int LEFT_INVERT = LEFT+INVERT;
	private static final int RIGHT_INVERT = RIGHT+INVERT;
	
	public final int dirr;
	public final int delta;
	
	public int failedPoints;
	
	public EdgeDetector(int dir, int del){
		dirr = dir;
		delta = del;
	}
	
	public static void drawEdges(BufferedImage img, Point[][] p){
		Graphics g = img.getGraphics();
		g.setColor(Color.green);
		for (int i = 0; i < p.length; i++) {
			if(p[i]!=null)
			for (int j = 0; j < p[i].length; j++) {
				if(p[i][j]==null)continue;
				g.drawRect(p[i][j].x, p[i][j].y, 0, 0);
				if(j == 0){
					g.drawLine(p[i][j].x, p[i][j].y-10, p[i][j].x, p[i][j].y+10);
					g.drawLine(p[i][j].x+10, p[i][j].y, p[i][j].x-10, p[i][j].y);
				}
			}
		}
	}
	
	public double getAngleToY(Point[][] p){
		double d = 0.0;
		int amount = 0;
		if(p[0] == null)
			return 0.0;
		for (int i = 0; i < p[0].length; i++) {
			try{
				double f = getAngleToYIntern(p, i);
				System.out.println(Math.toDegrees(f));
				d+=f;
				amount++;
			}catch (NullPointerException e) {
				failedPoints++;
				System.out.println("null");
			}
		}
		System.out.println(amount);
		return d/amount;
	}
	
	private double getAngleToYIntern(Point[][] p, int i) throws NullPointerException{
		if(p.length<4){
			return new Vector(p[0][i], p[1][i]).getAngleToY();
		}
		Vector[] v = new Vector[]{
			new Vector(p[0][i], p[1][i]),
			new Vector(p[2][i], p[1][i]),
			new Vector(p[0][i], p[3][i]),
			new Vector(p[2][i], p[3][i])
		};
		double d = v[0].getAngleToY()+v[1].getAngleToY()+
				v[2].getAngleToY()+v[3].getAngleToY();
		
		return d/4;
	}
	
	public Point[][] getEdgeFrome(BufferedImage ima){
		return getEdgeFrome(ima, true);
	}
	
	public Point[][] getEdgeFrome(BufferedImage ima, boolean degree45){
		if(degree45)
		return new Point[][]{
				raster45(ima, dirr),
				raster45(ima, dirr+INVERT),
				raster27(ima, dirr),
				raster27(ima, dirr+INVERT)
		};
		return new Point[][]{
				raster27(ima, dirr),
				raster27(ima, dirr+INVERT)
		};
	}
	
	public Point[] raster45(BufferedImage ima, int d){
		int size = getSizeI(ima); 
		for (int i = 0; i < size; i++) {
			for (int j = 0; j <= i; j++) {
				if(searchPic(i-j, j, ima, d)){
					return getClosePoints(i-j, j, ima, d);
				}
			}
		}
		return null;
	}
	
	public Point[] raster27(BufferedImage ima, int d){
		int size = getSizeI(ima);
		if(getSizeJ(ima)>size)
			size = getSizeJ(ima);
		for (int i = 0; i < size*2; i++) {
			for (int j = 0; j <= i; j++) {
				if(searchPic(i-j, j/2, ima, d)){
					return getClosePoints(i-j, j/2, ima, d);
				}
			}
		}
		return null;
	}
	
	public Point[] getClosePoints(int i, int j, BufferedImage img, int d){
		Point[] p = new Point[10];
		int[] q = convert(i, j, img, d);
		p[0] = new Point(q[0], q[1]);
		
		//int size = getSizeJ(img);
		for (int k = 1; k < p.length; k++) {
			for (int l = j-10; l < j+10; l++) {
				p[k] = spearhead(i+k*4, l, 10, img, d);
				if(p[k]!= null)break;
			}
		}
		
		return p;
	}
	
	private Point spearhead(int i, int j, int dis, BufferedImage img, int d){
		int[] q;
		
		for (int k = dis; k >= 0; k--) {
			if(searchPic(i+k, j-k/2, img, d)){
				q = convert(i+k, j-k/2, img, d);
				return new Point(q[0], q[1]);
			}
			if(searchPic(i-k, j-k/2, img, d)){
				q = convert(i-k, j-k/2, img, d);
				return new Point(q[0], q[1]);
			}
		}
		
		return null;
	}
	
	private boolean searchPic(int i, int j, BufferedImage ima, int d){
		int[] q = convert(i, j, ima, d);
		if(q[0]<0||q[1]<0)
			return false;
		if(q[0]>=ima.getWidth()||q[1]>=ima.getHeight())
			return false;
		
		return (ima.getRGB(q[0], q[1])&0xff)>delta;
		
	}
	
	private int getSizeI(BufferedImage i){
		if(dirr == UP || dirr == DOWN){
			return i.getWidth();
		}
		return i.getHeight();
	}
	private int getSizeJ(BufferedImage i){
		if(dirr == UP || dirr == DOWN){
			return i.getHeight();
		}
		return i.getWidth();
	}
	
	private int[] convert(int i, int j, BufferedImage ima, int d){
		switch(d){
		case UP:
			return new int[]{i,j};
		case UP_INVERT:
			return new int[]{ima.getWidth()-i,j};
		case DOWN:
			return new int[]{i,ima.getHeight()-j};
		case DOWN_INVERT:
			return new int[]{ima.getWidth()-i,ima.getHeight()-j};
		case LEFT:
			return new int[]{j,i};
		case LEFT_INVERT:
			return new int[]{j,ima.getHeight()-i};
		case RIGHT:
			return new int[]{ima.getWidth()-j,i};
		case RIGHT_INVERT:
			return new int[]{ima.getWidth()-j,ima.getHeight()-i};
		}
		return null;
	}
	
	public double getAngleToPosition(Point[][] p){
		double r = getAngleToY(p);
		switch(dirr){
		case UP:
			return Math.PI/2-r;
		case DOWN:
			return Math.PI/2-r;
		case LEFT:
			return -r;
		case RIGHT:
			return -r;
		default:
			return Double.NaN;
		}
	}
}
