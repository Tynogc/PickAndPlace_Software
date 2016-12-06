package main;

public class StartUp {
	
	private debug.DebugFrame frame;
	
	private boolean spectator;
	private boolean fullScreen;
	
	public StartUp(debug.DebugFrame f){
		frame = f;
	}
	
	public void doStartUp(){
		gtt();
		SeyprisMain.fullScreen = fullScreen;
		SeyprisMain.observer = spectator;
	}
	
	private void gtt(){
		//debug.Debug.println("* Enter Password:");
		//pw();
		debug.Debug.println("* Quick StartUp? [Y|n]");
		if(question(true)) return;
		debug.Debug.println("* Full Screen? [Y|n]");
		fullScreen = question(true);
		debug.Debug.println("* Spectator Mode? [y|N]");
		spectator = question(false);
	}
	
	@SuppressWarnings("static-access")
	public void pw(){
		frame.setPwState(true);
		while(frame.canState()==0){
			try {
				Thread.currentThread().sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		frame.setPwState(false);
	}
	
	public boolean question(boolean enter){
		boolean t = quete(enter);
		frame.setCheckState(false);
		if(t){
			debug.Debug.println("* YES");
		}else{
			debug.Debug.println("* NO");
		}
		return t;
	}
	
	@SuppressWarnings("static-access")
	public boolean quete(boolean enter){
		frame.setCheckState(true);
		
		while (true){
			switch (frame.canState()) {
			case 0:
				try {
					Thread.currentThread().sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
				
			case 1:
				return true;
			case 2:
				return false;
			case 3:
				return enter;

			default:
				break;
			}
			
		}
	}

}
