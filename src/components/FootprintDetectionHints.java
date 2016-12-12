package components;

public class FootprintDetectionHints {

	public final Footprint fp;
	
	public final int dirNumbers;
	public static final int DETECT_ONLY_DOWN = 12;
	public static final int DETECT_BOTH_SIDES_NARROW = 13;
	public static final int DETECT_BOTH_SIDES_WIDE = 14;
	public static final int DETECT_ALL_SIDES_PADDED = 15;
	public static final int DETECT_LEFT_RIGHT_PADDED = 16;
	
	/**
	 * 0:UP 1:RIGHT 2:DOWN 3:LEFT
	 */
	public boolean[] direction;
	public boolean[] only27;
	
	public FootprintDetectionHints(Footprint f, int dir){
		dirNumbers = dir;
		fp = f;
		
		switch (dirNumbers) {
		case DETECT_ONLY_DOWN:
			direction = new boolean[]{false, false, true, false};
			only27 = new boolean[]{false, false, false ,false};
			break;
		case DETECT_BOTH_SIDES_WIDE:
			direction = new boolean[]{true, false, true, false};
			only27 = new boolean[]{false, false, false ,false};
			break;
		case DETECT_BOTH_SIDES_NARROW:
			direction = new boolean[]{true, false, true, false};
			only27 = new boolean[]{true, false, true ,false};
			break;
		case DETECT_ALL_SIDES_PADDED:
			direction = new boolean[]{true, true,true,true};
			only27 = new boolean[]{true, true,true,true};
			break;
		case DETECT_LEFT_RIGHT_PADDED:
			direction = new boolean[]{false, true,false,true};
			only27 = new boolean[]{false, false,false,false};
			break;

		default:
			break;
		}
	}
}
