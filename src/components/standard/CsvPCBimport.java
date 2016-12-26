package components.standard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import components.Footprint;
import components.PCB;

public class CsvPCBimport {
	/**
	 * This Class only imports part-Placement Information!
	 * specctra-Files currently can't be used to create Footprints
	 */
	
	public PCB pcb;
	
	private int[] vals;
	
	private static final int VAL_REFF = 100;
	private static final int VAL_VALUE = 101;
	private static final int VAL_NAME = 102;
	private static final int VAL_POS_X = 103;
	private static final int VAL_POS_Y = 104;
	private static final int VAL_ROT = 105;
	private static final int VAL_SIDE = 106;
	
	public CsvPCBimport(File f){
		pcb = new PCB();
		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String s;
			do {
				s = br.readLine();
				if(s != null)
					processString(s);
			} while (s != null);

			br.close();
		} catch (FileNotFoundException e) {
			debug.Debug.println(e.getMessage(), debug.Debug.ERROR);
			return;
		} catch (IOException e) {
			debug.Debug.println(e.getMessage(), debug.Debug.ERROR);
		}
	}
	
	private void processString(String s){
		if(s.startsWith("##")){
			//TODO special stuff
			return;
		}
		String[] st = advSplit(s);
		if(s.startsWith("#")){
			//Information order
			int start = 0;
			if(st[0].compareTo("#")==0){
				start = 1;
			}
			vals = new int[st.length-start];
			for (int i = start; i < st.length; i++) {
				vals[i-start] = setInfoOrder(st[i]);
			}
			advancedArrPrinter(vals);
		}else{
			try {
				Footprint fp = new Footprint();
				for (int i = 0; i < st.length; i++) {
					setFP(st[i], vals[i], fp);
				}
				fp.yPos = -fp.yPos; //The y-Position is inverted in this format TODO verify this claim for other Programs
				fp.yPosInInt = -fp.yPosInInt;
				System.out.println("reference: "+fp.reference);
				System.out.println("->at "+fp.xPos+" "+fp.yPos+" "+fp.rotation+"("+fp.xPosInInt+" "+fp.yPosInInt+")");
				pcb.addFootPrint(fp);
			} catch (Exception e) {
				debug.Debug.println(" Problem loading Footprint: "+e.toString(), debug.Debug.ERROR);
			}
		}
	}
	
	private void setFP(String s, int id, Footprint fp){
		switch (id) {
		case VAL_REFF:
			fp.reference = new String(s);
			break;
		case VAL_NAME:
			fp.name = new String(s);
			break;
		case VAL_VALUE:
			fp.value = new String(s);
			break;
		case VAL_ROT:
			fp.rotation = Double.parseDouble(s);
			break;
		case VAL_POS_X:
			fp.xPos = Double.parseDouble(s);
			fp.xPosInInt = utility.DoubleReader.parseDouble(s);
			break;
		case VAL_POS_Y:
			fp.yPos = Double.parseDouble(s);
			fp.yPosInInt = utility.DoubleReader.parseDouble(s);
			break;
		case VAL_SIDE:
			//Use if needed
			break;

		default:
			break;
		}
	}
	
	private int setInfoOrder(String s){
		if(s.contains("Ref"))
			return VAL_REFF;
		if(s.contains("Val"))
			return VAL_VALUE;
		if(s.contains("Pack"))
			return VAL_NAME;
		if(s.contains("PosX"))
			return VAL_POS_X;
		if(s.contains("PosY"))
			return VAL_POS_Y;
		if(s.contains("Rot"))
			return VAL_ROT;
		if(s.contains("Side"))
			return VAL_SIDE;
		
		return 0;
	}
	
	private String[] advSplit(String s){
		String[] st = s.split(" ");
		int l = 0;
		for (int i = 0; i < st.length; i++) {
			if(st[i].length() > 0){
				l++;
			}
		}
		String[] slt = new String[l];
		l = 0;
		for (int i = 0; i < st.length; i++) {
			if(st[i].length() > 0){
				slt[l] = st[i];
				l++;
			}
		}
		advancedArrPrinter(slt);
		
		return slt;
	}
	
	private void advancedArrPrinter(String[] s){
		String tp = "";
		for (int i = 0; i < s.length; i++) {
			tp+=s[i]+" - ";
		}
		System.out.println(tp);
	}
	
	private void advancedArrPrinter(int[] s){
		String tp = "";
		for (int i = 0; i < s.length; i++) {
			tp+=s[i]+" - ";
		}
		System.out.println(tp);
	}
}

/*class StringList{
	public StringList n;
	public String s;
	
	public StringList(String s){
		this.s = s;
	}
	
	public void add(String s){
		if(this.s == null){
			this.s = s;
		}else if(n == null){
			n = new StringList(s);
		}else{
			n.add(s);
		}
	}
	
	public int count(){
		int u = 0;
		if(s != null){
			u = 1;
		}
		if(n == null){
			return u;
		}else{
			return u+n.count();
		}
	}
	
	public void toArr(String[] s, int u){
		if(this.s != null){
			s[u] = this.s;
			u++;
		}
		if(n != null){
			n.toArr(s, u);
		}
	}
}*/
