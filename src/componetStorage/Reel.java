package componetStorage;

import components.Footprint;
import components.FootprintDetectionHints;

public class Reel {

	public int numberOfParts;
	public final int id = 0;//TODO
	public int partOrientation;
	
	public int detectionHint;
	
	//Position
	public int positionOnGrid;
	public int grid;
	
	public Footprint fp;
	
	public FootprintDetectionHints getDetectionHints(){
		return new FootprintDetectionHints(fp, detectionHint);
	}
}
