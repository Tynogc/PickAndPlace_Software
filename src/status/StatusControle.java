package status;

public class StatusControle {

	public static StatusControle stc;
	
	public PCBstate pcbs;
	public Position pos;
	
	public StatusControle(){
		stc = this;
		
		pcbs = new PCBstate();
		pos = new Position();
		
		debug.Debug.bootMsg("Statusreport", 0);
	}
}
