package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import components.PCB;
import components.PcbPainter;
import components.kiCad.KiCadPcbImport;
import menu.AbstractMenu;
import menu.Button;
import menu.CheckBox;
import menu.Container;
import menu.DataFiled;
import menu.ScrollBar;
import project.PCBcomponentToReel;
import project.ProjectFile;

public class ProjectMenu extends AbstractMenu{

	private BufferedImage pcbIma;
	private PcbPainter[] painter;
	private PCB pcb;
	private ProjectFile project;
	
	private CheckBox[] scale;
	private CheckBox showName;
	private int pcX;
	private int pcY;
	private int ulc;
	
	private Container comToReel_container;
	private ScrollBar scr;
	private CompDataField[] coms;
	private int position;
	private static final int numOfButtons = 20;
	
	public ProjectMenu() {
		super(0,70,1600,1000);
		
		ulc = 150;
		moveAble = false;
		
		Button load = new Button(100,600,"res/ima/cli/G") {
			@Override
			protected void uppdate() {}
			@Override
			protected void isFocused() {}
			@Override
			protected void isClicked() {
				loadPCB(new File("PowerDistribution.kicad_pcb"));
			}
		};
		add(load);
		load.setText("Load");
		
		scale = new CheckBox[3];
		scale[0] = new CheckBox(1000,500,"res/ima/cli/cbx/CB", 100) {
			@Override
			public void changed(boolean b) {
				scale[0].setState(true);
				scale[1].setState(!true);
				scale[2].setState(!true);
				showName.setState(false);
				redrawPcb();
				ulc = 150;
			}
		};
		add(scale[0]);
		scale[0].setText("2:1");
		scale[1] = new CheckBox(1000,530,"res/ima/cli/cbx/CB", 100) {
			@Override
			public void changed(boolean b) {
				scale[0].setState(!true);
				scale[1].setState(true);
				scale[2].setState(!true);
				showName.setState(true);
				redrawPcb();
				ulc = 50;
			}
		};
		add(scale[1]);
		scale[1].setText("6:1");
		scale[2] = new CheckBox(1000,560,"res/ima/cli/cbx/CB", 100) {
			@Override
			public void changed(boolean b) {
				scale[0].setState(!true);
				scale[1].setState(!true);
				scale[2].setState(true);
				showName.setState(true);
				redrawPcb();
				ulc = 17;
			}
		};
		add(scale[2]);
		scale[2].setText("18:1");
		scale[0].setState(true);
		
		showName = new CheckBox(100,630,"res/ima/cli/cbx/CB", 100) {
			@Override
			public void changed(boolean b) {
				redrawPcb();
			}
		};
		add(showName);
		showName.setText("Show Name");
		
		pcbIma = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);
		
		DataFiled pos = new DataFiled(400,600,150,150,Color.DARK_GRAY) {
			int lx = -1;
			int ly;
			@Override
			protected void uppdate() {}
			
			@Override
			protected void isClicked() {
				
			}
			@Override
			public void leftClicked(int x, int y) {
				lx = pcX;
				ly = pcY;
				super.leftClicked(x, y);
			}
			@Override
			public void checkMouse(int x, int y) {
				if(isMouseHere(x, y) && lx>=0){
					pcX = (x-xPos)*2-ulc/2;
					pcY = (y-yPos)*2-ulc/2;
					if(pcX/2+ulc/2>xSize)pcX = (xSize-ulc/2)*2;
					if(pcY/2+ulc/2>ySize)pcY = (ySize-ulc/2)*2;
					if(pcX<ulc)pcX = ulc;
					if(pcY<ulc)pcY = ulc;
				}
				super.checkMouse(x, y);
			}
			@Override
			public void leftReleased(int x, int y) {
				if(isMouseHere(x, y)){
					redrawPcb();
					setText("Pos "+pcX+" "+pcY);
				}else if(lx>=0){
					pcX = lx;
					pcY = ly;
				}
				lx = -1;
				super.leftReleased(x, y);
			}
			@Override
			public void paintYou(Graphics g) {
				super.paintYou(g);
				g.setColor(Color.yellow);
				g.drawRect(xPos+pcX/2-ulc/2, yPos+pcY/2-ulc/2, ulc, ulc);
			}
		};
		add(pos);
		pos.setText("Pos 0 0");
		pcX = ulc;
		pcY = ulc;
		
		comToReel_container = new Container(10, 30);
		coms = new CompDataField[numOfButtons];
		for (int i = 0; i < coms.length; i++) {
			coms[i] = new CompDataField(0,i*20);
			comToReel_container.addInContainer(coms[i]);
		}
		add(comToReel_container);
	}

	@Override
	protected void uppdateIntern() {
		if(scr!=null)
		if(position != scr.getScrolled())
			scrolled();
	}

	@Override
	protected void paintIntern(Graphics g) {
		g.drawImage(pcbIma, 400, 0, null);
		g.setColor(Color.darkGray);
		g.drawRect(400, 0, 600, 600);
	}
	
	private void redrawPcb(){
		if(painter == null)return;
		Graphics2D g = (Graphics2D)pcbIma.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, 600, 600);
		if(scale[0].getState()){
			g.translate(-pcX*2+300,-pcY*2+300);
			painter[0].paint(g, showName.getState());
		}
		if(scale[1].getState()){
			g.translate(-pcX*6+300,-pcY*6+300);
			painter[1].paint(g, showName.getState());
		}
		if(scale[2].getState()){
			g.translate(-pcX*18+300,-pcY*18+300);
			painter[2].paint(g, showName.getState());
		}
	}
	
	private void loadPCB(File f){
		pcb = new KiCadPcbImport(f).pcb;
		painter = new PcbPainter[]{
				new PcbPainter(pcb, 2, Color.red, true),
				new PcbPainter(pcb, 6, Color.red, true),
				new PcbPainter(pcb, 18, Color.red, false)
		};
		project = new ProjectFile(pcb);
		redrawPcb();
		if(scr != null){
			remove(scr);
		}
		if(project.size<=numOfButtons){
			scr = new ScrollBar(260, 30, 400, 20, 40);
			scr.setDisabled(true);
		}else
			scr = new ScrollBar(260, 30, 400, numOfButtons, project.size);
		add(scr);
		scrolled();
	}
	
	private void scrolled(){
		position = scr.getScrolled();
		for (int i = 0; i < coms.length; i++) {
			if(position+i<project.size){
				coms[i].updatePCTR(project.componentMatch[position+i]);
			}else{
				coms[i].updatePCTR(null);
			}
		}
	}
}

class CompDataField extends DataFiled{

	public PCBcomponentToReel pcbToReel;
	
	public CompDataField(int x, int y) {
		super(x, y, 250,20,Color.white);
		rebindText();
	}

	@Override
	protected void isClicked() {
		if(pcbToReel != null){
			//FIXME
		}
	}

	@Override
	protected void uppdate() {}
	
	@Override
	public void longTermUpdate() {
		super.longTermUpdate();
		rebindText();
	}
	
	public void updatePCTR(PCBcomponentToReel p){
		pcbToReel = p;
		rebindText();
	}
	
	private void rebindText(){
		if(pcbToReel == null){
			setText("---");
			return;
		}
		setText(pcbToReel.fp.reference+" ");
	}
}
