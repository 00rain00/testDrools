package aiinterface;

import java.util.logging.Level;
import java.util.logging.Logger;

import loader.ResourceLoader;
import struct.FrameData;
import struct.GameData;
import struct.Key;

public class testDroolsAI implements AIInterface {

	@Override
	public int initialize(GameData gd, boolean playerNumber) {
		// TODO Auto-generated method stub
		Logger.getAnonymousLogger().log(Level.INFO, "initialize AI") ;
		return 0;
	}

	@Override
	public void getInformation(FrameData fd) {
		Logger.getAnonymousLogger().log(Level.INFO, "getInformation") ;
		// TODO Auto-generated method stub

	}

	@Override
	public void processing() {
		Logger.getAnonymousLogger().log(Level.INFO, "processing") ;
		// TODO Auto-generated method stub

	}

	@Override
	public Key input() {
		Logger.getAnonymousLogger().log(Level.INFO, "iinput") ;
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() {
		Logger.getAnonymousLogger().log(Level.INFO, "close") ;
		// TODO Auto-generated method stub

	}

	@Override
	public void roundEnd(int p1Hp, int p2Hp, int frames) {
		Logger.getAnonymousLogger().log(Level.INFO, "roundend") ;
		// TODO Auto-generated method stub

	}

}
