package project;

import components.Footprint;
import components.PCB;

public class ProjectFile {

	public final PCB pcb;
	
	public PCBcomponentToReel[] componentMatch;
	public final int size;
	
	public ProjectFile(final PCB p){
		pcb = p;
		
		Footprint[] fpa = pcb.getFParray();
		
		size = fpa.length;
		
		componentMatch = new PCBcomponentToReel[size];
		for (int i = 0; i < fpa.length; i++) {
			componentMatch[i] = new PCBcomponentToReel(fpa[i]);
		}
	}
}
