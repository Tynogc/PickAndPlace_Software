package components.kiCad;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import components.Footprint;
import components.Pad;

public class KiCadFileImport {
	
	private String layer;
	private Pad pad;
	public Footprint footprint;

	public KiCadFileImport(File f){
		Runtime.getRuntime().gc();
		String operate = "";
		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);

			String s = null;
			do {
				s = br.readLine();
				if(s != null)
					operate += s;
			} while (s != null);

			br.close();
		} catch (FileNotFoundException e) {
			debug.Debug.println(e.getMessage(), debug.Debug.ERROR);
			return;
		} catch (IOException e) {
			debug.Debug.println(e.getMessage(), debug.Debug.ERROR);
		}
		footprint = new Footprint();
		try {
			textTree(operate);
		} catch (Exception e) {
			debug.Debug.println("* ERROR converting Footprint"+e.getMessage(), debug.Debug.ERROR);
		}
		Runtime.getRuntime().gc();
	}
	
	public KiCadFileImport(String s){
		footprint = new Footprint();
		try {
			textTree(s);
		} catch (Exception e) {
			debug.Debug.println("* ERROR converting Footprint"+e.getMessage(), debug.Debug.ERROR);
		}
	}
	
	private void textTree(String s){
		int subSequence = 0;
		while (s.length()>0) {
			char c = s.charAt(0);
			if(c == ' '||c == '\n' ||c == '\r'){
				//Leere Zeichen
				s = s.substring(1);
				continue;
			}
			if(c == '('){//one Layer deeper
				subSequence++;
				s = s.substring(1);
				continue;
			}
			if(c == ')'){//one Layer higher
				subSequence--;
				s = s.substring(1);
				continue;
			}
			if(subSequence == 1){
				int t = subseq1(s);
				s = s.substring(t);
				continue;
			}
			if(subSequence == 2){
				int t = processHight(s);
				subseq2(new String(s.substring(0, t)));
				s = s.substring(t);
				continue;
			}
		}
	}
	
	private int subseq1(String s){
		if(s.startsWith("module ")){
			s = s.substring(7);
			int t = s.indexOf(' ');
			footprint.name = new String(s.substring(0, t));
			System.out.println(footprint.name);
			return t;
		}
		
		return 1;
	}
	
	private void subseq2(String s){
		//System.out.println(s);
		if(s.startsWith("layer ")){
			s = s.substring(6);
			layer = new String(s);
			System.out.println("->"+layer);
			return;
		}
		if(s.startsWith("pad")){
			processPad(s);
			pad = null;
			return;
		}
		if(s.startsWith("at ")){
			String[] st = s.split(" ");
			try {
				double x = Double.parseDouble(st[1]);
				double y = Double.parseDouble(st[2]);
				double r = 0;
				if(st.length>=4)
					r = Double.parseDouble(st[3]);
				footprint.xPos = x;
				footprint.yPos = y;
				footprint.rotation = r;
			} catch (Exception e) {
				debug.Debug.println("Problem loading Footprint (at)"+e.toString());
			}
			
			return;
		}
		if(s.startsWith("fp_text reference ")){
			String[] st = s.split(" ");
			if(st.length>=3)
				footprint.reference = st[2];
			System.out.println("reference: "+footprint.reference);
		}
	}
	
	private void processPad(String s){
		pad = new Pad();
		
		s = s.substring(4);
		int t = s.indexOf(' ');
		pad.name = new String(s.substring(0, t));
		
		while(s.length()>0){
			if(s.startsWith("smd ")){
				//TODO rect, oval, ect
				s = s.substring(3);
				continue;
			}
			if(s.startsWith("rect ")){
				pad.type = Pad.TYPE_RECT;
				s = s.substring(4);
				continue;
			}
			if(s.startsWith("(")){
				t = processHight(s.substring(1));
				subPad(new String(s.substring(1,t+1)));
				
				s = s.substring(t+1);
				continue;
			}
			
			s = s.substring(1);
		}
	}
	
	private void subPad(String s){
		String[] st = s.split(" ");
		if(st[0].compareTo("at")==0){
			try {
				double d1 = Double.parseDouble(st[1]);
				double d2 = Double.parseDouble(st[2]);
				pad.xPos = d1;
				pad.yPos = d2;
				if(st.length>=4){
					double d3 = Double.parseDouble(st[3]);
					pad.rotation = d3;
				}
			} catch (Exception e) {
				debug.Debug.println("* Error converting Pad: "+s, debug.Debug.ERROR);
			}
		}
		if(st[0].compareTo("size")==0){
			try {
				double d1 = Double.parseDouble(st[1]);
				double d2 = Double.parseDouble(st[2]);
				pad.xSize = d1;
				pad.ySize = d2;
			} catch (Exception e) {
				debug.Debug.println("* Error converting Pad: "+s, debug.Debug.ERROR);
			}
		}
		if(st[0].compareTo("layer")==0||st[0].compareTo("layers")==0){
			for (int i = 1; i < st.length; i++) {
				if(st[i].contains(layer) ||
						st[i].contains("*.Cu")){
					footprint.addPad(pad);
					System.out.println("->"+pad.name+" x"+pad.xPos+" y"+pad.yPos+" Size x"+pad.xSize+" y"+pad.ySize);
				}
			}
		}
	}
	
	private int processHight(String s){
		int q = 0;
		int i = 0;
		while(q>=0){
			char c = s.charAt(i);
			if(c =='(')q++;
			if(c ==')')q--;
			
			i++;
		}
		return i-1;
	}
}
