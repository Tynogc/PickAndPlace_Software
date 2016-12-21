package componetStorage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import components.Footprint;
import components.kiCad.KiCadFileImport;

public class SCloadSave {
	
	private static final String DIVIDER = " : ";
	private static final String COMMENT = "//";
	private static final String SUPER_PART = "***";
	
	private static final String pue_percent = "PUE_PERCENT";
	private static final String pue_ammount = "PUE_AMMOUNT";
	private static final String pue_retry = "PUE_RETRY";
	private static final String pue_action = "PUE_ACTION";
	
	private static final String check_Picked = "CHECK_PICKED";
	private static final String check_Reeled = "CHECK_REELED";
	private static final String check_Tape_Black = "CHECK_TAPE_BLACK";
	
	private static final String fp_hight = "FP_HIGHT";
	private static final String fp_orientation= "FP_ORIENTATION";
	private static final String fp_tool= "TOOL_TO_USE";
	
	private static final String SUPER_PART_CONTAINER = "CONTAINER";
	private static final String SUPER_PART_PCB = "PCB";
	
	private static final String COM_REEL = "REEL";
	private static final String COM_TUBE = "TUBE";
	private static final String COM_TRAY = "TRAY";

	public static StoredComponent load(File f){
		StoredComponent sc = new StoredComponent();
		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);

			sc.name = br.readLine();
			sc.id = br.readLine();
			String ui = br.readLine();
			sc.mark_pref = ui.contains("P");
			sc.mark_active = ui.contains("A");
			sc.mark_attention = !ui.contains("O");
			
			int currentPart = 0;
			String toFeedInFotprint = "";
			
			ui = br.readLine();
			while (ui != null) {
				if(ui.startsWith(COMMENT)){
					
				}else if(ui.startsWith(SUPER_PART)){
					if(ui.contains(DIVIDER+SUPER_PART_CONTAINER)){
						currentPart = 10;
						String[] st = ui.split(DIVIDER);
						if(st.length>=3){
							if(st[2].compareTo(COM_TRAY)==0)sc.container = StoredComponent.CONTAINER_TRAY;
							if(st[2].compareTo(COM_TUBE)==0)sc.container = StoredComponent.CONTAINER_TUBE;
							if(st[2].compareTo(COM_REEL)==0)sc.container = StoredComponent.CONTAINER_REEL;
						}
					}
					if(ui.contains(DIVIDER+SUPER_PART_PCB))currentPart = 20;
				}else{
					if(currentPart == 0)loadNormal(ui, sc);
					if(currentPart == 10);//TODO
					if(currentPart == 20)toFeedInFotprint+=ui;
				}
				ui = br.readLine();
			}
			Footprint fp = new KiCadFileImport(toFeedInFotprint).footprint;
			sc.fp = fp;
			
			br.close();
		} catch (FileNotFoundException e) {
			debug.Debug.println("*This File dosn't exist...", debug.Debug.COMERR);
		} catch (IOException e) {
			debug.Debug.println(e.getMessage(), debug.Debug.ERROR);
		} catch (Exception e) {
			debug.Debug.println("*ERROR reading Component: "+e.getMessage(), debug.Debug.ERROR);
		}
		
		return sc;
	}
	
	private static void loadNormal(String s, StoredComponent sc){
		String[] st = s.split(DIVIDER);
		if(s.startsWith(pue_action)) sc.pue_action = Integer.parseInt(st[1]);
		if(s.startsWith(pue_ammount)) sc.pue_Number = Integer.parseInt(st[1]);
		if(s.startsWith(pue_percent)) sc.pue_Percent = Double.parseDouble(st[1]);
		if(s.startsWith(pue_retry)) sc.pue_retry = st[1].compareToIgnoreCase("true")==0;
		if(s.startsWith(check_Picked)) sc.checkPartPicked = st[1].compareToIgnoreCase("true")==0;
		if(s.startsWith(check_Reeled)) sc.checkPartBeforPicking = Integer.parseInt(st[1]);
		if(s.startsWith(check_Tape_Black)) sc.reelBlack = st[1].compareToIgnoreCase("true")==0;
		if(s.startsWith(fp_hight)) sc.hight = Double.parseDouble(st[1]);
		if(s.startsWith(fp_orientation)) sc.partOrientation = Integer.parseInt(st[1]);
		if(s.startsWith(fp_tool)) sc.toolToUse = Integer.parseInt(st[1]);
	}
	
	public static void save(StoredComponent s, String fp){
		if(s.fp ==null){
			debug.Debug.println("* Can't save Component without Footprint!", debug.Debug.COMERR);
			return;
		}
		
		PrintWriter writer = null; 
		try { 
			writer = new PrintWriter(new FileWriter(fp)); 
			writer.println(s.name);
			writer.println(s.id);
			String q = "";
			if(s.mark_pref)q+="P";
			else q+="-";
			if(s.mark_active)q+="A";
			else q+="-";
			if(s.mark_attention)q+="W";
			else q+="O";
			writer.println(q);
			
			writer.println(COMMENT+"Values:");
			saveValues(s, writer);
			String ui = DIVIDER;
			if(s.container == StoredComponent.CONTAINER_REEL)ui+=COM_REEL;
			if(s.container == StoredComponent.CONTAINER_TUBE)ui+=COM_TUBE;
			if(s.container == StoredComponent.CONTAINER_TRAY)ui+=COM_TRAY;
			writer.println(SUPER_PART+DIVIDER+SUPER_PART_CONTAINER+ui);
			//TODO
			writer.println(SUPER_PART+DIVIDER+SUPER_PART_PCB);
			s.fp.save(writer);
		} catch (IOException e) { 
			debug.Debug.println(e.getMessage(), debug.Debug.ERROR);
		} finally { 
			if (writer != null){ 
				writer.flush(); 
				writer.close(); 
			} 
		}
	}
	
	private static void saveValues(StoredComponent s, PrintWriter p){
		p.println(pue_percent+DIVIDER+s.pue_Percent);
		p.println(pue_ammount+DIVIDER+s.pue_Number);
		p.println(pue_retry+DIVIDER+s.pue_retry);
		p.println(pue_action+DIVIDER+s.pue_action);
		p.println(check_Picked+DIVIDER+s.checkPartPicked);
		p.println(check_Reeled+DIVIDER+s.checkPartBeforPicking);
		p.println(check_Tape_Black+DIVIDER+s.reelBlack);
		p.println(COMMENT+"Footprint Values:");
		p.println(fp_hight+DIVIDER+s.hight);
		p.println(fp_orientation+DIVIDER+s.partOrientation);
		p.println(fp_tool+DIVIDER+s.toolToUse);
	}
}
