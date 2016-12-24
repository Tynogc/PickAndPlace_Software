package gui.subMenu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;

import componetStorage.SCloadSave;
import componetStorage.StoredComponent;
import utility.FileLoader;
import main.Fonts;
import main.PicLoader;
import main.SeyprisMain;
import menu.Button;
import menu.ScrollBar;
import menu.TextEnterButton;
import gui.GuiControle;
import gui.MoveMenu;

public class ComponentLibrary extends MoveMenu implements OpenFile{

	private FileToSearch[] que;
	private FileToSearch[] tags;
	
	private String searchCriterea = "";
	
	private int scrollPos;
	
	private ScrollBar scr;
	private static final int scrollSize = 24;
	private Button back;
	
	private DataPoint[] ddp;
	
	private String directory;
	
	private FileLoader clicked;
	
	public ComponentLibrary() {
		super(400,100,PicLoader.pic.getImage("res/ima/mbe/mLoad.png"), "Component Library");
		
		ddp = new DataPoint[scrollSize];
		for (int i = 0; i < ddp.length; i++) {
			ddp[i] = new DataPoint(10, i*30+70, this);
			add(ddp[i]);
		}
		
		Button neu = new Button(350,30,"res/ima/cli/B") {
			@Override
			protected void uppdate() {}
			@Override
			protected void isFocused() {}
			@Override
			protected void isClicked() {
				clickedNew(new StoredComponent());
			}
		};
		add(neu);
		neu.setTextColor(Button.gray);
		neu.setText("Create New");
		
		back = new Button(30,30,"res/ima/cli/Gsk") {
			@Override
			protected void uppdate() {}
			@Override
			protected void isFocused() {}
			@Override
			protected void isClicked() {
				setDisabled(true);
				loadToFile(new File("user/components"));
				directory = "";
			}
		};
		add(back);
		back.setText("Back");
		back.setDisabled(true);
		
		TextEnterButton search = new TextEnterButton(130,30,100,20,Color.white,SeyprisMain.getKL()) {
			@Override
			protected void textEntered(String text) {
				if(!text.startsWith("***Search**")){
					searchCriterea = text;
					search();
				}
			}
		};
		add(search);
		search.setText("***Search***");
		
		directory = "";
		
		loadToFile(new File("user/components"));
	}

	@Override
	protected void paintSecond(Graphics g) {
		g.setColor(Color.white);
		g.setFont(Fonts.font14);
		g.drawString(directory, 30, 67);
	}

	@Override
	protected boolean close() {
		return true;
	}

	@Override
	protected void uppdateIntern() {
		if(scr.getScrolled()  != scrollPos)
			scrolled(scr.getScrolled());
		
	}
	
	private void loadToFile(File f){
		File[] fq1 = f.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isDirectory();
			}
		});
		File[] fq2 = f.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".component");
			}
		});
		que = new FileToSearch[fq1.length+fq2.length];
		int j = 0;
		for (int i = 0; i < fq1.length; i++) {
			que[j] = new FileToSearch(fq1[i]);
			j++;
		}
		for (int i = 0; i < fq2.length; i++) {
			que[j] = new FileToSearch(fq2[i]);
			j++;
		}
		search();
	}
	
	private void search(){
		int i = 0;
		for (int j = 0; j < que.length; j++) {
			if(compareStrings(que[j].name,searchCriterea)||compareStrings(que[j].id,searchCriterea)){
				i++;
			}
		}
		tags = new FileToSearch[i];
		i = 0;
		for (int j = 0; j < que.length; j++) {
			if(compareStrings(que[j].name,searchCriterea)||compareStrings(que[j].id,searchCriterea)){
				tags[i] = que[j];
				i++;
			}
		}
		
		if(scr != null)
			remove(scr);
		if(tags.length<=scrollSize){
			scr = new ScrollBar(310, 70, 700, 10, 20);
			scr.setDisabled(true);
		}else{
			scr = new ScrollBar(310, 70, 700, scrollSize, tags.length);
		}
		add(scr);
		
		scrolledTo(0);
	}
	private void scrolledTo(int i){
		scrollPos = i;
		for (int j = 0; j < scrollSize; j++) {
			if(i+j<tags.length)
				ddp[j].setFTS(tags[i+j]);
			else
				ddp[j].setFTS(null);
		}
	}
	
	public void clickedOn(File f){
		if(f.isDirectory()){
			directory += f.getName()+"/";
			searchCriterea = "";
			loadToFile(f);
			back.setDisabled(false);
		}else{
			if(clicked!=null){
				clicked.fileLoaded(f);
			}else{
				clickedNew(SCloadSave.load(f));
			}
			closeYou();
		}
	}
	
	public void setActionClicked(FileLoader f){
		clicked = f;
	}
	private void clickedNew(StoredComponent sc){
		ComponentSetup cs = new ComponentSetup(xPos,yPos, sc);
		GuiControle.addMenu(cs);
		cs.setActionDone(clicked);
		cs.setFilePath(directory);
		closeYou();
	}
	
	private boolean compareStrings(String s1, String s2){
		if(s2.length()> s1.length())
			return false;
		for (int i = 0; i < s2.length(); i++) {
			char c1 = s1.charAt(i);
			char c2 = s2.charAt(i);
			if(Character.toLowerCase(c1)!=Character.toLowerCase(c2))
				return false;
		}
		return true;
	}

}
class DataPoint extends Button{

	private OpenFile comLib;
	public FileToSearch fts;
	
	public DataPoint(int x, int y, OpenFile cl) {
		super(x, y, "res/ima/cli/spb/bro/B");
		comLib = cl;
		setBold(false);
		setTextColor(Color.black);
	}
	
	public void setFTS(FileToSearch f){
		if(f == null){
			setText("---");
			setSecondLine(null);
			state1 = PicLoader.pic.getImage("res/ima/cli/spb/bro/Bn.png");
		}else{
			setText(f.name);
			setSecondLine(f.id);
			if(f.f.isDirectory()){
				setSecondLine("FOLDER");
				state1 = PicLoader.pic.getImage("res/ima/cli/spb/bro/Folder.png");
			}else{
				state1 = PicLoader.pic.getImage("res/ima/cli/spb/bro/Bn.png");
			}
		}
		fts = f;
	}

	@Override
	protected void isClicked() {
		if(fts != null)
			comLib.clickedOn(fts.f);
	}

	@Override
	protected void isFocused() {
		
	}

	@Override
	protected void uppdate() {
		
	}
	
}
class FileToSearch{
	public String name = "EMPTY";
	public String id = "EMPTY";
	public final File f;
	public FileToSearch(File f){
		this.f = f;
		if(f.isDirectory()){
			id = f.getName();
			name = f.getName();
			return;
		}
		
		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			name = br.readLine();
			id = br.readLine();
			br.close();
		} catch (FileNotFoundException e) {
			debug.Debug.println("*This File dosn't exist...", debug.Debug.COMERR);
		} catch (Exception e) {
			debug.Debug.println("*ERROR reading Component: "+e.getMessage(), debug.Debug.ERROR);
		}
	}
}

interface OpenFile{
	public void clickedOn(File f);
}
