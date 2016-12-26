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
	}
	
	public void load(File f){
		//TODO
	}

	@Override
	public void save(String fp) {
		// TODO Auto-generated method stub
	}
}
