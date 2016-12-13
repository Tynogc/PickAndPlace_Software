package componetStorage;

import components.Footprint;
import components.FootprintDetectionHints;

public class StoredComponent {

	public int numberOfParts;
	public final int id = 0;//TODO
	
	public int detectionHint;
	
	public String name;
	
	//PUE detection
	public double pue_Percent;//Percentage of Matching to Footprint
	public int pue_Number;//Min number of white Pixels
	
	//Footprint
	public Footprint fp;
	public double hight;
	public String footprintFilepath;
	
	public int partOrientation;
	public static final int ORIENTATION_0_DEGREE = 0;//UP
	public static final int ORIENTATION_90_DEGREE = 1;//RIGHT
	public static final int ORIENTATION_180_DEGREE = 2;//DOWN
	public static final int ORIENTATION_270_DEGREE = 3;//LEFT
	
	//Tool
	public int toolToUse;
	
	public FootprintDetectionHints getDetectionHints(){
		return new FootprintDetectionHints(fp, detectionHint, pue_Percent, pue_Number);
	}
}
