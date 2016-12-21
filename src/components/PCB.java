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
	
	public Footprint[] getFParray(){
		int s = 0;
		FPList f = components;
		while (f!=null) {
			if(f.fp!=null)
				s++;
			f = f.next;
		}
		Footprint[] fpa = new Footprint[s];
		s = 0;
		f = components;
		while (f!=null) {
			if(f.fp!=null){
				fpa[s] = f.fp;
				s++;
			}
			f = f.next;
		}
		
		return fpa;
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