package components;

public class PCB {

	public FPList components;
	public String name = "";
	
	public PCB(){
		components = new FPList(null);
	}
	
	public void addFootPrint(Footprint f){
		components.add(f);
	}
	
}

class FPList{
	public FPList next;
	public Footprint fp;
	
	public FPList(Footprint f){
		fp = f;
	}
	
	public void add(Footprint f){
		if(fp == null){
			fp = f;
			return;
		}
		if(next == null){
			next = new FPList(f);
			return;
		}
		
		next.add(f);
	}
}
