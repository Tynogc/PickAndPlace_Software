package debug;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class DebugFrame_Old {
	
	private static String[] text;
	private static byte[] colors;
	
	private static int position;
	
	private static DebugFrame_Old frameThing;
	
	private static final int length = 50;
	
	private JLabel[] labels;
	
	private JTextField tf;
	
	static{
		text = new String[length];
		colors = new byte[length];
	}
	
	public DebugFrame_Old(){
		JFrame f = new JFrame("Debug Console");
		f.setBounds(100,100,400,805);
		f.setLayout(null);
		
		labels = new JLabel[length];
		for (int i = 0; i < labels.length; i++) {
			labels[i] = new JLabel("*");
			labels[i].setBackground(Color.BLACK);
			labels[i].setOpaque(!false);
			labels[i].setForeground(Color.WHITE);
			labels[i].setBounds(0,i*15,400,15);
			f.add(labels[i]);
		}
		tf = new JTextField();
		tf.setBounds(0,750,400,20);
		tf.setBackground(Color.black);
		tf.setForeground(Color.white);
		tf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addString("   "+tf.getText(), 60);
				DebugComands_Old.comandTyped(tf.getText());
			}
		});
		f.add(tf);
		
		f.setVisible(true);
		
		frameThing = this;
	}
	
	public void refresh(){
		int k = 0;
		for (int i = position; i < length; i++) {
			labels[i].setLocation(0, k*15);
			k++;
		}
		for (int i = 0; i < position; i++) {
			labels[i].setLocation(0, k*15);
			k++;
		}
		if(position == 0){
			labels[length-1].setText(text[length-1]);
			labels[length-1].setForeground(getColor(colors[length-1]));
		}else{
			labels[position-1].setText(text[position-1]);
			labels[position-1].setForeground(getColor(colors[position-1]));
		}
	}
	
	public static void addString(String s, int color){
		if(!s.startsWith("* ")){
			if(!s.startsWith(" ")){
				if(!s.startsWith("/"))s = "* "+s;
			}
		}
		text[position] = s;
		colors[position] = (byte)color;
		
		position++;
		if(position >= length) position = 0;
		
		if(frameThing != null) frameThing.refresh();
	}
	
	private Color getColor(byte c){
		switch (c) {
		//Warning
		case 1:return Color.YELLOW;
		//All OK
		case 2:return Color.GREEN;
		//Error
		case 10:return Color.RED;
		//ComandProcess
		case 11:return new Color(0,180,240);
		//ComandProcessDuring run
		case 12:return new Color(130,130,250);
		//Important notice
		case 20:return new Color(190,0,60);
		//Comand
		case 60:return Color.BLUE;
		//Infos etc.
		default:
			return Color.WHITE;
		}
	}
	
	public static void throwEndMessage(String s){
		if(frameThing == null)frameThing = new DebugFrame_Old();
		addString("* Terminated!", 10);
		addString("  -Error Code ["+s+"]", 10);
		addString("  -System stopped at "+System.currentTimeMillis(), 20);
		addString("  -Frame counter "+0+" Last frame refresh rate "+0, 20);//TODO
		addString("* Runtime informations:", 20);
		Runtime r  = Runtime.getRuntime();
		long free = r.freeMemory();
		long total = r.maxMemory();
		long max = r.maxMemory();
		addString("  -RAM: Free "+free+"byte. Total "+total+"byte.", 0);
		addString("  -Maximum "+max+"byte.", 0);
		
		
	}
	

}
