package comunication;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import utility.FiFo;

public class Linker extends Thread{

	private Socket sck;
	private Scanner sc;
	
	public FiFo fifo;
	
	public boolean threadIsRunning;
	
	public Linker(String address, int port)throws Exception{
		sck = new Socket(address, port);
		sc = new Scanner(sck.getInputStream());
		fifo = new FiFo();
		fifo.setEnable(true);
		threadIsRunning = true;
		debug.Debug.println("*Connected!", debug.Debug.MASSAGE);
		start();
	}
	
	public void run(){
		while(threadIsRunning){
			if(sc.hasNextLine()){
				String s = sc.nextLine();
				fifo.in(s);
				System.out.println(s);
			}else{
				try {
					sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
