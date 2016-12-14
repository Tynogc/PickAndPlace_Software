package gui;

import java.awt.Graphics;

import debug.DebugMenu;
import menu.AbstractMenu;
import menu.MenuControle;

public class GuiControle {

	private main.MouseListener mouse;
	
	private MenuControle debugMenu;
	private MenuControle topMenu;
	private MenuControle bottomMenu;
	private static MenuControle sideMenu;
	private static MenuControle middleMenu;
	private static MenuControle[] menus;
	
	private BottomMenu btm;
	private debug.DebugMenu dbm;
	private static SideMenu sdm;
	private static PerformanceMenu pfm;
	private static boolean sdmActiv;
	
	private ToolTip toolTip;
	
	public GuiControle(main.MouseListener m, main.KeyListener k, boolean observer){
		mouse = m;
		topMenu = new MenuControle();
		debugMenu = new MenuControle();
		menus = new MenuControle[10];
		for (int i = 0; i < menus.length; i++) {
			menus[i] = new MenuControle();
		}
		
		if(observer){
			dbm = new DebugMenu();
			dbm.xPos = 10;
			dbm.yPos = 500;
			debugMenu.setActivMenu(dbm);
			topMenu.setActivMenu(new comunication.LinkedInMenu(k));
		}else{
			bottomMenu = new MenuControle();
			sideMenu = new MenuControle();
			middleMenu = new MenuControle();
			
			dbm = new DebugMenu();
			sdm = new SideMenu();
			btm = new BottomMenu();
			bottomMenu.setActivMenu(btm);
			debugMenu.setActivMenu(sdm);
			pfm = new PerformanceMenu();
			topMenu.setActivMenu(new TopMenu());
			debugMenu.setActivMenu(dbm);
			sideMenu.setActivMenu(sdm);
			sdmActiv = true;
			
			k.fMenus = btm;
		}
		
		toolTip = new ToolTip();
	}
	
	public boolean loop(){
		boolean left = mouse.left||mouse.leftClicked;
		boolean right = mouse.right || mouse.rightClicked;
		boolean clicked = false;
		
		if(toolTip.mouseState(mouse.x, mouse.y, left, right)){
			left = false;
			right = false;
			clicked = true;
		}
		
		if(debugMenu.mouseState(mouse.x, mouse.y, left, right)){
			left = false;
			right = false;
			clicked = true;
		}
		
		for (int i = menus.length-1; i >= 0; i--) {
			if(menus[i].mouseState(mouse.x, mouse.y, left, right)){
				left = false;
				right = false;
				clicked = true;
			}
		}
		
		if(topMenu.mouseState(mouse.x, mouse.y, left, right)){
			left = false;
			right = false;
			clicked = true;
		}
		
		if(bottomMenu != null)
		if(bottomMenu.mouseState(mouse.x, mouse.y, left, right)){
			left = false;
			right = false;
			clicked = true;
		}
		if(sideMenu != null)
		if(sideMenu.mouseState(mouse.x, mouse.y, left, right)){
			left = false;
			right = false;
			clicked = true;
		}
		if(middleMenu != null)
		if(middleMenu.mouseState(mouse.x, mouse.y, left, right)){
			left = false;
			right = false;
			clicked = true;
		}
		
		return clicked;
	}
	
	public void paint(Graphics g){
		topMenu.paintYou(g);
		if(bottomMenu != null)
			bottomMenu.paintYou(g);
		if(sideMenu != null)
			sideMenu.paintYou(g);
		if(middleMenu != null)
			middleMenu.paintYou(g);
		debugMenu.paintYou(g);
		for (int i = 0; i < menus.length; i++) {
			menus[i].paintYou(g);
		}
		toolTip.paintYou(g);
	}
	
	public static boolean addMenu(AbstractMenu  m){
		for (int i = 0; i < menus.length; i++) {
			if(!menus[i].isActiv()){
				menus[i].setActivMenu(m);
				return true;
			}
		}
		return false;
	}
	
	public static void toggleSideMenu(boolean b){
		if(b == sdmActiv)return;
		if(b)sideMenu.setActivMenu(sdm);
		else sideMenu.setActivMenu(pfm);
		sdmActiv = b;
	}
	
	public static void setMiddleMenu(AbstractMenu m){
		if(middleMenu != null)
			middleMenu.setActivMenu(m);
	}
	
	public void setdebugMenu(AbstractMenu m){
		debugMenu.setActivMenu(m);
	}
}
