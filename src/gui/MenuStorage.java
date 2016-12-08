package gui;

import main.KeyListener;

public class MenuStorage {

	public static Overview overview;
	
	public MenuStorage(KeyListener key){
		debug.Debug.println("Building Menus...");
		overview = new Overview(key);
		debug.Debug.bootMsg("Overview", overview.getStatus());
	}
}
