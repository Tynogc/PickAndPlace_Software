package menu;

import java.awt.Graphics;

public class Container implements ButtonInterface{

	private ButtonInterface next;
	private ButtonInterface content;
	
	private int xPos;
	private int yPos;
	
	public Container(int x, int y) {
		xPos = x;
		yPos = y;
		content = new EndButtonList();
	}
	
	@Override
	public ButtonInterface add(ButtonInterface b) {
		next = next.add(b);
		return this;
	}

	@Override
	public void leftClicked(int x, int y) {
		content.leftClicked(x-xPos, y-yPos);
		next.leftClicked(x, y);
	}

	@Override
	public void leftReleased(int x, int y) {
		content.leftReleased(x-xPos, y-yPos);
		next.leftReleased(x, y);	
	}

	@Override
	public void checkMouse(int x, int y) {
		content.checkMouse(x-xPos, y-yPos);
		next.checkMouse(x, y);
	}

	@Override
	public void paintYou(Graphics g) {
		g.translate(xPos, yPos);
		content.paintYou(g);
		g.translate(-xPos, -yPos);
		next.paintYou(g);
	}

	@Override
	public ButtonInterface remove(Button b) {
		next = next.remove(b);
		return this;
	}

	@Override
	public void longTermUpdate() {
	}

	@Override
	public void setNext(ButtonInterface b) {
		next = b;
	}
	

}
