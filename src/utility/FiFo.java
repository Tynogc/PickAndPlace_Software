package utility;

import java.util.concurrent.Semaphore;

public class FiFo {
	
	private FiElement first;
	private Semaphore sema;
	
	private boolean enabled;
	
	public FiFo(){
		first = null;
		sema = new Semaphore(1);
		enabled = true;
	}
	
	public String out(){
		try {
			sema.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(first != null){
			String s = first.str;
			first = first.next;
			sema.release();
			return s;
		}
		sema.release();
		return null;
	}
	
	public FiElement outElement(){
		try {
			sema.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(first != null){
			FiElement s = first;
			first = first.next;
			s.next = null;
			sema.release();
			return s;
		}
		sema.release();
		return null;
	}
	
	public boolean contains(){
		try {
			sema.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		boolean b = first != null; 
		sema.release();
		return b;
	}
	
	public void in(String s, long t){
		try {
			sema.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(!enabled){}
		else if(first == null){
			first = new FiElement(s, t);
		}else{
			first.add(new FiElement(s, t));
		}
		sema.release();
	}
	public void in(String s){
		in(s, 0);
	}
	
	public int lenght(){
		if(first == null)return 0;
		return first.lenght();
	}
	
	public void setEnable(boolean b){
		try {
			sema.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		enabled = b;
		sema.release();
	}
	
	/*public String[] getStringArr(){
		try {
			sema.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int l = lenght();
		String[] s = new String[l];
		for (int i = 0; i < s.length; i++) {
			s[i] = out();
		}
		sema.release();
		return s;
	}*/
}
