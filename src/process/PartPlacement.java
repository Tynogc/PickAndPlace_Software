package process;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.Semaphore;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import process.image.EdgeDetector;
import process.image.Filter;
import process.image.Point;

public class PartPlacement {

	private BufferedImage lastImageUnProcessed;
	private BufferedImage lastImageProcessed;
	private BufferedImage imageToProcess;
	private BufferedImage lastImageTDCamera;
	private BufferedImage lastImageViewerCamera;
	
	private Semaphore sema;
	
	private PicturClient pictureClient;
	
	private PicProcessingStatLoader values;
	private Spindel spindel;
	
	public PartPlacement(){
		debug.Debug.println(" Building Pic-Processing");
		sema = new Semaphore(1);
		
		pictureClient = new PicturClient(this);
		
		values = new PicProcessingStatLoader();
		debug.Debug.bootMsg(" Values", values.getState());
		
		spindel = new Spindel();
	}
	
	public void processImage(){
		debug.Debug.println("* Starting Process");
		spindel.processImage(imageToProcess, 0, 0, values);
		debug.Debug.println("DONE!");
		
		//TODO
		try {
			File outputfile = new File("test3.png");
			ImageIO.write(imageToProcess, "png", outputfile);
		} catch (Exception e) {
			// TODO: handle exception
		}
		lastImageProcessed = imageToProcess;
	}
	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void paintUnProcessed(Graphics g, int x, int y){
		try {
			sema.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(lastImageUnProcessed != null)
			g.drawImage(lastImageUnProcessed, x, y, null);
		g.setColor(Color.yellow);
		x+=spindel.centerOfRotationX;
		y+=spindel.centerOfRotationY;
		g.drawLine(x+10,y,x-10,y);
		g.drawLine(x,y-10,x,y+10);
		sema.release();
	}
	
	public void paintTDCamera(Graphics g, int x, int y){
		try {
			sema.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(lastImageTDCamera != null)
			g.drawImage(lastImageTDCamera, x, y, null);
		sema.release();
	}
	
	public void paintProcessed(Graphics g, int x, int y){
		try {
			sema.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(lastImageProcessed != null)
			g.drawImage(lastImageProcessed, x, y, null);
		sema.release();
	}
	
	public void paintViewer(Graphics g, int x, int y){
		try {
			sema.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(lastImageViewerCamera != null)
			g.drawImage(lastImageViewerCamera, x, y, null);
		sema.release();
	}
	
	public void paintSpindel(Graphics g, int x, int y){
		spindel.paintVisu(g, x, y);
	}
	
	public void request(){
		pictureClient.requestPic(main.Settings.CAMERA_PARTS);//TODO
		debug.Debug.println("*Requested Pic");
	}
	
	public BufferedImage recivedPicPlacement(BufferedImage b){
		try {
			sema.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		BufferedImage tr = lastImageUnProcessed;
		lastImageUnProcessed = b;
		sema.release();
		return tr;
	}
	
	public BufferedImage recivedViewer(BufferedImage b){
		try {
			sema.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		BufferedImage tr = lastImageViewerCamera;
		lastImageViewerCamera= b;
		sema.release();
		return tr;
	}
	
	public BufferedImage recivedPicTopDown(BufferedImage b){
		try {
			sema.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		BufferedImage tr = lastImageTDCamera;
		lastImageTDCamera = b;
		sema.release();
		return tr;
	}
	
	public void recivedPicToProcess(BufferedImage b){
		try {
			sema.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		imageToProcess = b;
		lastImageProcessed = b;
		sema.release();
	}
	
	public void runTest(String filepath){
		try {
			sema.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ImageIcon imo = new ImageIcon(filepath);
		imageToProcess = new BufferedImage(imo.getIconWidth(), imo.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		imageToProcess.getGraphics().drawImage(imo.getImage(), 0, 0, null);
		sema.release();
		debug.Debug.println("*Running Process-Test with "+filepath);
		if(imo.getIconHeight()>10)
			pictureClient.needProcess();
	}
	
}
