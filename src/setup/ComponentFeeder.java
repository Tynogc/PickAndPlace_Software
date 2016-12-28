package setup;

import componetStorage.StoredComponent;

public abstract class ComponentFeeder {

	public final int size;
	public final int xPos;
	public final int yPos;
	
	public StoredComponent[] holds;
	
	public ComponentFeeder(int x, int y, int s){
		size = s;
		xPos = x;
		yPos = y;
		holds = new StoredComponent[size];
	}
}
