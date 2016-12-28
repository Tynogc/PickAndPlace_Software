package project;

import java.awt.Component;

import components.Footprint;

public class PCBcomponentToReel {

	//Informations for Footprint positioning
	public final PlacementInformation fp;
	
	//Component information
	public String cpName;
	public String cpId;
	
	public PCBcomponentToReel(final Footprint f){
		fp = new PlacementInformation(f);
		cpName = "";
		cpId = "";
	}
}
