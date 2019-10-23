package comment;
import struct.FrameData;
import struct.CharacterData;
public class Message {
	public String current;
	public String next;
	
	public Message(){
		this.current="";
		this.next="";
	}
	public String getCurrent() {
		return current;
	}
	public void setCurrent(String current) {
		this.current = current;
	}
	public String getNext() {
		return next;
	}
	public void setNext(String next) {
		this.next = next;
	}
	public String getPlayerName(boolean player) {
		return (player?"ZEN":"GARNET");
	}
	public float getHlScore(FrameData fd) {
		float hl = 0;
		float oriHp = 100;
		
		
		
		return hl;
	}
	
}
