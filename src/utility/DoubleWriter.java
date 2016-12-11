package utility;

public class DoubleWriter {

	public static String fixedKommata(double d, int k){
		int u = 1;
		for (int i = 0; i < k; i++) {
			u*=10;
		}
		u = (int)(d*u);
		
		String s = "";
		if(u<0)
			u*=-1;
		for (int i = 0; i < 20; i++) {
			if(i==k)
				s = "."+s;
			
			s = ""+(u%10)+s;
			u/=10;
			if(u==0)
				break;
		}
		if(d<1&&d>-1)
			s = "0."+s;
		if(d<0)
			s="-"+s;
		return s;
	}
}
