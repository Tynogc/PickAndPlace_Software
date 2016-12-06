package process.image;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class Filter {

	public static BufferedImage colorFilter(BufferedImage img, Color ct, int diff) {
		BufferedImage im = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j < img.getWidth(); j++) {
				Color c = new Color(img.getRGB(j, i));
				
				int u1 = Math.abs(ct.getRed()-c.getRed());
				int u2 = Math.abs(ct.getGreen()-c.getGreen());
				int u3 = Math.abs(ct.getBlue()-c.getBlue());
				u1 = (int)Math.sqrt(u1*u1+u2*u2+u3*u3);
				u1 = diff*3-u1*3;
				if(u1<0)u1=0;
				if(u1>255)u1 = 255;
				
				Color newColor = new Color(u1, u1, u1);

				im.setRGB(j, i, newColor.getRGB());
			}
		}

		return im;
	}
	
	public static BufferedImage sobelFilter(BufferedImage img){
		BufferedImage im = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for (int i = 1; i < img.getWidth()-1; i++) {
			for (int j = 1; j < img.getHeight()-1; j++) {

				int u1 = (img.getRGB(i-1, j-1)&0xff)+(img.getRGB(i-1, j)&0xff)*2+(img.getRGB(i-1, j+1)&0xff)-
						(img.getRGB(i+1, j-1)&0xff)-(img.getRGB(i+1, j)&0xff)*2-(img.getRGB(i+1, j+1)&0xff);
				
				int u2 = (img.getRGB(i-1, j-1)&0xff)+(img.getRGB(i, j-1)&0xff)*2+(img.getRGB(i+1, j-1)&0xff)-
						(img.getRGB(i+1, j+1)&0xff)-(img.getRGB(i, j+1)&0xff)*2-(img.getRGB(i-1, j+1)&0xff);
				u1 = Math.abs(u1);
				u2 = Math.abs(u2);
				if(u2>u1)
					u1 = u2;
				
				if(u1>255)u1 = 255;
				if(u1<0)u1=0;
				im.setRGB(i, j, new Color(u1,u1,u1).getRGB());
			}
		}

		return im;
	}
	
	public static BufferedImage amplifyContrast(BufferedImage img, int amr, int nsr){
		BufferedImage im = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		int w = Color.white.getRGB();
		int b = Color.black.getRGB();
		for (int i = 1; i < img.getHeight()-1; i++) {
			for (int j = 1; j < img.getWidth()-1; j++) {
				
				if((img.getRGB(j, i)&0xff)>amr){
					if(nsr==0){
						im.setRGB(j, i, w);
					}else{
						int u = 0;
						if((img.getRGB(j-1, i-1)&0xff)>amr)u++;
						if((img.getRGB(j-1, i)&0xff)>amr)u++;
						if((img.getRGB(j-1, i+1)&0xff)>amr)u++;
						if((img.getRGB(j, i-1)&0xff)>amr)u++;
						if((img.getRGB(j, i+1)&0xff)>amr)u++;
						if((img.getRGB(j+1, i-1)&0xff)>amr)u++;
						if((img.getRGB(j+1, i)&0xff)>amr)u++;
						if((img.getRGB(j+1, i+1)&0xff)>amr)u++;
						
						if(u>=nsr){
							im.setRGB(j, i, w);
						}else{
							im.setRGB(j, i, b);
						}
					}
				}else{
					im.setRGB(j, i, b);
				}
			}
		}
		return im;
	}
}
