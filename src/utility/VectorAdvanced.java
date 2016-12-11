package utility;

public class VectorAdvanced {

	public double x1;
	public double x2;
	public double y1;
	public double y2;
	
	public VectorAdvanced(double x1, double y1, double x2, double y2){
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public void rotate(double rad){
		double x = x2-x1;
		double y = y2-y1;
		
		x2 = x * Math.cos(rad) - y * Math.sin(rad)+x1;
	    y2 = x * Math.sin(rad) + y * Math.cos(rad)+y1;
	}
	
}
