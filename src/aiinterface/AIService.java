package aiinterface;

import struct.FrameData;
import struct.GameData;
import struct.Key;

public class AIService implements AIInterface {

	public AIService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int initialize(GameData gd, boolean playerNumber) {
		// TODO Auto-generated method stub
		System.out.println("AIService");
		return 0;
	}

	@Override
	public void getInformation(FrameData fd) {
		// TODO Auto-generated method stub

	}

	@Override
	public void processing() {
		// TODO Auto-generated method stub

	}

	@Override
	public Key input() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public void roundEnd(int p1Hp, int p2Hp, int frames) {
		// TODO Auto-generated method stub

	}

}
