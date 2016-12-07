package comunication;

import java.awt.Color;
import java.awt.Graphics;

import main.KeyListener;
import menu.AbstractMenu;
import menu.DataFiled;
import menu.TextEnterButton;

public class LinkedInMenu extends AbstractMenu{

	private Linker link;
	
	private menu.TextEnterButton adress;
	private menu.TextEnterButton port;
	
	public LinkedInMenu(KeyListener k){
		super(0,0,500,500);
		
		adress = new TextEnterButton(20,70,100,20,Color.white,k) {
			@Override
			protected void textEntered(String text) {}
		};
		add(adress);
		port = new TextEnterButton(20,100,100,20,Color.white,k) {
			@Override
			protected void textEntered(String text) {
			}
		};
		add(port);
		final DataFiled connect = new DataFiled(20,130,100,20,Color.GREEN) {
			@Override
			protected void uppdate() {}
			@Override
			protected void isClicked() {
				try {
					connect(adress.getText(), Integer.parseInt(port.getText()));
				} catch (Exception e) {
					debug.Debug.println("Can't connect!", debug.Debug.ERROR);
					debug.Debug.println(e.toString(), debug.Debug.SUBERR);
				}
			}
		};
		add(connect);
	}

	@Override
	protected void uppdateIntern() {
		if(link != null){
			while(link.fifo.contains()){
				try {
					processString(link.fifo.out());
				} catch (Exception e) {
					debug.Debug.printExeption(e);
				}
			}
		}
	}

	@Override
	protected void paintIntern(Graphics g) {
		
	}
	
	public void connect(String s, int port)throws Exception{
		link = new Linker(s, port);
	}
	
	private void processString(String s){
		if(s.startsWith("/")){
			s = s.substring(1);
			int u = s.lastIndexOf('%');
			int zu = Integer.parseInt(s.substring(u+1));
			if(s.startsWith("C")){
				debug.Debug.print(s.substring(1, u), zu);
			}else{
				debug.Debug.println(s.substring(1, u), zu);
			}
		}
	}
}
