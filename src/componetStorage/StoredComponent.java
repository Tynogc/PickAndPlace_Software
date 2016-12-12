package componetStorage;

import components.Footprint;
import components.FootprintDetectionHints;

public class StoredComponent {

	public int numberOfParts;
	public final int id = 0;//TODO
	public int partOrientation;
	
	public int detectionHint;
	
	//Footprint
	public Footprint fp;
	public int hight;
	
	public FootprintDetectionHints getDetectionHints(){
		return new FootprintDetectionHints(fp, detectionHint);
	}
}
