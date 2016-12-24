package project;

import java.awt.Component;

import components.Footprint;

public class PCBcomponentToReel {

	public final PlacementInformation fp;
	public String cpName;
	public String cpId;
	
	public PCBcomponentToReel(final Footprint f){
		fp = new PlacementInformation(f);
	}
}
