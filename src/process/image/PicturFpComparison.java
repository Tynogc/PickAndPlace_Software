package process.image;

import java.awt.Color;
import java.awt.image.BufferedImage;

import components.Footprint;
import components.FootprintPainter;

public class PicturFpComparison {

	private FootprintPainter fp;
	
	public int xPos;
	public int yPos;
	
	public static final int LEFT_RIGHT = 22;
	public static final int UP_DOWN = 24;
	
	public PicturFpComparison(Footprint f, double scale, int x, int y){
		fp = new FootprintPainter(f, scale, true, Color.white);
		xPos = x;
		yPos = y;
	}
	
	public void search(BufferedImage im, int direction, int size){
		int xMax = -1;
		int yMax = -1;
		long max = 0;
		for (int i = -size; i < size; i++) {
			int x = xPos;
			int y = yPos;
			if(direction==LEFT_RIGHT)
				x+=i;
			else
				y+=i;
			
			//Checking oob;
			if(x-fp.middleX-1<0)continue;
			if(y-fp.middleY-1<0)continue;
			if(x+fp.middleX+1>=im.getWidth())continue;
			if(y+fp.middleY+1>=im.getHeight())continue;
			
			long sea = searchIntern(im, x, y);
			if(sea>max){
				max = sea;
				xMax = x;
				yMax = y;
			}
		}
		if(xMax<0||yMax<0){
			debug.Debug.println("* PROBLEM Checking footprint: no match -> PUE!", debug.Debug.ERROR);
			return;
		}
		xPos = xMax;
		yPos = yMax;
	}
	
	private long searchIntern(BufferedImage im, int x, int y){
		BufferedImage mask = fp.buffer;
		long c = 0;
		
		for (int i = 0; i < mask.getWidth(); i++) {
			for (int j = 0; j < mask.getHeight(); j++) {
				if((mask.getRGB(i, j)&0xff)<100)continue;
				
				int m = im.getRGB(x-fp.middleX+i, y-fp.middleY+j);
				c += (m&0xff);
			}
		}
		return c;
	}
}
