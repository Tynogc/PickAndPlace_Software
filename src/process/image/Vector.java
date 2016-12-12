package process.image;

public class Vector {

	public Point p1;
	public Point p2;
	
	public Vector(Point p1, Point p2){
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public Vector(int x1, int y1, int x2, int y2){
		p1 = new Point(x1, y1);
		p2 = new Point(x2, y2);
	}
	
	public double getAngleToY(){
		//double d = (double)(p2.y-p1.y)/(double)(p2.x-p1.x);
		return Math.atan2((double)(p2.x-p1.x),(double)(p2.y-p1.y));
	}
}
