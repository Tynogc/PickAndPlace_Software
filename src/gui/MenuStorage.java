package gui;

import main.KeyListener;

public class MenuStorage {

	public static Overview overview;
	public static ProjectMenu project;
	public static MachineSetup setup;
	
	public MenuStorage(KeyListener key, process.ProcessControle pc){
		debug.Debug.println("Building Menus...");
		overview = new Overview(key, pc.partPlacementsystem);
		debug.Debug.bootMsg("Overview", overview.getStatus());
		project = new ProjectMenu();
		debug.Debug.bootMsg("Project", project.getStatus());
		setup = new MachineSetup();
		debug.Debug.bootMsg("Machine Setup", setup.getStatus());
	}
}
