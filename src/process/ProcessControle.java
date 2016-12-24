package process;

public class ProcessControle {

	public ComandProcessor comandProcess;
	public final MachineLayout machineLayout;
	private final IPCtoCNC cncOutput;
	private final IPC_Simulation cncSimulation;
	public final PartPlacement partPlacementsystem;
	
	public ProcessControle(){
		cncOutput = new IPCtoCNC() {//FIXME init correct
		};
		
		cncSimulation = new IPC_Simulation();
		
		partPlacementsystem = new PartPlacement();
		machineLayout = new MachineLayout();
	}
	
	/////System comands
	
	public final void pressRun(){
		
	}
	
	public final void pressStop(){
		
	}
	
	public final void pressReset(){
		
	}
}
