package process;

public class IPCtoCNC {
	/**
	 * InternalPositioningComands to CNC-Machiene Translator
	 * 
	 * this class translate abstract Positioning and other IO to the Machine
	 */
	
	private final MachineLayout layout;
	
	public IPCtoCNC(final MachineLayout l){
		layout = l;
	}
}
