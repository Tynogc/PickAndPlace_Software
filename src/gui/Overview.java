package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;

import components.FootprintPainter;
import components.kiCad.KiCadPcbImport;
import status.PCBstate;
import status.StatusControle;
import main.SeyprisMain;
import menu.AbstractMenu;
import menu.DataFiled;
import menu.TextEnterButton;

public class Overview extends AbstractMenu{

	private DataFiled pcb1stat;
	private DataFiled pcb2stat;
	
	private process.PartPlacement pp;
	
	private DataFiled[] spindel;
	
	public Overview(main.KeyListener k, process.PartPlacement p) {
		super(0,70,SeyprisMain.sizeX()-300, SeyprisMain.sizeY()-100);
		moveAble = false;
		
		pcb1stat = new DataFiled(300,500,100,20,Color.black) {
			int i = -256;
			@Override
			protected void uppdate() {
				int u = StatusControle.stc.pcbs.pcbOne;
				if(u!=i){
					i = u;
					switch (u) {
					
					case PCBstate.EMPTY:
						setBlinking(true);
						setColor(Color.red);
						setText("Empty");
						break;
					case PCBstate.PROCESSING:
						setBlinking(false);
						setColor(Color.green);
						setText("Processing");
						break;
					case PCBstate.DONE:
						setBlinking(true);
						setColor(new Color(20,200,255));
						setText("Done!");
						break;
					case PCBstate.READY:
						setBlinking(false);
						setColor(lightBlue);
						setText("Ready");
						break;
					case PCBstate.NO_DATA:
					default:
						setBlinking(false);
						setColor(brown);
						setText("No Data");
						break;
					}
				}
			}
			@Override
			protected void isClicked() {
				pp.spindel.openCPmenue();
			}
		};
		add(pcb1stat);
		pcb2stat = new DataFiled(500,500,100,20,Color.black) {
			int i = -256;
			@Override
			protected void uppdate() {
				int u = StatusControle.stc.pcbs.pcbTwo;
				if(u!=i){
					i = u;
					switch (u) {
					
					case PCBstate.EMPTY:
						setBlinking(true);
						setColor(Color.red);
						setText("Empty");
						break;
					case PCBstate.PROCESSING:
						setBlinking(false);
						setColor(Color.green);
						setText("Processing");
						break;
					case PCBstate.DONE:
						setBlinking(true);
						setColor(new Color(20,200,255));
						setText("Done!");
						break;
					case PCBstate.READY:
						setBlinking(false);
						setColor(lightBlue);
						setText("Ready");
						break;
					case PCBstate.NO_DATA:
					default:
						setBlinking(false);
						setColor(brown);
						setText("No Data");
						break;
					}
				}
			}
			@Override
			protected void isClicked() {
				pp.request();
			}
		};
		add(pcb2stat);
		
		pp = p;
		
		add(new TextEnterButton(700,100,100,20,Color.white,k) {
			@Override
			protected void textEntered(String text) {
				pp.runTest(text);				
			}
		});
	}

	@Override
	protected void uppdateIntern() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void paintIntern(Graphics g) {
		pp.paintViewer(g, 630, 500);
		pp.paintSpindel(g, 30, 0);
		pp.paintProcessed(g, 800, 0);
	}

}
