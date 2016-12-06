package gui;

public class MenuStorage {

	public static Overview overview;
	
	public MenuStorage(){
		debug.Debug.println("Building Menus...");
		overview = new Overview();
		debug.Debug.bootMsg("Overview", overview.getStatus());
	}
}
