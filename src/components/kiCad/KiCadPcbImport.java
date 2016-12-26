package components.kiCad;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import components.Footprint;
import components.PCB;

public class KiCadPcbImport {
	
	public PCB pcb;

	public static final int COMPATIBLE_VERSION_LOW = 4;
	public static final int COMPATIBLE_VERSION_HIGH = 4;
	
	public KiCadPcbImport(File f){
		debug.Debug.println("Import KiCad-PCB...");
		Runtime.getRuntime().gc();
		String operate = "";
		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);

			String s = null;
			do {
				s = br.readLine();
				if(s != null)
					operate += s;
			} while (s != null);

			br.close();
		} catch (FileNotFoundException e) {
			debug.Debug.println(e.getMessage(), debug.Debug.ERROR);
			return;
		} catch (IOException e) {
			debug.Debug.println(e.getMessage(), debug.Debug.ERROR);
		}
		
		pcb = new PCB();
		
		try {
			process(operate);
		} catch (Exception e) {
			debug.Debug.println("* ERROR loading PCB: "+e.toString(), debug.Debug.ERROR);
		}
		debug.Debug.println("DONE!");
	}
	
	private void process(String s){
		s = s.substring(s.indexOf('(')+1);
		while(s.length()>1){
			char c = s.charAt(0);
			if(c == '('){
				int u = processHight(s.substring(1));
				process1stStage(s.substring(1, u+1));
				s = s.substring(u+1);
				continue;
			}
			s = s.substring(1);
		}
	}
	
	private void process1stStage(String s){
		if(s.length()>30)
			System.out.println(s.substring(0,30));
		else
			System.out.println(s);
		if(s.startsWith("version ")){
			try{
				int i = Integer.parseInt(s.substring(8));
				if(i>COMPATIBLE_VERSION_LOW){
					debug.Debug.println("This PCB-File may not be compatible!");
				}
				if(i<COMPATIBLE_VERSION_HIGH){
					debug.Debug.println("This PCB-File may not be compatible!");
				}
			}catch (Exception e) {
				debug.Debug.println(e.toString());
			}
		}else if(s.startsWith("module ")){
			Footprint fp = new KiCadFileImport("("+s).footprint;
			pcb.addFootPrint(fp);
			System.out.println("fp loaded!");
		}
	}
	
	private int processHight(String s){
		int q = 0;
		int i = 0;
		while(q>=0){
			char c = s.charAt(i);
			if(c =='(')q++;
			if(c ==')')q--;
			
			i++;
		}
		return i-1;
	}
}
