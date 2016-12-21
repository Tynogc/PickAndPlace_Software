package componetStorage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class SCloadSave {
	
	private static final String DIVIDER = " : ";
	private static final String COMMENT = "//";
	
	private static final String pue_percent = "PUE_PERCENT";
	private static final String pue_ammount = "PUE_PERCENT";
	private static final String pue_retry = "PUE_PERCENT";
	private static final String pue_action = "PUE_PERCENT";
	
	private static final String check_Picked = "CHECK_PICKED";
	private static final String check_Reeled = "CHECK_REELED";
	private static final String check_Tape_Black = "CHECK_TAPE_BLACK";
	
	private static final String fp_hight = "FP_HIGHT";
	private static final String fp_orientation= "FP_ORIENTATION";
	private static final String fp_tool= "TOOL_TO_USE";

	public static StoredComponent load(File f){
		
		
		return null;
	}
	
	public static void save(StoredComponent s, String fp){
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
