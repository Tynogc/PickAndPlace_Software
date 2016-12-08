package process;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.Semaphore;

import javax.imageio.ImageIO;

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
	
	public PartPlacement(){
		sema = new Semaphore(1);
		
		pictureClient = new PicturClient(this);
	}
	
	public void paintUnProcessed(Graphics g, int x, int y){
		try {
			sema.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(lastImageUnProcessed != null)
			g.drawImage(lastImageUnProcessed, x, y, null);
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
	
	public void processImage(){
		//TODO!!!!
		try {
			File outputfile = new File("test.png");
			ImageIO.write(imageToProcess, "png", outputfile);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		debug.Debug.println("Starting Process");
		imageToProcess = Filter.colorFilter(imageToProcess, new Color(80,255,255), 100);
		lastImageProcessed = imageToProcess;
		imageToProcess = Filter.amplifyContrast(imageToProcess, 50, 3);
		EdgeDetector ed = new EdgeDetector(EdgeDetector.UP, 50);
		Point[][] p;
		Point[][] p1;
		Point[][] p2;
		Point[][] p3;
		try {
			p = ed.getEdgeFrome(imageToProcess);
			ed = new EdgeDetector(EdgeDetector.LEFT, 50);
			p1 = ed.getEdgeFrome(imageToProcess);
			ed = new EdgeDetector(EdgeDetector.DOWN, 50);
			p2 = ed.getEdgeFrome(imageToProcess);
			ed = new EdgeDetector(EdgeDetector.RIGHT, 50);
			p3 = ed.getEdgeFrome(imageToProcess);
			
		} catch (Exception e) {
			debug.Debug.printExeption(e);
			return;
		}
		debug.Debug.println("Done!");
		try {
			sema.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			ed.drawEdges(lastImageProcessed, p);
			ed.drawEdges(lastImageProcessed, p1);
			ed.drawEdges(lastImageProcessed, p2);
			ed.drawEdges(lastImageProcessed, p3);
		} catch (Exception e) {
			sema.release();
			debug.Debug.printExeption(e);
			return;
		}
		sema.release();
	}
	
}
