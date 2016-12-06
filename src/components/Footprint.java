package components;

public class Footprint {

	public PadList pads;
	
	public String name;
	
	public Footprint(){
		pads = new PadList();
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