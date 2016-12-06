package process;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.concurrent.Semaphore;

public class PartPlacement {

	private BufferedImage lastImageUnProcessed;
	private BufferedImage lastImageProcessed;
	private BufferedImage imageToProcess;
	private BufferedImage lastImageTDCamera;
	
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
	
	public void request(){
		pictureClient.requestPic(0);//TODO
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
		//TODO process
		lastImageProcessed = b;
		sema.release();
	}
	
	public void processImage(){
		
	}
	
}
