package componetStorage;

import components.Footprint;
import components.FootprintDetectionHints;

public class StoredComponent implements utility.SaveAble{

	public String name = "EMPTY";
	//Unique ID of the Part
	public String id;
	
	public boolean mark_active;
	public boolean mark_pref;
	public boolean mark_attention;
	
	public int numberOfParts;
	
	public int detectionHint;
	
	//PUE detection
	public double pue_Percent;//Percentage of Matching to Footprint
	public int pue_Number;//Min number of white Pixels
	//PUE action
	public boolean pue_retry;
	public int pue_action;
	public static final int PUE_ACTION_STOP = 0;
	public static final int PUE_ACTION_NOTE = 1;
	public static final int PUE_ACTION_IGNORE = 2;
	public static final int PUE_ACTION_PLACE = 3;
	
	//Footprint
	public Footprint fp;
	public double hight;
	
	public int partOrientation;
	public static final int ORIENTATION_0_DEGREE = 0;//UP
	public static final int ORIENTATION_90_DEGREE = 1;//RIGHT
	public static final int ORIENTATION_180_DEGREE = 2;//DOWN
	public static final int ORIENTATION_270_DEGREE = 3;//LEFT
	
	//Tool
	public int toolToUse;
	
	//Picker check
	public boolean checkPartPicked = true;
	public boolean reelBlack;
	public int checkPartBeforPicking;
	public static final int CHECK_FIRST = 0;
	public static final int CHECK_EVERY = 1;
	public static final int CHECK_NEVER = 2;
	
	public Reel reel;
	public Tube tube;
	public Tray tray;
	public int container;
	public static final int CONTAINER_REEL = 0;
	public static final int CONTAINER_TUBE = 1;
	public static final int CONTAINER_TRAY = 2;
	
	public FootprintDetectionHints getDetectionHints(){
		return new FootprintDetectionHints(fp, detectionHint, pue_Percent, pue_Number);
	}

	@Override
	public void save(String fp) {
		SCloadSave.save(this, fp);
	}
	
	
}
