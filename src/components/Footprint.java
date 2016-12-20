package components;

public class Footprint {

	public PadList pads;
	
	public String name;
	public String reference;
	
	/**
	 * xPos and yPos will be NaN unless loaded from PDB-File
	 * DO NOT USE for FP-Detection
	 */
	public double xPos;
	/**
	 * xPos and yPos will be NaN unless loaded from PDB-File
	 * DO NOT USE for FP-Detection
	 */
	public double yPos;
	public double rotation;
	
	public Footprint(){
		pads = new PadList();
		xPos = Double.NaN;
		yPos = Double.NaN;
	}
	
	public void addPad(Pad p){
		pads.addPad(p);
	}
}

class PadList{
	public Pad pad;
	public PadList next;
	
	public PadList(){
		
	}
	
	public PadList(Pad p){
		pad = p;
	}
	
	public void addPad(Pad p){
		if(pad == null){
			pad = p;
			return;
		}
		if(next == null){
			next = new PadList(p);
			return;
		}
		next.addPad(p);
	}
}