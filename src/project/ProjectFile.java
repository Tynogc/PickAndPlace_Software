package project;

import java.io.File;

import components.Footprint;
import components.PCB;

public class ProjectFile implements utility.SaveAble{

	public final PCB pcb;
	
	public PCBcomponentToReel[] componentMatch;
	public final int size;
	
	private static final String DIVIDER = " :#: ";
	
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
		//TODO
	}

	@Override
	public void save(String fp) {
		// TODO Auto-generated method stub
	}
}
