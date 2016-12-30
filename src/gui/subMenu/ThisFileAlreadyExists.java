package gui.subMenu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import gui.GuiControle;
import gui.MoveMenu;
import main.PicLoader;
import menu.Button;
import utility.FileLoader;
import utility.SaveAble;

public class ThisFileAlreadyExists extends MoveMenu{

	private String filePath;
	
	public ThisFileAlreadyExists(int x, int y, final SaveAble s, final String fp, final FileLoader fl) {
		super(x, y, PicLoader.pic.getImage("res/ima/mbe/warn.png"), fp+" already Exists!");
		Button b1 = new Button(100,140,"res/ima/cli/G") {
			@Override
			protected void uppdate() {}
			@Override
			protected void isFocused() {}
			
			@Override
			protected void isClicked() {
				s.save(fp);
				if(fl != null)
					fl.fileLoaded(new File(fp));
				
				closeYou();
			}
		};
		b1.setText("Overwrite");
		add(b1);
		Button b2 = new Button(240,140,"res/ima/cli/G") {
			@Override
			protected void uppdate() {}
			@Override
			protected void isFocused() {}
			
			@Override
			protected void isClicked() {
				closeYou();
			}
		};
		b2.setText("Cancel");
		add(b2);
		
		filePath = fp;
		
		closeOutside = true;
	}

	@Override
	protected void paintSecond(Graphics g) {
		g.setFont(main.Fonts.fontBold18);
		g.setColor(Color.red);
		g.drawString("Overwrite File?", 40, 70);
		g.setFont(main.Fonts.font12);
		g.setColor(Color.white);
		g.drawString(filePath, 50, 70);
	}

	@Override
	protected boolean close() {
		return true;
	}

	@Override
	protected void uppdateIntern() {
		
	}
	
	public static void saveFile(SaveAble s, String fp, int x, int y, FileLoader fl){
		File f = new File(fp);
		if(f.exists()){
			debug.Debug.println("*"+fp+" already exists!", debug.Debug.COMERR);
			GuiControle.addMenu(new ThisFileAlreadyExists(x, y, s, fp, fl));
		}else{
			s.save(fp);
			if(fl != null)
				fl.fileLoaded(new File(fp));
		}
	}

}
