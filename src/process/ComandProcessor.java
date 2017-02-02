package process;

import cnc.Abstract_CNC;

public class ComandProcessor {

	/**
	 * Processes Comand-Flow
	 */
	
	private Abstract_CNC cnc;
	
	public ComandProcessor(){
		
	}
	
	public synchronized void startProcess(){
		
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
