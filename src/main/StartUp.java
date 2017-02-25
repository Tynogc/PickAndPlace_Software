package main;

public class StartUp {
	
	private debug.DebugFrame frame;
	
	private boolean spectator = false;
	private boolean fullScreen = true;
	
	private boolean askLic;
	
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
		askLic = true;
		debug.Debug.println("* Quick StartUp? [Y|n]");
		if(question(true)) return;
		askLic = false;
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
			if(askLic){
				if(frame.canKey() == 'l' || frame.canKey() == 'L'){
					SeyprisMain.displayLicense();
					askLic = false;
				}
			}
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
