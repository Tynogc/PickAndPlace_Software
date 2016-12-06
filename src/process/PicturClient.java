package process;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.util.concurrent.Semaphore;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;


public class PicturClient extends Thread{

	private Semaphore sema;
	private boolean threadIsRunning = true;
	private PartPlacement pp;
	
	private int requested;
	private VideoCapture[] vc;
	private Mat matrix[];
	private BufferedImage[] oldImages;
	
	private int topDownCamera = 1;
	private int partPlacementCamera = 2;
	
	public int refreschRate = 60;
	
	static{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME); 
	}
	
	public PicturClient(PartPlacement p){
		sema = new Semaphore(1);
		pp = p;
		vc = new VideoCapture[3];
		matrix = new Mat[3];
		oldImages = new BufferedImage[3];
		for (int i = 0; i < matrix.length; i++) {
			openVc(i);
		}
		main.ExitThread.vcs = vc;
		requested = -1;
		setName("Camera Thread");
		start();
	}
	
	private void openVc(int i){
		debug.Debug.println("* Trying to Acsess Camera with ID:"+i);
		vc[i] = new VideoCapture(i);
		matrix[i] = new Mat();
		vc[i].read(matrix[i]);
		vc[i].set(Highgui.CV_CAP_PROP_BUFFERSIZE, 1);
		vc[i].set(Highgui.CV_CAP_PROP_IOS_DEVICE_EXPOSURE, 0);
		vc[i].set(Highgui.CV_CAP_PROP_ISO_SPEED, 0);
		
		try {
			sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(vc[i].isOpened()){
			debug.Debug.print(" DONE", debug.Debug.MASSAGE);
		} else{
			debug.Debug.print(" ERROR", debug.Debug.ERROR);
			vc[i].release();
			vc[i] = null;
		}
	}
	
	public void requestPic(int i){
		try {
			sema.acquire();
		} catch (InterruptedException e) {
			debug.Debug.printExeption(e);
		}
		requested = i;
		sema.release();
	}
	
	public void run(){
		try {
			sema.acquire();
		} catch (InterruptedException e) {
			debug.Debug.printExeption(e);
		}
		int i = 0;
		while (threadIsRunning) {
			if(requested>=0){
				sema.release();
				try {
					loadImage(0, true);
				} catch (Exception e) {
					debug.Debug.printExeption(e);
				}
			}else{
				sema.release();
				i+=10;
				if(i>=refreschRate){
					i = 0;
					gui.PerformanceMenu.ThreadCheck(this);
					try {
						loadImage(topDownCamera, false);
						loadImage(partPlacementCamera, false);
					} catch (InterruptedException e) {
						debug.Debug.printExeption(e);
					}
				}
			}
			try {
				sleep(10);
			} catch (InterruptedException e) {
				debug.Debug.printExeption(e);
			}
			
			try {
				sema.acquire();
			} catch (InterruptedException e) {
				debug.Debug.printExeption(e);
			}
		}
	}
	
	private void loadImage(int i, boolean request) throws InterruptedException{
		sema.acquire();
		if(request)
			i = requested;
		sema.release();
		
		if(vc[i] == null){
			return;
		}
		
		if(request){
			vc[i].read(matrix[i]);
		}
		
		try{
			if(!vc[i].read(matrix[i])){
				debug.Debug.println("* ERROR reading Camera "+i, debug.Debug.ERROR);
				//TODO
				return;
			}
		}catch (Exception e) {
			debug.Debug.println("* ERROR reading Camera "+i, debug.Debug.ERROR);
			debug.Debug.println(" Exeption "+e.toString(), debug.Debug.SUBERR);
			//TODO
			return;
		}
		
		BufferedImage im = matToBufferedImage(matrix[i], i, request);
		
		if(request){
			debug.Debug.println("Aquiered Pictur!");
			pp.recivedPicToProcess(im);
			sema.acquire();
			requested = -1;
			sema.release();
			pp.processImage();
		}else if(i == topDownCamera){
			oldImages[i] = pp.recivedPicTopDown(im);
		}else if(i == partPlacementCamera){
			oldImages[i] = pp.recivedPicPlacement(im);
		}
	}
	
	private BufferedImage matToBufferedImage(Mat frame, int i, boolean newBuffer) {
        //Mat() to BufferedImage
        int type = 0;
        if (frame.channels() == 1) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        } else if (frame.channels() == 3) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        BufferedImage tr = oldImages[i];
        if(tr == null || newBuffer)
        	tr = new BufferedImage(frame.width(), frame.height(), type);
        	
        WritableRaster raster = tr.getRaster();
        DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
        byte[] data = dataBuffer.getData();
        frame.get(0, 0, data);
        
        return tr;
    }
}
