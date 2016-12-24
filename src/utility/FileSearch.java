package utility;

import java.io.File;

public class FileSearch {

	private File directory;
	private FileQue result;
	
	public FileSearch(File directiory){
		this.directory = directiory;
	}
	
	public File[] searchFor(String s){
		result = new FileQue(null);
		iteratePosition(directory, s);
		
		int a = result.count();
		File[] fa = new File[a];
		result.parsToArray(fa, 0);
		return fa;
	}
	
	public void iteratePosition(File f, String search){
		FileQue dirs = new FileQue(null);
		File[] fa = f.listFiles();
		if(fa == null)
			return;
		for (int i = 0; i < fa.length; i++) {
			if(fa[i].isDirectory()){
				dirs.add(fa[i]);
			}else if(fa[i].getName().startsWith(search)){
				result.add(fa[i]);
			}
		}
		
		while (dirs != null) {
			if(dirs.f != null)
				iteratePosition(dirs.f, search);
			dirs = dirs.next;
		}
	}
}
class FileQue{
	public File f;
	public FileQue next;
	
	public FileQue(File f){
		this.f = f;
	}
	
	public void add(File f){
		if(this.f==null){
			this.f = f;
		}else if(next != null){
			next.add(f);
		}else{
			next = new FileQue(f);
		}
	}
	
	public int count(){
		if(f == null){
			if(next != null)
				return next.count();
			return 0;
		}
		if(next != null)
			return next.count()+1;
		return 1;
	}
	
	public void parsToArray(File[] f, int c){
		if(this.f != null){
			f[c] = this.f;
			c++;
		}
		if(next != null)
			next.parsToArray(f, c);
	}
}
