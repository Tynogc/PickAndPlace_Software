package components;

public class FootprintSize {

	public static int[] getSize(Footprint f, double scale){
		int xSize = 0;
		int ySize = 0;
		
		PadList pad = f.pads;
		if(pad == null){
			return new int[]{0,0};
		}
		do {
			if(pad.pad != null){
				int[][] xi = getPadPosition(pad.pad, scale, f.rotation);
				
				for (int i = 0; i < xi[0].length; i++) {
					if(Math.abs(xi[0][i])>xSize)
						xSize = Math.abs(xi[0][i]);
					if(Math.abs(xi[1][i])>ySize)
						ySize = Math.abs(xi[1][i]);
				}
			}
			
			pad = pad.next;
		} while (pad!= null);
		
		return new int[]{xSize,ySize};
	}
	
	public static int[][] getPadPosition(Pad p, double s, double rotOfFp){
		double x = p.xPos*s;
		double y = p.yPos*s;
		double xs = p.xSize*s/2;
		double ys = p.ySize*s/2;
		double r = Math.toRadians(p.rotation-rotOfFp);
		
		int[] xi = new int[4];
		int[] yi = new int[4];
		xi[0] = (int)(x+Math.cos(r)*xs+Math.sin(r)*ys);
		yi[0] = (int)(y+Math.sin(r)*xs+Math.cos(r)*ys);
		xi[1] = (int)(x-Math.cos(r)*xs+Math.sin(r)*ys);
		yi[1] = (int)(y-Math.sin(r)*xs+Math.cos(r)*ys);
		xi[3] = (int)(x+Math.cos(r)*xs-Math.sin(r)*ys);
		yi[3] = (int)(y+Math.sin(r)*xs-Math.cos(r)*ys);
		xi[2] = (int)(x-Math.cos(r)*xs-Math.sin(r)*ys);
		yi[2] = (int)(y-Math.sin(r)*xs-Math.cos(r)*ys);
		
		return new int[][]{xi,yi};
	}
}
