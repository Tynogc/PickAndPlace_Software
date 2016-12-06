package comunication;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{

	public ServerSocket server;
	
	public static FiFo send;
	
	public Server(){
		send = new FiFo();
		try {
			server = new ServerSocket(3637);//TODO load Port
		} catch (IOException e) {
			debug.Debug.printExeption(e);
		}
		start();
	}
	
	public void run(){
		while(true){
			try {
				runServer();
			} catch (Exception e) {
				debug.Debug.printExeption(e);
			}
			send.setEnable(false);
		}
	}
	
	private void runServer() throws Exception{
		Socket socket = server.accept();
		
		PrintWriter p = new PrintWriter(socket.getOutputStream(), true);
		send.setEnable(true);
		debug.Debug.println("* Observer Connected: "+socket.getInetAddress(), debug.Debug.COM);
		while(true){
			if(send.contains()){
				p.println(send.out());
				if(p.checkError()){
					debug.Debug.println("* Observer Closed "+socket.getInetAddress(), debug.Debug.COMERR);
					break;
				}
			}else{
				sleep(30);
			}
		}
	}
	
}
