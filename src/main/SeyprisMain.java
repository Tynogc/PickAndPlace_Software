package main;

import gui.GuiControle;
import gui.MenuStorage;
import gui.PerformanceMenu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class SeyprisMain extends JPanel{
	
	private JFrame frame;
	private KeyListener key;
	private gui.GuiControle gui;
	private MouseListener mouse;
	
	private StartUp startUp;
	private StartAnimation anim;
	public static boolean playAnimation = true;
	
	private BufferedImage[] buffer;
	private int bufferInUse;
	
	private BufferedImage ima;
	
	private static FrameSize size;
	public final int playerOffsetX;
	public final int playerOffsetY;
	
	public static final int fpsMultiplier = 1;
	
	public static final int tileSize = 16;
	
	public static final boolean mapCreatorAllowed = true;
	
	public static boolean fullScreen = true;
	public static boolean observer = false;
	
	private BufferStrategy strategy;
	
	private int scaling = 3;
	
	public SeyprisMain(){
		Dimension dim = getToolkit().getScreenSize();
		size = new FrameSize(dim.width, dim.height);
		
		Runtime.getRuntime().addShutdownHook(new ExitThread());
		
		playerOffsetX = sizeX()/(tileSize*2);
		playerOffsetY = sizeY()/(tileSize*2);
		
		debug.DebugFrame dbf = new debug.DebugFrame();
		dbf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Fonts.createAllFonts();
		
		debug.Debug.println("* Starting Seypris 0.0.1");
		
		startUp = new StartUp(dbf);
		startUp.doStartUp();
		if(observer){
			size = new FrameSize(620,750);
		}
		
		debug.Debug.bootMsg("Settings", Settings.load());
		
		frame = new JFrame();
		frame.setBounds(10, 10, sizeX(), sizeY());
		setBounds(10, 10, sizeX(), sizeY());
		//frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		new PicLoader();
		
		setFocusable(false);
		frame.setFocusable(true);
		key = new KeyListener();
		frame.addKeyListener(key);
		
		//setDoubleBuffered(true);
		
//		buffer = new BufferedImage[]{
//				new BufferedImage(sizeX(), sizeY(), BufferedImage.TYPE_INT_ARGB),
//				new BufferedImage(sizeX(), sizeY(), BufferedImage.TYPE_INT_ARGB)
//		};
		
		if(fullScreen){
			frame.setUndecorated(true);
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		}
		
		mouse = new MouseListener();
		frame.addMouseListener(mouse);
		frame.addMouseMotionListener(mouse);
		frame.addMouseWheelListener(mouse);
		
		gui = new GuiControle(mouse, key, observer);
		if(!observer){
			new MenuStorage(key);
			GuiControle.setMiddleMenu(MenuStorage.overview);
		}
		
		new status.StatusControle();
		
		if(playAnimation)
			anim = new StartAnimation(true);
		
		if(!observer)
			debug.Debug.fifo = new comunication.Server().send;
		
		setVisible(true);
		frame.setVisible(true);
		
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		frame.createBufferStrategy(3);
		strategy = frame.getBufferStrategy();
		
		final MainThread mainThr = new MainThread(this);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				mainThr.start();
			}
		});
	}
	
	public void loop(int fps, int secFps, int thiFps, int triFps){
		
		Graphics2D g = null;
		try {
			g = (Graphics2D)strategy.getDrawGraphics();
		} catch (Exception e) {
			debug.Debug.println(e.toString(), debug.Debug.ERROR);
			return;
		}
		
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
		
		g.setColor(Color.black);
		g.fillRect(0, 0, sizeX(), sizeY());
		
		PerformanceMenu.markTime(PerformanceMenu.PlayerAndMap);
		
		PerformanceMenu.markTime(PerformanceMenu.NPCmovement);
		if(!gui.loop());
			//clickedOn.loop(player.xPos-playerOffsetX, player.yPos-playerOffsetY);
		PerformanceMenu.markTime(PerformanceMenu.UpdateGui);

		PerformanceMenu.markTime(PerformanceMenu.PaintBack);
		
		PerformanceMenu.markTime(PerformanceMenu.PaintEntity);
		gui.paint(g);
		if(anim != null){
			if(anim.uppdate())
				anim = null;
			else
				anim.paint(g, sizeX()/2-185, sizeY()/2-150);
		}
		PerformanceMenu.markTime(PerformanceMenu.PaintGui);
		
		mouse.leftClicked = false;
		mouse.rightClicked = false;
		
		g.setColor(Color.cyan);
		g.setFont(Fonts.font12);
		g.drawString("FPS: "+fps, 0, 54);
		g.drawString("T: ["+thiFps+"] ["+triFps+"]", 0, 70);
		if(secFps<0)g.setColor(Color.red);
		secFps = -secFps+MainThread.timeToFrameUppdate;
		double fpsDpe = (double)secFps/MainThread.timeToFrameUppdate;
		secFps = (int)(fpsDpe*100.0);
		g.drawString("Load:"+secFps/100+""+(secFps/10)%10+""+secFps%10+"%", 50, 54);
				
		g.dispose();
		//paint(getGraphics());
		strategy.show();
		PerformanceMenu.markTime(PerformanceMenu.RedrawBack);
	}
	
	public void saveLoop(){
		
	}
	
	@Override
	public void paint(Graphics g) {
		//super.paint(g);
		if(g!=null && ima != null)
			g.drawImage(ima, 0, 0, null);
	}
	
	public static int sizeX(){
		return size.xSize;
	}
	public static int sizeY(){
		return size.ySize;
	}
	
	public static void main(String[] a){
		new SeyprisMain();
	}

}

class FrameSize{
	final int xSize;
	final int ySize;
	
	public FrameSize(int x, int y){
		xSize = x;
		ySize = y;
	}
}
