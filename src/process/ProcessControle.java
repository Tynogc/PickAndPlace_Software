package process;

import setup.SetupControle;
import cnc.Abstract_CNC;
import cnc.CNC_Simulation;

public class ProcessControle {

	//Can process placement-Comand-Que
	public ComandProcessor comandProcess;
	//The Positioning-Layout of the Machine
	public final MachineLayout machineLayout;
	
	private final IPCtoCNC ipcToCnc;
	//The real CNC_Machine
	private final Abstract_CNC cnc_Machine;
	//A CNC-Simulator, for placement-Simulation
	private final CNC_Simulation simulation;
	
	public final PartPlacement partPlacementsystem;
	public MachinePCBview pcbView;
	
	public SetupControle setup;
	
	public ProcessControle(){
		machineLayout = new MachineLayout();
		debug.Debug.bootMsg(" Loading M-Layout", machineLayout.load());
		
		ipcToCnc = new IPCtoCNC(machineLayout);
		
		partPlacementsystem = new PartPlacement();
		
		cnc_Machine = new Abstract_CNC();
		simulation = new CNC_Simulation();
		
		setup = new SetupControle(machineLayout);
	}
	
	/////System comands
	
	public final void pressRun(){
		
	}
	
	public final void pressStop(){
		
	}
	
	public final void pressReset(){
		
	}
}
