package project;

import components.Footprint;

public class PlacementInformation {

	public String name;
	public String reference;
	public String value;
	
	/**
	 * xPos in mikroMeter
	 */
	public int xPos;
	/**
	 * yPos in mikroMeter
	 */
	public int yPos;
	
	public double rotation;
	
	public PlacementInformation(){};
	public PlacementInformation(Footprint f){
		this(f, f.reference);
	}
	public PlacementInformation(Footprint f, String ref){
		if(f.xPosInInt == Integer.MIN_VALUE){
			xPos = (int)(f.xPos*1000.0);
			yPos = (int)(f.yPos*1000.0);
		}else{
			xPos = f.xPosInInt;
			yPos = f.yPosInInt;
		}
		rotation = f.rotation;
		name = f.name;
		reference = ref;
		value = f.value;
	}
}
