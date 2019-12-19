package comment;
import struct.FrameData;
import struct.CharacterData;
import java.util.ArrayList;
public class Message {
	public String current;
	public String next;
	public ArrayList<String>comments;
	public boolean combo;
	public int offence;
	public String state;
	public boolean corner;
	public String action;
	public boolean ult;
	public String playerName;
	public boolean jump;
	public boolean hp;
	public boolean heavy;
	public boolean end;
	public boolean block;
	public boolean isBlock() {
		return block;
	}
	public void setBlock(boolean block) {
		this.block = block;
	}
	public boolean isEnd() {
		return end;
	}
	public void setEnd(boolean end) {
		this.end = end;
	}
	public boolean isHeavy() {
		return heavy;
	}
	public void setHeavy(boolean heavy) {
		this.heavy = heavy;
	}
	public Message(){
		this.current="";
		this.next="";
		this.comments = new ArrayList<String>();
		this.offence=-1;
		this.corner=false;
		this.ult=false;
		this.jump=false;
		this.hp=false;
		this.end = false;
		this.playerName="ZEN";
		this.action="kick";
	}
	public boolean isHp() {
		return hp;
	}

	public void setHp(boolean hp) {
		this.hp = hp;
	}

	public boolean isJump() {
		return jump;
	}

	public void setJump(boolean jump) {
		this.jump = jump;
	}

	public String getPlayerName() {
		return playerName;
	}
	
	
	public boolean isUlt() {
		return ult;
	}
	public void setUlt(boolean ult) {
		this.ult = ult;
	}
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public boolean isCorner() {
		return corner;
	}
	public void setCorner(boolean corner) {
		this.corner = corner;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getOffence() {
		return offence;
	}
	public void setOffence(int offence) {
		this.offence = offence;
	}
	public boolean isCombo() {
		return combo;
	}
	public void setCombo(boolean combo) {
		this.combo = combo;
	}
	public ArrayList<String> getComments() {
		return comments;
	}
	public void setComments(ArrayList<String> comments) {
		this.comments = comments;
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
	public void setPlayerName(boolean player) { //1 zen 2 g
		this.playerName=player?"ZEN":"GARNET";
	}
	public void addComments(String comment) {
		comments.add(comment);
	}
	public void emptyComments(){
		comments.clear();
	}
	
	
}
