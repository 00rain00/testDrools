package comment;
import struct.FrameData;
import struct.CharacterData;
import java.util.ArrayList;
public class Message {
	public String current;
	public String next;
	public ArrayList<String>comments;
	
	public ArrayList<String> getComments() {
		return comments;
	}
	public void setComments(ArrayList<String> comments) {
		this.comments = comments;
	}
	public Message(){
		this.current="";
		this.next="";
		this.comments = new ArrayList<String>();
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
	public void addComments(String comment) {
		comments.add(comment);
	}
	public void emptyComments(){
		comments.clear();
	}
	
	
}
