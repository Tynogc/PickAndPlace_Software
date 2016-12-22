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

import main.Fonts;
import main.PicLoader;
import menu.Button;
import menu.ScrollBar;
import gui.MoveMenu;

public class ComponentLibrary extends MoveMenu{

	private FileToSearch[] que;
	private FileToSearch[] tags;
	
	private String searchCriterea = "";
	
	private int scrollPos;
	
	private ScrollBar scr;
	private static final int scrollSize = 24;
	
	private DataPoint[] ddp;
	
	private String directory;
	
	public ComponentLibrary() {
		super(400,100,PicLoader.pic.getImage("res/ima/mbe/mLoad.png"), "Component Library");
		
		ddp = new DataPoint[scrollSize];
		for (int i = 0; i < ddp.length; i++) {
			ddp[i] = new DataPoint(10, i*30+70, this);
			add(ddp[i]);
		}
		
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
			if(que[j].name.startsWith(searchCriterea)||que[j].id.startsWith(searchCriterea)){
				i++;
			}
		}
		tags = new FileToSearch[i];
		i = 0;
		for (int j = 0; j < que.length; j++) {
			if(que[j].name.startsWith(searchCriterea)||que[j].id.startsWith(searchCriterea)){
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
			directory += "/"+f.getName();
			loadToFile(f);
		}
	}

}
class DataPoint extends Button{

	private ComponentLibrary comLib;
	public FileToSearch fts;
	
	public DataPoint(int x, int y, ComponentLibrary cl) {
		super(x, y, "res/ima/cli/spb/bro/B");
		comLib = cl;
		setBold(false);
		setTextColor(Color.black);
	}
	
	public void setFTS(FileToSearch f){
		if(f == null){
			setText("---");
			setSecondLine(null);
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
			name = "";
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
