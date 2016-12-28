package setup;

import java.io.PrintWriter;

import process.MachineLayout;

public class SetupControle {

	public ComponentFeeder[] reelFeeder;
	
	public ComponentFeeder[] trayFeeder;
	
	public SetupControle(MachineLayout m){
		trayFeeder = new ComponentFeeder[m.trayPosX.length];
		for (int i = 0; i < trayFeeder.length; i++) {
			trayFeeder[i] = new ComponentFeeder(m.trayPosX[i], m.trayPosY[i], m.traySize[i]) {
			};
		}
		
		reelFeeder = new ComponentFeeder[m.reelPosX.length];
		for (int i = 0; i < reelFeeder.length; i++) {
			reelFeeder[i] = new ComponentFeeder(m.reelPosX[i], m.reelPosY[i], m.reelSize[i]) {
			};
		}
	}
}
