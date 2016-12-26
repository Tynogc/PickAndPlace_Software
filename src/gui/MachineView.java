package gui;

import java.awt.Color;
import java.awt.Graphics;

import main.KeyListener;
import menu.AbstractMenu;
import menu.BigTextBox;
import menu.TextEnterButton;

public class MachineView extends AbstractMenu{

	private BigTextBox process;
	
	public MachineView(KeyListener k) {
		super(0,70,1400,900);
		
		process = new BigTextBox(10, 10, 400, 600);
		add(process);
		add(new TextEnterButton(310,610,100,20,Color.white,k) {
			@Override
			protected void textEntered(String text) {
				try {
					process.goTo(Integer.parseInt(text));
					setTextColor(Color.black);
				} catch (Exception e) {
					setTextColor(Color.red);
				}
			}
		});
		
		
		process.setText(getStr(45));
	}
	
	private String[] getStr(int size){
		String[] s = new String[size];
		
		for (int i = 0; i < s.length; i++) {
			s[i] = "A: "+i+" test "+Math.random();
		}
		return s;
	}

	@Override
	protected void uppdateIntern() {
		
	}

	@Override
	protected void paintIntern(Graphics g) {
		
	}

}
