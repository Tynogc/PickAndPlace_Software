package status;

public class PCBstate {

	public static final int NO_DATA = 0;
	public static final int EMPTY = 10;
	public static final int PROCESSING = 20;
	public static final int READY = 30;
	public static final int DONE = 40;
	
	public int pcbOne;
	public int pcbTwo = DONE;
}
