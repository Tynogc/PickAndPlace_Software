package components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class FootprintPainterToShow {

	public BufferedImage buffer;
	
	public final int middleX;
	public final int middleY;
	
	public FootprintPainter fpp;
	
	public FootprintPainterToShow(Footprint f, int size, boolean fill, Color c, boolean strings, int rot){
		int[] i = FootprintSize.getSize(f, 10.0);
		int u = i[0];
		if(i[1]>u)
			u = i[1];
		
		double s = (double)size/(double)(u/10);
		s/=2;
		if(s == Double.NaN || size == 0 || s>10.0)
			s = 10.0;
		
		debug.Debug.println("* "+s);
		
		fpp = new FootprintPainter(f, s, fill, c, strings, rot);
		middleX = fpp.middleX;
		middleY = fpp.middleY;
		buffer = fpp.buffer;
		
		Graphics g = buffer.getGraphics();
		g.setColor(Color.blue);
		g.drawLine(middleX+10, middleY, middleX-10, middleY);
		g.drawLine(middleX, middleY+10, middleX, middleY-10);
	}
}
