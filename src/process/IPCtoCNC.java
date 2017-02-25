package process;

import cnc.Abstract_CNC;

public class IPCtoCNC {
	/**
	 * InternalPositioningComands to CNC-Machiene Translator
	 * 
	 * this class translate abstract Positioning and other IO to the Machine
	 */
	
	private final MachineLayout layout;
	
	private Abstract_CNC cnc;
	
	public IPCtoCNC(final MachineLayout l){
		layout = l;
	}
	
	public void setCNC(Abstract_CNC c){
		cnc = c;
		debug.Debug.println("Changed command-CNC to ",debug.Debug.COM);
		if(c.isSimulation())
			debug.Debug.println(c.toString(),debug.Debug.MASSAGE);
		else
			debug.Debug.println(c.toString(),debug.Debug.TEXT);
	}
}
