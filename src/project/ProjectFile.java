package project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import components.Footprint;
import components.PCB;

public class ProjectFile implements utility.SaveAble{

	public final PCB pcb;
	
	public PCBcomponentToReel[] componentMatch;
	public final int size;
	
	private static final String DIVIDER = " :#: ";
	
	public String projectName = "";
	public String filepath = "";
	
	public static final String FILE_END = ".project";
	public static final String FILE_PATH = "user/project/";
	
	public ProjectFile(final PCB p){
		pcb = p;
		
		Footprint[] fpa = pcb.getFParray();
		
		size = fpa.length;
		
		componentMatch = new PCBcomponentToReel[size];
		for (int i = 0; i < fpa.length; i++) {
			componentMatch[i] = new PCBcomponentToReel(fpa[i]);
		}
		
		for (int i = 0; i < fpa.length; i++) {
			for (int j = 0; j < fpa.length; j++) {
				if(i==j)continue;
				if(fpa[i].reference.compareTo(fpa[j].reference)==0){
					debug.Debug.println(" Footprint reference match! "+fpa[j].reference, debug.Debug.WARN);
				}
			}
		}
	}
	
	public void load(File f){
		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			
			String s;
			do {
				s = br.readLine();
				if(s != null){
					if(s.startsWith("//"))continue;
					String[] st = s.split(DIVIDER);
					loadSplit(st);
				}
			} while (s != null);
		} catch (Exception e) {
			debug.Debug.println("*ERROR loading Project: "+e.getMessage(), debug.Debug.ERROR);
		}
	}
	
	private void loadSplit(String[] s){
		if(s.length<2)
			return;
		if(s[1].length()<=1)
			return;
		for (int i = 0; i < componentMatch.length; i++) {
			if(componentMatch[i].fp.reference.compareTo(s[0])==0){
				/*File[] f = new utility.FileSearch(new File("user/components")).searchFor(s[1]);
				if(f.length==0){
					debug.Debug.println("Component with name "+s[1]+" not found!", debug.Debug.ERROR);
					return;
				}
				if(f.length>1)
					debug.Debug.println("Component with name "+s[1]+" has multiple Files!", debug.Debug.WARN);
				*/
				componentMatch[i].cpId = s[1];
				if(s.length>=3)
					componentMatch[i].cpName = s[2];
				
				return;
			}
		}
		debug.Debug.println("Reference with name "+s[0]+" not found!", debug.Debug.WARN);
	}

	@Override
	public void save(String fp) {
		PrintWriter writer = null; 
		try { 
			writer = new PrintWriter(new FileWriter(fp)); 
			writer.println("//"+projectName);
			writer.println("//Generated with Seypris PNP-Controle v"+main.SeyprisMain.VERSION +" (C)Sven T. Schneider");
			
			for (int i = 0; i < componentMatch.length; i++) {
				writer.println(componentMatch[i].fp.reference+DIVIDER+componentMatch[i].cpId+DIVIDER+
						componentMatch[i].cpName);
			}
			
			writer.flush();
			writer.close();
		}catch(Exception e){
			debug.Debug.println("*ERROR saving Project: "+e.getMessage(), debug.Debug.ERROR);
		}
	}
}
