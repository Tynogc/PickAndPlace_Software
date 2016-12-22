package project;

import java.awt.Component;

import components.Footprint;

public class PCBcomponentToReel {

	public final PlacementInformation fp;
	public Component cp;
	
	public PCBcomponentToReel(final Footprint f){
		fp = new PlacementInformation(f);
	}
}
