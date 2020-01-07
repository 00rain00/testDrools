package comment;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import struct.FrameData;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;

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
			 fbot.joinChannel(cName);
			 channel =  cb.joinChannel(cName);
			ArrayList<String>comments = new ArrayList<String>();
			comments.add("BatChest");
			CompletableFuture<Integer> future = CompletableFuture.supplyAsync(new Supplier<Integer>() {
				  @Override
				    public Integer get() {
					int send =  CommentService.sendComment(comments, cb, channel);
				        return send;
				    }
				});
		
		
		
		int result = future.get();
		result+=1;
		System.out.println(result);
		Thread.sleep(10000);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testAsyn() {
		// Run a task specified by a Supplier object asynchronously
		CompletableFuture<String> future = CompletableFuture.supplyAsync(new Supplier<String>() {
		    @Override
		    public String get() {
		        try {
		        	
		            TimeUnit.SECONDS.sleep(5);
		        } catch (InterruptedException e) {
		            throw new IllegalStateException(e);
		        }
		        return "Result of the asynchronous computation";
		    }
		});

		// Block and get the result of the Future
		String result;
		try {
			result = future.get();
			System.out.println(result);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	@Test
	public void testDrools() {
		 KieServices ks;
		  KieContainer kContainer;
	 StatelessKieSession kSession;
	 ks = KieServices.Factory.get();
	    kContainer = ks.getKieClasspathContainer();
	    kSession = kContainer.newStatelessKieSession("ksession-rules");
	    CharacterData p1 = new CharacterData();
	    CharacterData p2 = new CharacterData();
	    kSession.execute(Arrays.asList(new Object[] {p1,p2}));
	}
	
	@Test
	public void readcomments() {
		
	ArrayList <String>coms = new ArrayList<String>();
	//coms =CommentService.readComments();
		
	}
	
}
