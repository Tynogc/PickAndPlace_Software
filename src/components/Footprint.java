package components;

import java.io.PrintWriter;

import project.PlacementInformation;

public class Footprint{

	public PadList pads;
	
	public String name;
	public String reference;
	public String value;
	
	/**
	 * xPos and yPos will be NaN unless loaded from PDB-File
	 * DO NOT USE for FP-Detection
	 */
	public double xPos;
	public int xPosInInt;
	/**
	 * xPos and yPos will be NaN unless loaded from PDB-File
	 * DO NOT USE for FP-Detection
	 */
	public double yPos;
	public int yPosInInt;
	public double rotation;
	
	public Footprint(){
		pads = new PadList();
		xPos = Double.NaN;
		yPos = Double.NaN;
		xPosInInt = Integer.MIN_VALUE;
		yPosInInt = Integer.MIN_VALUE;
	}
	
	public void addPad(Pad p){
		pads.addPad(p);
	}
	
	public void save(PrintWriter p){
		p.println("(module "+name+" (layer F.Cu)");
		if(!Double.isNaN(xPos) || rotation != 0.0)
			p.println("(at "+xPos+" "+yPos+" "+rotation+")");
		p.println("(fp_text reference "+reference+")");
		
		PadList pl = pads;
		while (pl!=null) {
			Pad pad = pl.pad;
			if(pad!=null){
				p.println("(pad "+pad.name+" smd rect (at "+pad.xPos+" "+pad.yPos+" "+
						(pad.rotation+rotation)+") (size "+pad.xSize+" "+pad.ySize+") (layers F.Cu))");
			}
			pl = pl.next;
		}
		p.println(")");
	}
}

class PadList{
	public Pad pad;
	public PadList next;
	
	public PadList(){
		
	}
	
	public PadList(Pad p){
		pad = p;
	}
	
	public void addPad(Pad p){
		if(pad == null){
			pad = p;
			return;
		}
		if(next == null){
			next = new PadList(p);
			return;
		}
		next.addPad(p);
	}
}