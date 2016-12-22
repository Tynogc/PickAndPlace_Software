package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;

import org.opencv.highgui.VideoCapture;

public class ExitThread extends Thread{
	
	private static String filePath;
	
	public static boolean restart = false;
	
	public static VideoCapture[] vcs;
	
	public static void setFilePath(String s){
		filePath = s;
	}
	
	private static String[] upLink;
	private static String[] downLink;
	
	static{
		upLink = new String[10];
		downLink = new String[10];
	}
	
	public static void setUpLink(String[] s){
		if(s == null)return;
		if(s.length == 0)return;
		for (int i = upLink.length; i >= 0; i--) {
			if(i+s.length>=upLink.length)continue;
			upLink[i+s.length] = upLink[i];
		}
		
		upLink[0] = "["+new java.text.SimpleDateFormat("mm:ss.").format(new java.util.Date (System.currentTimeMillis()))+
				(System.currentTimeMillis()%1000)/10;
		
		if(upLink[0].length()==8){
			upLink[0] =upLink[0].substring(0, 7)+"0"+upLink[0].charAt(7)+"]"+s[0];
		}else{
			upLink[0] +="]"+s[0];
		}
		
		for (int i = 1; i < s.length; i++) {
			if(i>=upLink.length)break;
			upLink[i] = "          "+s[i];
		}
	}
	
	public static void setDownLink(String[] s){
		if(s == null)return;
		if(s.length == 0)return;
		for (int i = downLink.length; i >= 0; i--) {
			if(i+s.length>=downLink.length)continue;
			downLink[i+s.length] = downLink[i];
		}
		
		downLink[0] = "["+new java.text.SimpleDateFormat("mm:ss.").format(new java.util.Date (System.currentTimeMillis()))+
				(System.currentTimeMillis()%1000)/10;
		
		if(downLink[0].length()==8){
			downLink[0] =downLink[0].substring(0, 7)+"0"+downLink[0].charAt(7)+"]"+s[0];
		}else{
			downLink[0] +="]"+s[0];
		}
		
		for (int i = 1; i < s.length; i++) {
			if(i>=downLink.length)break;
			downLink[i] = "          "+s[i];
		}
	}
	
	public ExitThread(){
		
	}
	
	public void run(){
		PrintWriter writer = null; 
		try { 
			writer = new PrintWriter(new FileWriter(filePath, true), true); 
			writer.println("------------------------------");
			writer.println("Last UpLink record:");
			for (int i = 0; i < upLink.length; i++) {
				if(upLink[i] != null){
					writer.println(upLink[i]);
				}
			}
			writer.println("------------------------------");
			writer.println("Last DownLink record:");
			for (int i = 0; i < downLink.length; i++) {
				if(downLink[i] != null){
					writer.println(downLink[i]);
				}
			}
			writer.println("------------------------------");
			writer.println("Closing Video Conections...:");
			if(vcs!=null)
			for (int i = 0; i < vcs.length; i++) {
				if(vcs[i]!=null){
					vcs[i].release();
					writer.println("Closed "+i);
				}
			}
		} catch (IOException ioe) { 
			ioe.printStackTrace(); 
		} finally { 
			if (writer != null){ 
				writer.flush(); 
				writer.close(); 
			} 
		}
		if(restart){
			StringBuilder cmd = new StringBuilder();
			cmd.append(System.getProperty("java.home")+File.separator+"bin"+File.separator+"java ");
			for(String jvmArg : ManagementFactory.getRuntimeMXBean().getInputArguments()){
				cmd.append(jvmArg+" ");
			}
			cmd.append("-cp ").append(ManagementFactory.getRuntimeMXBean().getClassPath()).append(" ");
			cmd.append(SeyprisMain.class.getName()).append(" ");
			try {
				Runtime.getRuntime().exec(cmd.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
