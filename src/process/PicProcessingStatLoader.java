package process;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class PicProcessingStatLoader {

	public final Color filterColor;
	public final int cornerNumber;
	public final int contastValue;
	
	public final int[][] offsetValues;
	
	public final double scaling;
	
	private static final String filepath = "res/camera_setup.set";
	
	private boolean err;
	private boolean war;
	
	private String[] str;
	
	public PicProcessingStatLoader(){
		err = false;
		war = false;
		str = new String[30];
		try {
			FileReader fr = new FileReader(filepath);
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
			debug.Debug.println(" Picture settings: " + i + " Lines loaded!");

			br.close();
		} catch (FileNotFoundException e) {
			debug.Debug.println(e.getMessage(), debug.Debug.ERROR);
			err = true;
		} catch (IOException e) {
			debug.Debug.println(e.getMessage(), debug.Debug.ERROR);
			err = true;
		}
		String[] s; int r=0; int g=0; int b=0;
		try {
			s = str[1].split(" ");
			r = Integer.parseInt(s[1]);
			g = Integer.parseInt(s[2]);
			b = Integer.parseInt(s[3]);
		} catch (Exception e) {
			debug.Debug.println("Color Format wrong!", debug.Debug.ERROR);
			err = true;
		}
		filterColor = new Color(r,g,b);
		r = 50;
		g = 1;
		try {
			s = str[2].split(" ");
			r = Integer.parseInt(s[1]);
			g = Integer.parseInt(s[3]);
		} catch (Exception e) {
			debug.Debug.println("Value Format wrong!", debug.Debug.ERROR);
			err = true;
		}
		contastValue = r;
		cornerNumber = g;
		offsetValues = new int[4][2];
		try {
			offsetValues[0] = uli(str[5]);
			offsetValues[1] = uli(str[6]);
			offsetValues[2] = uli(str[7]);
			offsetValues[3] = uli(str[8]);
		} catch (Exception e) {
			debug.Debug.println("Positioning Format wrong!", debug.Debug.WARN);
			war = true;
		}
		
		double d = 1.0;
		try {
			s = str[3].split(": ");
			d = Double.parseDouble(s[1]);
		} catch (Exception e) {
			debug.Debug.println("Scaling format wronng!", debug.Debug.WARN);
			war = true;
		}
		scaling = d;
	}
	
	private int[] uli(String st){
		String[] s = st.split(" ");
		return new int[]{
			Integer.parseInt(s[2]),
			Integer.parseInt(s[4])
		};
	}
	
	public void saveFilterColor(Color c){
		str[1] = "Null-Color: "+c.getRed()+" "+c.getGreen()+" "+c.getBlue();
		save();
	}
	
	public void saveScaling(double s){
		str[3] = "Scaling mm to pixel: "+s;
		save();
	}
	
	public void saveCornerNumber(int n){
		str[2] = "Contrast: "+contastValue+" CornerNumber: "+n;
		save();
	}
	public void saveContrastValue(int v){
		str[2] = "Contrast: "+v+" CornerNumber: "+cornerNumber;
		save();
	}
	public void saveOffset(int[][] t){
		try {
			str[4] = "1> x: "+t[0][0]+" y: "+t[0][1];
			str[5] = "2> x: "+t[1][0]+" y: "+t[1][1];
			str[6] = "3> x: "+t[2][0]+" y: "+t[2][1];
			str[7] = "4> x: "+t[3][0]+" y: "+t[3][1];
		} catch (Exception e) {
			debug.Debug.printExeption(e);
			return;
		}
		save();
	}
	
	private void save(){
		PrintWriter writer = null; 
		try { 
			writer = new PrintWriter(new FileWriter(filepath)); 
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
	
	public byte getState(){
		if(err)return 1;
		if(war)return 2;
		return 0;
	}
}
