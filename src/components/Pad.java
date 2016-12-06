package components;

public class Pad {

	public double xPos;
	public double yPos;
	public double xSize;
	public double ySize;
	
	public int type;
	public static final int TYPE_RECT = 0;
	public static final int TYPE_CIRCLE = 1;
	
	public double rotation;
	
	public String name = "";
	
	public Pad(double x, double y, double xs, double ys, double r){
		type = TYPE_RECT;
		xPos = x;
		yPos = y;
		xSize = xs;
		ySize = ys;
	}
	
	public Pad(){
		
	}
}
