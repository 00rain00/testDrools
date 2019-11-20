package comment;
import struct.FrameData;
import struct.CharacterData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;

import comment.Highlight;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import comment.Message;

import com.cavariux.twitchirc.Chat.Channel;
import com.cavariux.twitchirc.Core.TwitchBot;
import com.darkprograms.speech.synthesiser.SynthesiserV2;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
public class CommentService {
	static int oriHp = 400;
	static org.slf4j.Logger logger = LoggerFactory.getLogger(CommentService.class);
	 static KieServices ks;
	 static  KieContainer kContainer;
	 static KieSession kSession;
	 
	public static Highlight setHighlight(FrameData fd) {
		
		Highlight hl = new Highlight();
		try {
		if(fd.currentFrameNumber>=1) {
			
			//p1
			hl.setFrameNumber(fd.getFramesNumber());
			CharacterData p1 = fd.getCharacter(true);
			CharacterData p2 = fd.getCharacter(false);
			hl.setP1Damage(oriHp-p1.getHp());
			hl.setP2Damage(oriHp-p2.getHp());
			hl.setP1Hits(p1.getHitCount());
			hl.setP2Hits(p2.getHitCount());
			hl.setP1Energy(p1.getEnergy()/300.0);
			hl.setP2Energy(p2.getEnergy()/300.0);
			double d1 = 1.0 - ((double)Math.abs(480.0 - p1.getCenterX())/480.0); //0->left corner 1-> right corner
			double d2 = 1.0 - ((double)Math.abs(480.0 - p2.getCenterX())/480.0);
			hl.setP1Position(d1);
			hl.setP2Position(d2);
			hl.setDifDis((double)Math.abs((Math.abs(p1.getCenterX()-p2.getCenterX())-41.0))/879.0);//normalize to 0-1
			//p2
			hl.setP1giveEnergy(p1.getAttack().getGiveEnergy());
			hl.setP2giveEnergy(p2.getAttack().getGiveEnergy());
			hl.setP1guardAddEnergy(p1.getAttack().getGuardAddEnergy());
			hl.setP2guardAddEnergy(p2.getAttack().getGuardAddEnergy());
			hl.setP1hitAddEnergy(p1.getAttack().getHitAddEnergy());
			hl.setP2hitAddEnergy(p2.getAttack().getHitAddEnergy());
			hl.setP1startAddEnergy(p1.getAttack().getStartAddEnergy());
			hl.setP2startAddEnergy(p2.getAttack().getStartAddEnergy());
			
			
			
		}else {
			logger.info(" current frame <1");
		}
		}catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return hl;
	}
	public static double calculateHlScore(ArrayList<Highlight> hlList) {
		double result =0;
		if(hlList.size()==0||hlList==null) {
			return 0;
		}
		try {
			int size = hlList.size();
			 if(size<=1200) {
				 return 1;
			 }else if(size<=2400) {
				 return 2;
			 }else {
				 return 3;
			 }
			
			
			

			}catch(Exception e) {
				logger.error(e.getMessage(), e);
			}
		
		
		
		
		return result;
	}
	
	
	
	
	
	
	public static Message generateComment(FrameData fd,int frequence,KieSession ks,Message msg) {
		try {
		
		CharacterData p1 =  fd.getCharacter(true);
		  CharacterData p2 =  fd.getCharacter(false);
		int currentFrame = fd.currentFrameNumber;
		if(currentFrame>=1) {
			if(currentFrame%frequence ==0) {
				  ks.insert(p1);
				   ks.insert(p2);
				   ks.insert(msg);
				  
				   ks.fireAllRules();
			  		
				
			}else {
				//logger.info("ignor frame "+currentFrame);
			}
			
			
		}
		
		}catch(Exception e) {
			logger.error(e.getMessage(),e);
		}
		return msg;
	}
	public static void text2Speech(Message msg, SynthesiserV2 synthesizer,double speed,double pitch,double hlScore) {
		ArrayList<String> comments  = msg.getComments();
		String text = "";
		for (String com : comments) {
			text = text.concat(com).concat(".");
		}
		logger.info(text);
		logger.info("comment size:"+comments.size());
		String a = text;
		switch((int)hlScore) {
		case 1:
			synthesizer.setPitch(1.0);
			synthesizer.setSpeed(1.0);
			break;
		case 2:
			synthesizer.setPitch(1.3);
			synthesizer.setSpeed(1.1);
			break;
		case 3:
			synthesizer.setPitch(1.5);
			synthesizer.setSpeed(1.2);
			break;
		}
		if(msg.combo) {
			synthesizer.setPitch(1.3);
			synthesizer.setSpeed(1.1);
		}
		
		//Create a new Thread because JLayer is running on the current Thread and will make the application to lag
		Thread thread = new Thread(() -> {
			try {
				
				//double pitch =synthesizer.getPitch();
				//double speed =synthesizer.getSpeed();
				//Create a JLayer instance
				AdvancedPlayer player = new AdvancedPlayer(synthesizer.getMP3Data(comments));
				player.play();
				
				System.out.println("Successfully got back synthesizer data,pitch:"+pitch+"speed:"+speed);
				
			} catch (IOException | JavaLayerException e) {
				
				e.printStackTrace(); //Print the exception ( we want to know , not hide below our finger , like many developers do...)
				
			}
		});
		
		//We don't want the application to terminate before this Thread terminates
		thread.setDaemon(false);
		
		//Start the Thread
		thread.start();
	}
	
	public static int sendComment(Message msg,TwitchBot bot, Channel ch) {
		if(msg==null||bot==null||ch==null) {
			return 0;
		}
		ArrayList<String> comments = msg.getComments();
		int totalSentComment = comments.size();
		try {
		for (String com :comments) {
			bot.sendMessage(com, ch);
		}
		
		}catch(Exception e) {
			logger.error(e.getMessage(),e);
		}
		return totalSentComment;
	}
	
}
