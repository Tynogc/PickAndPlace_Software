package link;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import utility.FiFo;

public class Linker{

	private Socket socket;
	
	private FiFo out;
	private FiFo in;
	private PrintWriter send;
	private Scanner recive;
	
	private Semaphore sema;
	private boolean isRunnung;
	
	public Linker(Socket s) throws IOException{
		socket = s;
		
		out = new FiFo();
		in = new FiFo();
		
		send = new PrintWriter(socket.getOutputStream(), true);
		recive = new Scanner(socket.getInputStream());
		
		sema = new Semaphore(1);
		isRunnung = true;
		
		new Thread(){
			@Override
			public void run() {
				try {
					while (listen()) {}
				} catch (Exception e) {
					debug.Debug.println("* Communication ERROR: Read: "+e, debug.Debug.ERROR);
				}
				debug.Debug.println(" Communication: Reader has run out!", debug.Debug.SUBERR);
			}
		}.start();
		
		new Thread(){
			@Override
			public void run() {
				try {
					while (write()) {}
				} catch (Exception e) {
					debug.Debug.println("* Communication ERROR: Write: "+e, debug.Debug.ERROR);
				}
				debug.Debug.println(" Communication: Writer has run out!", debug.Debug.SUBERR);
			}
		}.start();
	}
	
	private boolean listen() throws InterruptedException{
		if(sema.tryAcquire()){
			if(!isRunnung){
				sema.release();
				return false;
			}
			sema.release();
			if(recive.hasNext()){
				in.in(recive.nextLine(), System.currentTimeMillis());
				return true;
			}
		}
		Thread.sleep(5);
		return true;
	}
	
	private boolean write() throws InterruptedException{
		sema.acquire();
		if(!isRunnung){
			sema.release();
			return false;
		}
		sema.release();
		if(out.contains()){
			send.println(out.out());
			return true;
		}
		Thread.sleep(5);
		return true;
	}
	
	public void terminate(){
		
	}
}
