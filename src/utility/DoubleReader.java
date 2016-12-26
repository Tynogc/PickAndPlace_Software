package utility;

public class DoubleReader {

	/**
	 * Returns an Integer equal to Double x1000 of the input-String
	 */
	public static int parseDouble(String s){
		String[] w = s.split("\\.");
		
		int i = Integer.parseInt(w[0]);
		int j = 0;
		if(w.length>=2){
			if(w[1].length()==0)
				w[1]+="000";
			if(w[1].length()==1)
				w[1]+="00";
			if(w[1].length()==2)
				w[1]+="0";
			j = Integer.parseInt(w[1].substring(0,3));
		}
			
		
		if(i>Integer.MAX_VALUE/1000 || i<Integer.MIN_VALUE/1000)
			debug.Debug.println(" Problem with values: Integer to big: "+i+"."+j, debug.Debug.WARN);
		
		if(i<0)j = -j;
		
		return i*1000+j;
	}
}
