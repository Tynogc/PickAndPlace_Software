package gui.subMenu;

import project.PCBcomponentToReel;

public abstract class ComponentBrowserProject extends ComponentBrowser{

	private final PCBcomponentToReel cptr;
	
	public ComponentBrowserProject(int x, int y, PCBcomponentToReel s) {
		super(x, y);
		titel = "Asign Component for "+s.fp.reference;
		cptr = s;
		focusTeb();
	}
	
	@Override
	protected void focusTeb() {
		if(cptr != null)
			teb.setText(cptr.cpId);
		super.focusTeb();
	}

}
