package componetStorage;

import components.Footprint;
import components.FootprintDetectionHints;

public class StoredComponent {

	public int numberOfParts;
	public final int id = 0;//TODO
	public int partOrientation;
	
	public int detectionHint;
	
	public String name;
	
	//PUE detection
	public double pue_Percent;//Percentage of Matching to Footprint
	public int pue_Number;//Min number of white Pixels
	
	//Footprint
	public Footprint fp;
	public int hight;
	
	public FootprintDetectionHints getDetectionHints(){
		return new FootprintDetectionHints(fp, detectionHint, pue_Percent, pue_Number);
	}
}
