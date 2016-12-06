package comunication;

public class FiElement{
	
	public FiElement next;
	public String str;
	public long time;
	
	public FiElement(String s, long t){
		str = s;
		next = null;
		time = t;
	}
	
	public void add(FiElement f){
		if(next == null) next = f;
		else next.add(f);
	}
	
	public int lenght(){
		if(next == null)return 1;
		return next.lenght()+1;
	}

}
