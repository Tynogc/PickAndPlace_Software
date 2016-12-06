package debug;

public class DebugComands_Old {
	
	public static void comandTyped(String comand){
		if(comand.startsWith("/")){
			//Comand
			if(comand.startsWith("/system.")){
				rutineSystem(comand.substring(8));
			}
		}else if(comand.startsWith("%")){
			//Debug einstellung
		}else if(comand.startsWith("#")){
			//Markierung
		}else{
			debug.Debug.print("This Text can not be processed... ", 10);
			DebugFrame_Old.addString("Commands start with /", 20);
			DebugFrame_Old.addString("Settings start with %", 20);
			DebugFrame_Old.addString("Marks are #", 20);
		}
	}
	
	private static void rutineSystem(String s){
		if(s.compareTo("ram") == 0){
			Runtime r = Runtime.getRuntime();
			long max = r.maxMemory();
			long total = r.totalMemory();
			long used = r.freeMemory();
			DebugFrame_Old.addString("* Memory: "+((total-used)/1000000)+"Mb used of total "+(total/1000000)+"Mb", 11);
			DebugFrame_Old.addString("  -Maximum Memory: "+(max/1000000)+"Mb", 12);
			DebugFrame_Old.addString("  -Free direct Memory: "+(used/1000000)+"Mb", 12);
			DebugFrame_Old.addString("  -Current in use: "+(total-used)+"byte", 12);
			return;
		}
		if(s.compareTo("info") == 0){
			Runtime r = Runtime.getRuntime();
			int i1 = r.availableProcessors();
			DebugFrame_Old.addString("* Processors available "+i1, 11);//TODO mehr
			return;
		}
		if(s.compareTo("gc") == 0||s.compareTo("garbage") == 0){
			DebugFrame_Old.addString("* Starting GarbageColector:", 11);
			long t = System.currentTimeMillis();
			Runtime r = Runtime.getRuntime();
			long mem = r.totalMemory()-r.freeMemory();
			DebugFrame_Old.addString("  -Running gc...", 12);
			r.gc();
			long newMem = r.totalMemory()-r.freeMemory();
			DebugFrame_Old.addString("  -Done! Time: "+(System.currentTimeMillis()-t)+"ms", 12);
			DebugFrame_Old.addString("  -Momory befor: "+mem+"byte", 12);
			DebugFrame_Old.addString("  -Momory after: "+newMem+"byte", 12);
			return;
		}
		DebugFrame_Old.addString("Command \""+s+"\" not found in System", 10);
	}

}
