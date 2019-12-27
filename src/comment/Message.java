package comment;
import struct.FrameData;
import struct.CharacterData;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;
import ice_agent.TTSBridge;
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
		this.playerName="P1";
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
		this.playerName=player?"P1":"P2";
	}
	public void addComments(String comment) {
		comments.add(comment);
	}
	public void emptyComments(){
		comments.clear();
	}
	public void deleteRepeatComments(boolean hlFlag)  {
		//shink size
		Set<String> commentSet = new LinkedHashSet<>();
		
		if(comments.size()!=0) {
			//add emotes after hl
			for(int i =0;i<comments.size();i++) {
				String temp = comments.get(i);
				if(hlFlag) {
				comments.set(i, temp.concat(" BatChest"));
				}
				
			}
			
			for(String comment : comments) {
				commentSet.add(comment);
			}
			
			comments.clear();
			if(hlFlag) {
				comments.addAll(commentSet);
				
			}else {
				comments.add((String) commentSet.toArray()[0]);
			}
			
		}
		
		
		
		
	}
	public void speak(String text,boolean hlFlag) {
		Thread thread = new Thread(() -> {
			try {
				//1f 2f
				TTSBridge tts = new TTSBridge();
				tts.voice_name = "en-GB-Wavenet-B";
				tts.language_code="en-GB";
				tts.rate=1f; //1.2
				tts.pitch=1f; //3
				if(hlFlag) {
					tts.rate=1.2f;
					tts.pitch=3f;
					tts.gain=10f;
				}
				
				
				
				
				
				// 说话
				tts.speak(text);
				System.out.println(text);
				//System.out.println("Successfully got back synthesizer data,pitch:"+pitch+"speed:"+speed);
				
			} catch (Exception e) {
				
				e.printStackTrace(); //Print the exception ( we want to know , not hide below our finger , like many developers do...)
				
			}
		});
		
		//We don't want the application to terminate before this Thread terminates
		thread.setDaemon(false);
		
		//Start the Thread
		thread.start();
	}
	
	
	
}
