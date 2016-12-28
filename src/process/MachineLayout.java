package process;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class MachineLayout {

	/**
	 * This class holds Positioning informations for Tools, Components, Parking-Positions ect.
	 */
	
	//reels
	public int[] reelPosX;
	public int[] reelPosY;
	public int[] reelSize;
	
	public int reelStepSize;
	
	//Trays
	public int[] trayPosX;
	public int[] trayPosY;
	public int[] traySize;
	
	public int trayStepSize;
	
	//HeadInformation[x][y][xPos,yPos,Axis]
	public int[][][] headInformation;
	
	public MachineLayout(){
		
	}
	
	public byte load(){
		try {
			FileReader fr = new FileReader("res/machineLayout.set");
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
			return 1;
		} catch (Exception e) {
			debug.Debug.println(e.getMessage(), debug.Debug.ERROR);
			return 1;
		}
		
		return 0;
	}
	
	private void processString(String s){
		if(s.startsWith("//"))return;
		
		String[] st = s.split(" ");
		if(st[0].compareTo("REEL")==0){
			if(st[1].charAt(0)==':'){
				int size = Integer.parseInt(st[2]);
				reelPosX = new int[size];
				reelPosY = new int[size];
				reelSize = new int[size];
			}else if(st[1].compareTo("STEPSIZE")==0){
				reelStepSize = Integer.parseInt(st[3]);
			}else{
				int u = Integer.parseInt(st[1])-1;
				for (int i = 3; i < st.length; i++) {
					if(st[i].startsWith("x"))
						reelPosX[u] = Integer.parseInt(st[i].substring(1));
					if(st[i].startsWith("y"))
						reelPosY[u] = Integer.parseInt(st[i].substring(1));
					if(st[i].startsWith("s"))
						reelSize[u] = Integer.parseInt(st[i].substring(1));
				}
			}
		}else if(st[0].compareTo("TRAY")==0){
			if(st[1].charAt(0)==':'){
				int size = Integer.parseInt(st[2]);
				trayPosX = new int[size];
				trayPosY = new int[size];
				traySize = new int[size];
			}else if(st[1].compareTo("STEPSIZE")==0){
				trayStepSize = Integer.parseInt(st[3]);
			}else{
				int u = Integer.parseInt(st[1])-1;
				for (int i = 3; i < st.length; i++) {
					if(st[i].startsWith("x"))
						trayPosX[u] = Integer.parseInt(st[i].substring(1));
					if(st[i].startsWith("y"))
						trayPosY[u] = Integer.parseInt(st[i].substring(1));
					if(st[i].startsWith("s"))
						traySize[u] = Integer.parseInt(st[i].substring(1));
				}
			}
		}else if(st[0].compareTo("HEADS")==0){
			headInformation = new int[Integer.parseInt(st[2])][Integer.parseInt(st[3])][3];
		}else if(st[0].compareTo("HEAD")==0){
			int u = Integer.parseInt(st[1])-1;
			int v = Integer.parseInt(st[2])-1;
			for (int i = 4; i < st.length; i++) {
				if(st[i].startsWith("CoR_X"))
					headInformation[u][v][0] = Integer.parseInt(st[i].substring(5));
				if(st[i].startsWith("CoR_Y"))
					headInformation[u][v][1]  = Integer.parseInt(st[i].substring(5));
				if(st[i].startsWith("Axis"))
					headInformation[u][v][2]  = Integer.parseInt(st[i].substring(4));
			}
		}
	}
}
