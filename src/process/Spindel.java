package process;

import java.awt.image.BufferedImage;

public class Spindel {

	public final int centerOfRotationX;
	public final int centerOfRotationY;
	
	private BufferedImage visualised;
	
	public byte status;
	public static final byte STATUS_EMPTY = 10;
	public static final byte STATUS_PICKED = 20;
	public static final byte STATUS_ACTIVE = 14;
	public static final byte STATUS_PLACED = 30;
	public static final byte STATUS_ERROR = -22;
	
	public Spindel(){
		centerOfRotationX = 0;
		centerOfRotationY = 0;//TODO
		
		
	}
	
	public void processImage(BufferedImage i, double offsetX, double offsetY){
		
	}
}
