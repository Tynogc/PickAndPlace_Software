package process;

import cnc.Abstract_CNC;

public class ComandProcessor {

	/**
	 * Processes Command-Flow
	 */
	
	private IPCtoCNC cnc;
	
	public ComandProcessor(){
		
	}
	
	public synchronized void startProcess(){
		
	}
	
	public void setTranslator(IPCtoCNC c){
		cnc = c;
	}
	
}
