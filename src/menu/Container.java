package menu;

import java.awt.Graphics;

public class Container extends Button implements ButtonInterface{

	private ButtonInterface subButtons;
	
	public Container(int x, int y, int wi, int hi) {
		super(x, y, wi, hi);
		subButtons = new EndButtonList();
	}
	
	public void addToContainer(Button b){
		b.xPos = +xPos;
		b.yPos = +yPos;
		subButtons = subButtons.add(b);
	}
	public void removeFromContainer(Button b){
		subButtons = subButtons.remove(b);
	}

	@Override
	protected void isClicked() {
		
	}

	@Override
	protected void isFocused() {
		
	}

	@Override
	public void leftClicked(int x, int y) {
		next.leftClicked(x, y);
		subButtons.leftClicked(x, y);
	}

	@Override
	public void leftReleased(int x, int y) {
		next.leftReleased(x, y);
		subButtons.leftReleased(x, y);
	}

	@Override
	public void checkMouse(int x, int y) {
		next.checkMouse(x, y);
		subButtons.checkMouse(x, y);
	}

	@Override
	public void paintYou(Graphics g) {
		next.paintYou(g);
		//TODO selbst zeichnen
		subButtons.paintYou(g);
	}

	@Override
	public Button add(Button b){
		next = next.add(b);
		return this;
	}
	
	@Override
	public ButtonInterface remove(Button b){
		if(b == this){
			return next;
		}
		next = next.remove(b);
		return this;
	}

	@Override
	protected void uppdate() {
		
	}

}
