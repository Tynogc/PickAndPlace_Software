package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import main.PicLoader;
import main.SeyprisMain;
import menu.AbstractMenu;

public class SideMenu extends AbstractMenu{

	private BufferedImage[][] bigNums;
	private BufferedImage[][] smalNums;
	private BufferedImage[] ico;
	
	public SideMenu() {
		super(SeyprisMain.sizeX()-200,70,200,500);
		bigNums = new BufferedImage[11][3];
		smalNums = new BufferedImage[11][3];
		for (int i = 0; i < bigNums.length; i++) {
			for (int k = 0; k < 3; k++) {
				bigNums[i][k] = generatColoredBuffer(new ImageIcon("res/ima/gui/side/gs"+i+".png"), k);
				smalNums[i][k] = generatColoredBuffer(new ImageIcon("res/ima/gui/side/gc"+i+".png"), k);
			}
		}
		
		ico = new BufferedImage[]{
			PicLoader.pic.getImage("res/ima/gui/side/aw1.png"),
			PicLoader.pic.getImage("res/ima/gui/side/aw2.png")
		};
	}
	
	private BufferedImage generatColoredBuffer(ImageIcon ima, int i){
		BufferedImage ret = new BufferedImage(ima.getIconWidth(), ima.getIconHeight()
				, BufferedImage.TYPE_INT_ARGB);
		Graphics g = ret.getGraphics();
		g.drawImage(ima.getImage(),0 ,0,null);
		
		Color c0  = null;
		Color c1  = null;
		Color c2  = null;
		
		if(i == 2){
			c0 = new Color(205,10,10);
			c1 = new Color(230,15,15);
			c2 = new Color(255,20,20);
		}else if(i == 1){
			c0 = new Color(80,80,80);
			c1 = new Color(100,100,100);
			c2 = new Color(110,110,110);
		}else{
			c0 = new Color(0xffcdcd99);
			c1 = new Color(0xffe8e8cc);
			c2 = new Color(0xffffffe0);
		}
		
		for (int j = 0; j < ima.getIconWidth(); j++) {
			for (int k = 0; k < ima.getIconHeight(); k++) {
				int o = ret.getRGB(j, k);
				if(o == 0xffcdcdcd){
					g.setColor(c0);
					g.drawRect(j, k, 0, 0);
				}
				if(o == 0xffe8e8e8){
					g.setColor(c1);
					g.drawRect(j, k, 0, 0);
				}
				if(o == 0xffffffff){
					g.setColor(c2);
					g.drawRect(j, k, 0, 0);
				}
				if(o == 0xff7f7f7f){
					g.setColor(new Color(80,80,80));
					g.drawRect(j, k, 0, 0);
				}
			}
		}
		
		return ret;
	}
	
	private void drawEmc(Graphics g, int x, int y, int data, boolean absolut, int status){
		if(absolut)g.drawImage(ico[1], x, y, null);
		else g.drawImage(ico[0], x, y, null);
		if(status<0||status>=bigNums[0].length){
			status = 0;
			g.drawImage(bigNums[10][status], x+10, y+4, null);
			g.drawImage(bigNums[10][status], x+42, y+4, null);
			g.drawImage(bigNums[10][status], x+74, y+4, null);
			g.drawImage(bigNums[10][status], x+106, y+4, null);
			g.drawImage(smalNums[10][status], x+148, y+15, null);
			g.drawImage(smalNums[10][status], x+171, y+15, null);
			g.drawImage(smalNums[10][status], x+194, y+15, null);
		}
	}

	@Override
	protected void uppdateIntern() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void paintIntern(Graphics g) {
		drawEmc(g, 60, 20, 0, false, 10);
		
	}
}
