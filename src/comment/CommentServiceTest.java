package comment;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import struct.FrameData;
import org.junit.Before;
import org.junit.Test;

import com.cavariux.twitchirc.Chat.Channel;

import fighting.Fighting;
import struct.CharacterData;
import fighting.Character;
import comment.Fightbot;
import comment.CommentBot;
//import com.darkprograms.speech.synthesiser.SynthesiserV2;
public class CommentServiceTest {

	@Before
	public void setUp() throws Exception {
	
	}

	@Test
	public void testSetHighlight() {
		try {
		FrameData fd = new FrameData();
		
		CharacterData cd2 = new CharacterData();
		
		CharacterData cd1 = new CharacterData(); 
		
		
		cd1.setLeft(220);
		cd2.setLeft(220);
		cd1.setHp(100);
		fd.setCurrentFrameNumber(1);
		fd.setCharacterData(cd1,cd2);
		Highlight hl = CommentService.setHighlight(fd);
		assertEquals("not same",hl.getP1Damage(),300,0);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testTestMatlab() {
//		try {
//		CommentService.testMatlab();
//		
//		}catch (Exception e){
//			e.printStackTrace();
//		}
	}
	@Test
	public void testTTS() {
		try {
			ArrayList<String>comments = new ArrayList<String>();
			comments.add("Exactly huge attack Over there sending ZEN to the asmosphere!");
			boolean hlFlag = true;
			CommentService.GTTS(comments, hlFlag);
			Thread.sleep(10000);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	@Test
	public void testSendComment() {
		try {
		 Fightbot fbot;
		CommentBot cb;
	  Channel channel;
			String cName = "#hunteer_999";
			System.out.println("initial bot connections :"+cName);
		 fbot = new Fightbot();
			cb = new CommentBot();
			fbot.connect();
			cb.connect();
			 channel = fbot.joinChannel(cName);
			 cb.joinChannel(cName);
			Set<String>comments = new HashSet<String>();
			comments.add("Exactly huge attack Over there sending ZEN to the asmosphere!");
		//	CommentService.sendComment(comments, fbot, channel);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testAsyn() {
//		CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
//		    // Simulate a long-running Job   
//		    try {
//		        TimeUnit.SECONDS.sleep(1);
//		    } catch (InterruptedException e) {
//		        throw new IllegalStateException(e);
//		    }
//		    System.out.println("I'll run in a separate thread than the main thread.");
//		});
//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		future.runAsync(() -> {
//		    // Simulate a long-running Job   
//		    try {
//		        TimeUnit.SECONDS.sleep(1);
//		    } catch (InterruptedException e) {
//		        throw new IllegalStateException(e);
//		    }
//		    System.out.println("I'll run in a separate thread than the main thread.2");
//		});
//		
//		
//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		
	}
}
