package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class Settings {

	private boolean warn;
	private boolean err;
	private String[] str;
	
	private static final String INBETWEEN = " : ";
	private static final String FILEPATH = "res/";
	private static final String DEFAULT_FILEPATH = "settings";
	
	////////////////////////////////////////////////////////////////////////////
	public static int CAMERA_TOP_DOWN;
	public static int CAMERA_PARTS;
	public static int CAMERA_VIEWER;
	public static String CAMERA_TD_RESOLUTION;
	public static String CAMERA_PP_RESOLUTION;
	public static String CAMERA_VW_RESOLUTION;
	public static int CAMERA_REDRAW_TIME;
	public static int CAMERA_REDRAW_PP;
	public static int CAMERA_REDRAW_TD;
	public static int CAMERA_REDRAW_VW;
	
	private Settings(){
		str = new String[100];
		warn = false;
		err = false;
	}
	
	public void loadFromFile(String fp){
		try {
			FileReader fr = new FileReader(fp);
			BufferedReader br = new BufferedReader(fr);

			int i = 0;
			for (; i < str.length; i++) {
				String s = br.readLine();
				if (s == null)
					break;
				if (s.length() < 1)
					break;
				str[i] = s;
			}
			debug.Debug.println(" System settings: " + i + " Lines loaded!");

			br.close();
		} catch (FileNotFoundException e) {
			debug.Debug.println(e.getMessage(), debug.Debug.ERROR);
			err = true;
			return;
		} catch (IOException e) {
			debug.Debug.println(e.getMessage(), debug.Debug.ERROR);
			err = true;
		}
	}
	
	public void saveToFile(String fp){
		PrintWriter writer = null; 
		try { 
			writer = new PrintWriter(new FileWriter(fp)); 
			for (int i = 0; i < str.length; i++) {
				if(str[i]==null)break;
				writer.println(str[i]);
			}
		} catch (IOException e) { 
			debug.Debug.println(e.getMessage(), debug.Debug.ERROR);
		} finally { 
			if (writer != null){ 
				writer.flush(); 
				writer.close(); 
			} 
		}
	}
	
	public void saveToString(){
		loadSave(true);
	}
	/**
	 * 
	 * @return 0:Sucess / 1:Error / 2: Warn
	 */
	public byte loadFromString(){
		loadSave(false);
		if(err)
			return 1;
		if(warn)
			return 2;
		return 0;
	}
	
	private void loadSave(boolean save){
		if(save)str[0] = "---Cameras---";
		CAMERA_PARTS = loadSaveInt(save, 1, "ID of Part-Pickup-Viewer", CAMERA_PARTS);
		CAMERA_TOP_DOWN = loadSaveInt(save, 2, "ID of Top-Down", CAMERA_TOP_DOWN);
		CAMERA_VIEWER = loadSaveInt(save, 3, "ID of Viewer-Camera", CAMERA_VIEWER);
		if(save)str[4] = "-Cameras: Repaint-";
		CAMERA_REDRAW_TIME = loadSaveInt(save, 5, "Update Delay (ms)", CAMERA_REDRAW_TIME);
		CAMERA_REDRAW_TD = loadSaveInt(save, 6, "Update Top Down", CAMERA_REDRAW_TD);
		CAMERA_REDRAW_PP = loadSaveInt(save, 7, "Update PartPlacement", CAMERA_REDRAW_PP);
		CAMERA_REDRAW_VW = loadSaveInt(save, 8, "Update Viewer", CAMERA_REDRAW_VW);
		if(save)str[9] = "-Cameras: Resulution-";
		CAMERA_TD_RESOLUTION = loadSaveString(save, 10, "Top Down", CAMERA_TD_RESOLUTION);
		CAMERA_PP_RESOLUTION = loadSaveString(save, 11, "Part Placement", CAMERA_PP_RESOLUTION);
		CAMERA_VW_RESOLUTION = loadSaveString(save, 12, "Viewer (Do not change!)", CAMERA_VW_RESOLUTION);
	}
	
	private String loadSaveString(boolean save, int pos, String prefix, String tls){
		if(save){
			str[pos] = prefix+INBETWEEN+tls;
			return tls;
		}else{
			try {
				String[] st = str[pos].split(INBETWEEN);
				if(!st[0].equals(prefix)){
					err = true;
					debug.Debug.println("* "+prefix+" Not found!", debug.Debug.ERROR);
				}
				if(st.length != 2){
					warn = true;
					return "";
				}
				if(st[1] == null){
					warn = true;
					return "";
				}
				if(st[1].length()<1){
					warn = true;
					return "";
				}
				return st[1];
			} catch (Exception e) {
				err = true;
				debug.Debug.println("* "+prefix+" Can't be loadded!", debug.Debug.ERROR);
				return "";
			}
		}
	}
	
	private int loadSaveInt(boolean save, int pos, String prefix, int tls){
		if(save){
			str[pos] = prefix+INBETWEEN+tls;
			return tls;
		}else{
			try {
				String[] st = str[pos].split(INBETWEEN);
				if(!st[0].equals(prefix)){
					err = true;
					debug.Debug.println("* "+prefix+" Not found!", debug.Debug.ERROR);
				}
				if(st.length != 2){
					warn = true;
					return -1;
				}
				if(st[1] == null){
					warn = true;
					return -1;
				}
				if(st[1].length()<1){
					warn = true;
					return -1;
				}
				int i = Integer.MIN_VALUE;
				i = Integer.parseInt(st[1]);
				if(i == Integer.MIN_VALUE){
					warn = true;
					return -1;
				}
				return i;
			} catch (Exception e) {
				err = true;
				debug.Debug.println("* "+prefix+" Can't be loadded!", debug.Debug.ERROR);
				return -1;
			}
		}
	}
	
	/**
	 * Loads the relevant Data frome the File in the memory
	 * @return load was succesfull
	 */
	public static byte load(String filepath){
		Settings rv = new Settings();
		rv.loadFromFile(FILEPATH+filepath+".set");
		if(rv.err){
			debug.Debug.println("* Can't load! File not found!", debug.Debug.SUBERR);
			return 1;
		}
		return rv.loadFromString();
	}
	
	public static byte load(){
		return load(DEFAULT_FILEPATH);
	}
	
	public static void save(String filePath){
		Settings rv = new Settings();
		rv.saveToString();
		rv.saveToFile(FILEPATH+filePath+".set");
	}
	
	public static void save(){
		save(DEFAULT_FILEPATH);
	}
	
	public static void repair(){
		save(DEFAULT_FILEPATH);
	}
}
