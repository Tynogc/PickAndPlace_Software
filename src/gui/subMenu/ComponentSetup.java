package gui.subMenu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFileChooser;

import components.FootprintPainter;
import components.FootprintPainterToShow;
import componetStorage.StoredComponent;
import main.PicLoader;
import menu.Button;
import menu.CheckBox;
import menu.DataFiled;
import menu.DropDownButton;
import menu.DropDownMenu;
import menu.TextEnterButton;

public class ComponentSetup extends gui.MoveMenu{
	
	private StoredComponent reel;
	private FootprintPainterToShow fpp;
	
	private DropDownMenu orientation;
	
	private BufferedImage[] imas;
	
	private TextEnterButton name;
	private TextEnterButton hight;
	private TextEnterButton pue_validation;
	
	private CheckBox pue_Retry;
	private DropDownMenu pue_action;
	
	private TextEnterButton barCode;
	
	private CheckBox checkPartPicked;
	private DropDownMenu checkPartReeled;
	private CheckBox checkPartReeledBlack;
	
	private boolean valOK;
	
	private Button save;

	public ComponentSetup(int x, int y, StoredComponent s) {
		super(x, y, PicLoader.pic.getImage("res/ima/mbe/m400x800.png"), "Setup Component");
		reel = s;
		
		Button loadFp = new Button(230,100,"res/ima/cli/B") {
			
			@Override
			protected void uppdate() {}
			
			@Override
			protected void isFocused() {}
			
			@Override
			protected void isClicked() {
				openMenu(1);
			}
		};
		loadFp.setText("Load Footprint");
		loadFp.setTextColor(Button.gray);
		add(loadFp);
		
		save = new Button(130,750,"res/ima/cli/B") {
			
			@Override
			protected void uppdate() {}
			
			@Override
			protected void isFocused() {}
			
			@Override
			protected void isClicked() {
				save();
			}
		};
		save.setText("Save to File");
		save.setTextColor(Button.gray);
		add(save);
		
		Button loadTool = new Button(230,200,"res/ima/cli/G") {
			
			@Override
			protected void uppdate() {}
			
			@Override
			protected void isFocused() {}
			
			@Override
			protected void isClicked() {
				//TODO...
			}
		};
		loadTool.setText("Select Tool");
		add(loadTool);
		
		Button reelSetup = new Button(75,450,"res/ima/cli/G") {
			
			@Override
			protected void uppdate() {}
			
			@Override
			protected void isFocused() {}
			
			@Override
			protected void isClicked() {
				openMenu(2);
			}
		};
		reelSetup.setText("Setup...");
		add(reelSetup);
		
		DataFiled toolNumber = new DataFiled(230,170,80,20,Color.blue) {
			@Override
			protected void uppdate() {}
			@Override
			protected void isClicked() {}
			@Override
			public void longTermUpdate() {
				setText("Tool "+reel.toolToUse);
				super.longTermUpdate();
			}
		};
		add(toolNumber);
		toolNumber.setTextColor(Color.white);
		
		orientation = new DropDownMenu(70,260,100) {
			@Override
			protected void changed(int i) {
				opneFpp();
				reel.partOrientation = i;
			}
		};
		orientation.addSubButton(new DropDownButton(100, 20, "0 Degree"), StoredComponent.ORIENTATION_0_DEGREE);
		orientation.addSubButton(new DropDownButton(100, 20, "90 Degree"), StoredComponent.ORIENTATION_90_DEGREE);
		orientation.addSubButton(new DropDownButton(100, 20, "180 Degree"), StoredComponent.ORIENTATION_180_DEGREE);
		orientation.addSubButton(new DropDownButton(100, 20, "270 Degree"), StoredComponent.ORIENTATION_270_DEGREE);
	
		imas = new BufferedImage[]{
			PicLoader.pic.getImage("res/ima/gui/par/feederDirection.png"),
			PicLoader.pic.getImage("res/ima/gui/par/1.png"),
			PicLoader.pic.getImage("res/ima/gui/par/2.png"),
			PicLoader.pic.getImage("res/ima/gui/par/3.png"),
			PicLoader.pic.getImage("res/ima/gui/par/tool.png"),
			PicLoader.pic.getImage("res/ima/gui/par/4.png"),
			PicLoader.pic.getImage("res/ima/gui/par/pue.png"),
			PicLoader.pic.getImage("res/ima/gui/par/checkFP.png"),
			PicLoader.pic.getImage("res/ima/gui/par/checkFPBB.png"),
			PicLoader.pic.getImage("res/ima/gui/par/checkFPBW.png")
		};
		valOK = true;
		
		name = new TextEnterButton(30,660,150,20,Color.white,main.SeyprisMain.getKL()) {
			@Override
			protected void textEntered(String text) {
				reel.name = text;
			}
		};
		add(name);
		
		barCode = new TextEnterButton(30,700,340,20,Color.white,main.SeyprisMain.getKL()) {
			@Override
			protected void textEntered(String text) {
				reel.id = text;
			}
		};
		add(barCode);
		
		hight = new TextEnterButton(71,370,100,20,Color.white,main.SeyprisMain.getKL()) {
			@Override
			protected void textEntered(String text) {
				double d = checkValue(this);
				if(d!=Double.NaN)
					reel.hight = d;
			}
		};
		add(hight);
		
		pue_validation = new TextEnterButton(71,610,100,20,Color.white,main.SeyprisMain.getKL()) {
			@Override
			protected void textEntered(String text) {
				double d = checkValue(this);
				if(d!=Double.NaN)
					reel.pue_Percent = d;
			}
		};
		add(pue_validation);
		
		pue_Retry = new CheckBox(200,580,"res/ima/cli/cbx/CB",100) {
			@Override
			public void changed(boolean b) {
				reel.pue_retry = b;
			}
		};
		add(pue_Retry);
		pue_Retry.setText("Retry Picking");
		pue_Retry.setTextColor(Color.white);
		
		pue_action = new DropDownMenu(200,610,100) {
			@Override
			protected void changed(int i) {
				reel.pue_action = i;
			}
		};
		pue_action.addSubButton(new DropDownButton(100,20,"Stop"), StoredComponent.PUE_ACTION_STOP);
		pue_action.addSubButton(new DropDownButton(100,20,"Note at end"), StoredComponent.PUE_ACTION_NOTE);
		pue_action.addSubButton(new DropDownButton(100,20,"Ignore"), StoredComponent.PUE_ACTION_IGNORE);
		DropDownButton ddb = new DropDownButton(100,20,"Place");
		ddb.setColor(Color.red);
		pue_action.addSubButton(ddb, StoredComponent.PUE_ACTION_PLACE);
		
		checkPartPicked = new CheckBox(75,500,"res/ima/cli/cbx/CB",100) {
			@Override
			public void changed(boolean b) {
				toggledCPP(b);
				reel.checkPartPicked = b;
			}
		};
		add(checkPartPicked);
		checkPartPicked.setText("Check Picked");
		
		checkPartReeledBlack = new CheckBox(262,500,"res/ima/cli/cbx/CB",100) {
			@Override
			public void changed(boolean b) {
				reel.reelBlack = b;
			}
		};
		add(checkPartReeledBlack);
		checkPartReeledBlack.setText("Tape is Black");
		
		checkPartReeled = new DropDownMenu(262,530,100){
			protected void changed(int i) {
				reel.checkPartBeforPicking = i;
		};};
		checkPartReeled.addSubButton(new DropDownButton(100, 20, "Check at Start"), StoredComponent.CHECK_FIRST);
		checkPartReeled.addSubButton(new DropDownButton(100, 20, "Check every Time"), StoredComponent.CHECK_EVERY);
		checkPartReeled.addSubButton(new DropDownButton(100, 20, "Check Never"), StoredComponent.CHECK_NEVER);
		add(checkPartReeled);
		
		add(pue_action);
		add(orientation);
		
		checkPartReeled.setCurrentlyActiv(reel.checkPartBeforPicking);
		checkPartPicked.setState(reel.checkPartPicked);
		hight.setText(""+reel.hight);
		orientation.setCurrentlyActiv(reel.partOrientation);
		name.setText(reel.name);
		barCode.setText(reel.id);
		pue_validation.setText(""+reel.pue_Percent);
	}
	
	@Override
	public void longTermUpdate() {
		super.longTermUpdate();
		if(reel.fp != null){
			if(fpp != null){
				if(fpp.name.compareTo(reel.fp.name)==0)
					return;
			}
			opneFpp();
		}
	}
	
	private void opneFpp(){
		if(reel.fp != null)
			fpp = new FootprintPainterToShow(reel.fp, 200,true,Color.red,true,orientation.getState());
	}

	@Override
	protected void paintSecond(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(10, 40, 200, 200);
		g.drawImage(imas[0], 10, 140, null);
		if(fpp != null)
			g.drawImage(fpp.buffer, 10, 40, null);
		
		g.drawImage(imas[1], 10, 260, null);
		g.drawImage(imas[2], 320, 40, null);
		g.drawImage(imas[4], 320, 140, null);
		g.drawImage(imas[5], 10, 420, null);
		g.drawImage(imas[3], 10, 340, null);
		g.drawImage(imas[7], 10, 500, null);
		g.drawImage(imas[6], 10, 580, null);
		
		if(checkPartReeledBlack.getState())
			g.drawImage(imas[8], 200, 500, null);
		else
			g.drawImage(imas[9], 200, 500, null);
		
		g.setFont(main.Fonts.font12);
		g.setColor(Color.white);
		g.drawString("Part Position in Reel", 20, 257);
		g.drawString("Component Hight", 20, 337);
		g.drawString("Reel/Tray/Tube - Setup", 20, 417);
		g.drawString("Pickup-Error", 20, 577);
		g.drawString("Check Footprint", 20, 497);
		g.drawString("Check Part befor Picking", 200, 497);
		g.drawString("Barcode", 20, 697);
		g.drawString("Hight in mm", 71, 365);
		g.drawString("Percent detected", 71, 605);
	}

	@Override
	protected boolean close() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void uppdateIntern() {
		// TODO Auto-generated method stub
		
	}
	
	private double checkValue(TextEnterButton t){
		String s = t.getText();
		double d;
		try {
			d = Double.parseDouble(s);
		} catch (Exception e) {
			t.setTextColor(Color.red);
			valOK = false;
			return Double.NaN;
		}
		t.setTextColor(Color.black);
		valOK = true;
		return d;
	}
	
	private void toggledCPP(boolean b){
		if(b)
			checkPartPicked.setTextColor(Color.white);
		else
			checkPartPicked.setTextColor(Color.red);
		
		pue_action.setDisabled(!b);
		pue_Retry.setDisabled(!b);
		pue_validation.setDisabled(!b);
	}
	
	private void openMenu(int q){
		if(q == 1)
			gui.GuiControle.addMenu(new FootprintSetup(xPos+30, yPos+50, reel));
		if(q == 2)
			gui.GuiControle.addMenu(new ReelSetup(xPos+30, yPos+50, reel));
	}
	
	private void save(){
		reel.id = barCode.getText();
		ThisFileAlreadyExists.saveFile(reel, "user/components/"+reel.id+".component", xPos+100, yPos+100);
	}
	
}
