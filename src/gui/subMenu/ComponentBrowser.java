package gui.subMenu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import utility.FileLoader;
import main.PicLoader;
import main.SeyprisMain;
import menu.Button;
import menu.ScrollBar;
import menu.TextEnterButton;
import gui.GuiControle;
import gui.MoveMenu;

public abstract class ComponentBrowser extends MoveMenu implements OpenFile{

	protected DataPoint[] ddp;
	protected TextEnterButton teb;
	protected Button browse;
	protected ScrollBar scr;
	protected FileToSearch[] files;
	
	protected static final int numberOfScroll = 3;
	
	protected int scrolling;
	
	public ComponentBrowser(int x, int y) {
		super(x, y, PicLoader.pic.getImage("res/ima/mbe/m500x200.png"),"");
		ddp = new DataPoint[numberOfScroll];
		for (int i = 0; i < ddp.length; i++) {
			ddp[i] = new DataPoint(50, 100+i*30, this);
			add(ddp[i]);
		}
		scr = new ScrollBar(350,90,90,20,40);
		add(scr);
		scr.setDisabled(true);
		
		teb = new TextEnterButton(50,70,300,20,Color.white, SeyprisMain.getKL()) {
			@Override
			protected void textEntered(String text) {
				enteredSearch(text);
			}
		};
		add(teb);
		
		browse = new Button(370,70,"res/ima/cli/B") {
			@Override
			protected void uppdate() {}
			@Override
			protected void isFocused() {}
			@Override
			protected void isClicked() {
				ComponentLibrary cl = new ComponentLibrary();
				GuiControle.addMenu(cl);
				cl.setActionClicked(new FileLoader() {
					@Override
					public void fileLoaded(File f) {
						clickedOn(f);
					}
				});
			}
		};
		add(browse);
		
		scrolledTo(0);
	}

	@Override
	protected void paintSecond(Graphics g) {
		
	}

	@Override
	protected boolean close() {
		return true;
	}

	@Override
	protected void uppdateIntern() {
		
	}
	
	private void enteredSearch(String s){
		if(s.length()<3)return;
		
		File[] f = new utility.FileSearch(new File("user/components")).searchFor(s);
		files = new FileToSearch[f.length];
		debug.Debug.println(f.length+" Files found.", debug.Debug.SUBCOM);
		for (int i = 0; i < f.length; i++) {
			files[i] = new FileToSearch(f[i]);
		}
		if(scr != null)
			remove(scr);
		if(files.length>numberOfScroll){
			scr = new ScrollBar(350,90,90,numberOfScroll,files.length);
			add(scr);
		}else{
			scr = new ScrollBar(350,90,90,20,40);
			add(scr);
			scr.setDisabled(true);
		}
		scrolledTo(0);
	}
	
	private void scrolledTo(int sc){
		scrolling = sc;
		if(files==null)
			return;
		for (int j = 0; j < numberOfScroll; j++) {
			if(sc+j<files.length)
				ddp[j].setFTS(files[sc+j]);
			else
				ddp[j].setFTS(null);
		}
	}
	
	@Override
	public void clickedOn(File f) {
		debug.Debug.println("Loaded...", debug.Debug.SUBCOM);
		loadedComp(componetStorage.SCloadSave.load(f));
		closeYou();
	}
	
	public abstract void loadedComp(componetStorage.StoredComponent s);

}
