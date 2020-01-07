package comment;
import struct.FrameData;
import struct.CharacterData;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;

import comment.Highlight;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

import comment.Message;
import ice_agent.TTSBridge;

import com.cavariux.twitchirc.Chat.Channel;
import com.cavariux.twitchirc.Core.TwitchBot;
//import com.darkprograms.speech.synthesiser.SynthesiserV2;
//import javazoom.jl.decoder.JavaLayerException;
//import javazoom.jl.player.advanced.AdvancedPlayer;
import com.mathworks.engine.*;
import java.util.concurrent.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class CommentService {
	static int oriHp = 400;
	static org.slf4j.Logger logger = LoggerFactory.getLogger(CommentService.class);
	 static KieServices ks;
	 static  KieContainer kContainer;
	 static KieSession kSession;
	static Set<String>commentSet;
	static Map<String,String>skillMap ;
			static {
				skillMap=new HashMap<>();
				skillMap.put("STAND_D_DB_BA", "Flying crop");
//				skillMap.put("BACK_STEP", "Back step");
//				skillMap.put("FORWARD_WALK", "Step forward");
//				skillMap.put("DASH", "Lean forward");
				skillMap.put("STAND_GUARD", "Guard");
				skillMap.put("CROUCH_GUARD", "Guard");
				skillMap.put("THROW_A", "Throw");
				skillMap.put("THROW_B", "Great Throw");
				skillMap.put("STAND_A", "Punch");
				skillMap.put("STAND_B", "Kick");
				skillMap.put("CROUCH_A", "Low Punch");
				skillMap.put("CROUCH_B", "Low Kick");
				skillMap.put("STAND_FA", "Heavy Punch");
				skillMap.put("STAND_FB", "Heavy Kick");
				skillMap.put("CROUCH_FA", "Low Heavy Punch");
				skillMap.put("CROUCH_FB", "Low Heavy Kick");
				skillMap.put("STAND_D_DF_FA", "Hadouken");
				skillMap.put("STAND_D_DF_FB", "Super Hadouken");	
				skillMap.put("STAND_F_D_DFA", "Uppercut");
				skillMap.put("STAND_F_D_DFB", "Super Uppercut");
				skillMap.put("STAND_D_DB_BB", "Slide Kick");
				skillMap.put("STAND_D_DF_FC", "Ultimate Hadouken");	
			}
	
			static String[]air = {
					"Important huge hit over there and sending he to the atmosphere.",
					"Exactly important huge ! Over there sending @ into the atmosphere"
					};
					static String[]normal = {
							" playing very re-actively, and waiting to see what will do.",
							"A miracle action.",
							"@ is ridiculous.",
							"The genius @ surviving.",
							"punishing properly.",
							"@ not sure what to do, @ is capitalizing it.",
							"see what @ is doing .",
							"The defense is a little shaky here.",
							"@ packing a few try but not able to catch the opponent.",
							"He just act so flexible.",
							
					};
					static String[]end= {
							"The big counter hit confirm this round.",
							"It gives the moment to punch and @ takes a turn.",
							"@ is going to get in with !  and just like that finish the game.",
							"@ takes what he can get and go for the reset at the end.",
							"A ! takes the round here.",
							"@ able to  !  enough to take the round.",
							"You see ,that was a checkmate scenario.",
							"1,2,3 strike. you are out.",
							"Nice predict of the hit and get a counter hit.",
							"Wow！ what kind of mad men is he",
							"what a beautiful ! to beat up the @ .",
							"Incredible round for @ ."
					};
					static String[]distance= {
							
							"@ backs up in the corner instead of choosing go forward.",
							
							"@ decides to spend some !  to maintain that distance.",
							"@ try to keep the space .",
							"a lot of back up from @.",
							
							
							
							"@ can have a little bit of break now.",
							"@ try to switch up a strategy here.",
							"@ tries to slow down and find the range."
					};
					static String[]corner= {
							"They have no more space to walk backward."	,
							"really get in there, nicely force into the corner.",
							
							"@ caging the opponent in that following a branch of !.",
							
							"@ is in trouble here.",
							"@ will stick out the corner.",
							"This corner pressure is very cool.",
							
							"@ tucked the opponent very quick",
							"What a ! under @, he manage to escape the corner.",
							"So important to trap your opponent into the corner.",
							
							
							
					};
					static String[]ult= {
							" triggered the ! to do another critical arts.",
							"What a !, huge damage after that and give @ the moment to  a knock out.",
							"@ is going to get in with !  and just like that finish the game.",
							"@ ! ,enough to take the round.",
							"Here is the ! into the activation .",
							"look at the pressure now.",
							"such a good ! .",
							"one big hit can lead to a lot of damage.",
							" @ gonna to get a !.",
							"It is the damage as @ gets the hits.",
							"big damage @ optimal there.",
							"huge damage already lost.",
							"That is @, playing out of his mind.",
							"One hit can lead directly into critical act from @ .",
							"A ton of damage.",
							"Any time  get hit,  lose all of that amount.",
							" never seen that before",
							"The big counter hit connect the ! ."
					};
					static String[] hp = {
						"It is pressure time now.",
						"@ got to do something right now.",
						"@ need to be very careful.",
						"God, @  already down half of the life.",
					"@ is losing the momentum .",
					"@ never stop.",
					"Drain a lot of life.",
					"He finds of hit, very dangerous for @ .",
					"Once @ get in, it will overwhelm you."
					};
					static String[] jump= {
							
							"Here we go.",
							"@ trying to move in the air. ",
							"@ plays very quick.",
							"! come down too fast, the regular ! cannot be hit.",
							"@ goes for the air because @ wants to jump in ."
					};
					static String[]heavy= {
							"huge damage output from @.",
							"! works so well it gives @ the advantage ",
							"@ stand out followed ! .",
							"The offense is overwhelming.",
							
							"Great start of the @ with the ! .",
							"@ could get more damage.",
							"@ is controlling the around ",
							"@ get in there with a ! right to the face.",
							"! is really hard to answer.",
							"It is going to be  a lot of ! .",
							
							" the life deficit so high .",
							"@ stand up and got a  ! .",
							"@ rolled over and make the lead of big damage and pressure.",
							"Immediately active the ! ."
					};
					static String[]combo= {
							"Very nice a ! combo.",
							"@ add a combo , a ton of damage here.",
							
							
							"@ hit the opponent and going to give a combo.",
							"a big combo and follow up ! .",
							
							"Wow！ , a make up of ! and ! right there.",
							"  @ use !  to get through and combo into the opponent.",
							
					};
					static String[]block= {
							" @ blocking but no punish.",
							"That is a very popular defense. ",
							"@ is patient defense now.",
							"Incredible awareness and reaction on that one.",
							"Immediately active the ! ."
					};
					
					
	static ArrayList<String>dmch = readComments("C:\\Users\\irvine\\git\\testDrools\\src\\comment\\dm_ch.txt");
	static ArrayList<String>wannengch=readComments("C:\\Users\\irvine\\git\\testDrools\\src\\comment\\normal_ch.txt");
	static ArrayList<String>heavych=readComments("C:\\Users\\irvine\\git\\testDrools\\src\\comment\\heavy_ch.txt");
	static ArrayList<String>comboch=readComments("C:\\Users\\irvine\\git\\testDrools\\src\\comment\\combo_ch.txt");
	static ArrayList<String>hpch=readComments("C:\\Users\\irvine\\git\\testDrools\\src\\comment\\hp_ch.txt");
	static ArrayList<String>ultch=readComments("C:\\Users\\irvine\\git\\testDrools\\src\\comment\\ult_ch.txt");
	static ArrayList<String>cornerch=readComments("C:\\Users\\irvine\\git\\testDrools\\src\\comment\\corner_ch.txt");
	static ArrayList<String>distancech=readComments("C:\\Users\\irvine\\git\\testDrools\\src\\comment\\distance_ch.txt");
	static ArrayList<String>endch=readComments("C:\\Users\\irvine\\git\\testDrools\\src\\comment\\end_ch.txt");
	
	
	public static Highlight setHighlight(FrameData fd) {
		
		Highlight hl = new Highlight();
		try {
		if(fd.currentFrameNumber>=1) {
			
			
			hl.setFrameNumber(fd.getFramesNumber());
			CharacterData p1 = fd.getCharacter(true);
			CharacterData p2 = fd.getCharacter(false);
			hl.setP1Damage(oriHp-p1.getHp());
			hl.setP2Damage(oriHp-p2.getHp());
			hl.setHpdif(Math.abs(p1.getHp()-p2.getHp()));
			hl.setP1Hits(p1.getHitCount());
			hl.setP2Hits(p2.getHitCount());
			hl.setP1Energy(p1.getEnergy()/300.0);
			hl.setP2Energy(p2.getEnergy()/300.0);
			double d1 = 1.0 - ((double)Math.abs(480.0 - p1.getCenterX())/480.0); //0->left corner 1-> right corner
			double d2 = 1.0 - ((double)Math.abs(480.0 - p2.getCenterX())/480.0);
			hl.setP1Position(d1);
			hl.setP2Position(d2);
			hl.setDifDis((double)Math.abs((Math.abs(p1.getCenterX()-p2.getCenterX())-41.0))/879.0);//normalize to 0-1
			
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
	public static double[][] prepareHLData(ArrayList<Highlight> hlList) {
		/*
		 * [][]
		 * hpdif disdif 1hitEn 1guardEn 1GiveEn 1hitCount 2hitEn 2guardEn 2GiveEn  2hitcount
		*/
		LinkedHashSet<Highlight>hlSet = new LinkedHashSet<Highlight>();
		for (Highlight hl :hlList) {
			hlSet.add(hl);
		}
		
		if(hlList.size()==0||hlList==null) {
			return null;
		}
		
		int size = hlSet.size();
		double[][] Mhl=new double [size][10];
		try {
			Iterator<Highlight> itr = hlSet.iterator();
			int i =0;
		while(itr.hasNext()) {
				Highlight hl = itr.next();
				
				for (int j =0;j<10;j++) {
					switch (j) {
					case 0:
						Mhl[i][j]=hl.gethpdif();
						break;
					case 1:
						Mhl[i][j]=hl.getDifDis();
						break;
					case 2:
						Mhl[i][j]=hl.getP1hitAddEnergy();
						break;
					case 3:
						Mhl[i][j]=hl.getP1guardAddEnergy();
						break;
					case 4:
						Mhl[i][j]=hl.getP1giveEnergy();
						break;
					case 5:
						Mhl[i][j]=hl.getP1Hits();
						break;
					case 6:
						Mhl[i][j]=hl.getP2hitAddEnergy();
						break;
					case 7:
						Mhl[i][j]=hl.getP2guardAddEnergy();
						break;
					case 8:
						Mhl[i][j]=hl.getP2giveEnergy();
						break;
					case 9:
						Mhl[i][j]=hl.getP2Hits();
						break;
					}
						
				}
				i = i+1;
		}
			
				}catch(Exception e) {
				logger.error(e.getMessage(), e);
			}
		
		
		
		return Mhl;
		
	}
	
	public static Message generateComment(FrameData fd,StatelessKieSession ks,boolean hlFlag) {
		Message msg = new Message();
		try {
			CharacterData p1 =  fd.getCharacter(true);
			CharacterData p2 =  fd.getCharacter(false);
			System.out.println("p1:"+fd.getFramesNumber()+"ACTION:"+p1.getAction().toString());
			ks.execute(Arrays.asList(new Object[] {p1,p2,msg}));
			
			double difdis=(double)Math.abs((Math.abs(p1.getCenterX()-p2.getCenterX())-41.0))/879.0;//normalize to 0-1
			double d1 = 1.0 - ((double)Math.abs(480.0 - p1.getCenterX())/480.0); //0->left corner 1-> right corner
			double d2 = 1.0 - ((double)Math.abs(480.0 - p2.getCenterX())/480.0);
			
			Random rand =new Random();
			String action = getActionRealName(msg.getAction());
			String playerName = rand.nextBoolean()?"P1":"P2";
			
			
			
			
			
			//jifei
//			if((msg.getOffence()==1||msg.getOffence()==0)&msg.getState()=="air") {
//				String text =air[getRandomNumber(air.length)];
//				msg.addComments(matchTemplate(text,action,playerName));
//				
//			}
//			normal
//			if(msg.getOffence()==-1) {
//				String text =normal[getRandomNumber(normal.length)];
//				String a =matchTemplate(text,action,playerName);
//				msg.addComments(a);
//			}
//			distance
			if(difdis>=0.5) {
				String text =distancech.get(getRandomNumber(distancech.size()));
				String a =matchTemplate(text,action,playerName);
				msg.addComments(a);
			}
			//corner
			if(difdis<=0.4&&(d1>=0.9||d1<=0.1)&(d2>=0.9||d2<=0.1)) {
				String text =cornerch.get(getRandomNumber(cornerch.size()));
				String a =matchTemplate(text,action,playerName);
				msg.addComments(a);
			}
			//ult
			if(msg.isUlt()) {
				String text =ultch.get(getRandomNumber(cornerch.size()));
				String a =matchTemplate(text,action,playerName);
				msg.addComments(a);
			}
			//jump
//			if(msg.isJump()) {
//				String text =jump[getRandomNumber(jump.length)];
//				String a =matchTemplate(text,action,playerName);
//				msg.addComments(a);
//			}
			//hpDangerous
			if(msg.isHp()) {
				String text =hpch.get(getRandomNumber(hpch.size()));
				String a =matchTemplate(text,action,playerName);
				msg.addComments(a);
			}
			//heavy
			if(msg.isHeavy()) {
				String text =heavych.get(getRandomNumber(heavych.size()));
				String a =matchTemplate(text,action,playerName);
				msg.addComments(a);
			}
			//end
			if(msg.isEnd()) {
				String text =endch.get(getRandomNumber(endch.size()));
				String a =matchTemplate(text,action,playerName);
				msg.addComments(a);
			}
			//conbo
			if(msg.isCombo()) {
				String text =comboch.get(getRandomNumber(comboch.size()));
				String a =matchTemplate(text,action,playerName);
				msg.addComments(a);
			}
			//block
//			if(msg.isBlock()) {
//				String text =block[getRandomNumber(block.length)];
//				String a =matchTemplate(text,action,playerName);
//				msg.addComments(a);
//			}
			String text =dmch.get(getRandomNumber(dmch.size()));
			String a =matchTemplate(text,action,playerName);
			msg.addComments(a);
			

		
			   msg.deleteRepeatComments(hlFlag);
	
			
		
		
			
		}catch(Exception e) {
			logger.error(e.getMessage(),e);
		}
		return msg;
	}
	
	public static int sendComment(ArrayList<String> comments,TwitchBot bot, Channel ch) {
		  try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int totalSentComment = comments.size();
		try {
		for (String com :comments) {
			System.out.println(com);
			bot.sendMessage(com, ch);
		}
		
		}catch(Exception e) {
			logger.error(e.getMessage(),e);
		}
		return totalSentComment;
	}
	
	public static double evaluateHl(MatlabEngine eng,double[][]matrix)throws Exception {
		double hlScore=0;
		eng.eval("cd C:\\Users\\irvine\\git\\hl");
		//printMatrix(matrix);
			
		hlScore=eng.feval("testJava",matrix);
		
        return hlScore;
        
	}
	public static void GTTS(ArrayList<String> comments,boolean hlFlag) {
		
		System.out.println("GTTS fired");
		Thread thread = new Thread(() -> {
			try {
				//1f 2f
				TTSBridge tts = new TTSBridge();
				tts.voice_name = "en-GB-Wavenet-B";
				tts.language_code="en-GB";
				tts.rate=1f; //1.2
				tts.pitch=1f; //3
				if(hlFlag) {
					
					tts.pitch=3f;
					tts.gain=10f;
				}
				// 
				int size = comments.size();
				if (size>1&&size<=3) {
					tts.rate = 1.3f;
				}
				if(size>3) {
					tts.rate = 1.6f;
				}
				
					
					
				
			//	String text = "";
				for (String com : comments) {
					//text = text.concat(com).concat(".");
					tts.speak(com);
					System.out.println(com);
				}
				
				
				// 说话
			//	tts.speak(text);
			//	System.out.println(text);
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
	public static void printMatrix(double[][]m) {
		
	        for(double[] row : m) {
	        	 for (double i : row) {
	                 System.out.print(i);
	                 System.out.print("\t");
	             }
	             
	        }
	}
	public static int getRandomNumber(int range) {
		Random random = new Random();
		return random.nextInt(range);
	}
	public static String getActionRealName(String skillCode) {
		return skillMap.getOrDefault(skillCode, "attack");
	}
	
	public static String matchTemplate(String temp,String action, String playerName) {
		
		
		String comment =  temp.replace("@", playerName).replace("$", action);
		return comment;
	}
	
	public static Message generateCommentByMCTS() {
		return new Message();
	}
	
	public static boolean evaluateHlByDistanceCue(ArrayList<Highlight> list) {
		boolean flag = false;
		
		try {
			double d1 = list.get(list.size()-1).getDifDis();
			double d2 = list.get(list.size()-60).getDifDis();
			double difDis =Math.abs(d1-d2);
//			System.out.println(difDis);
//			System.out.println(d1);
//			System.out.println(d2);
			if(difDis>=0.2) {
		    	flag = true;
		    }
		
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return flag;
	}
	
	public static boolean evaluateHlByRandomSeed(FrameData fd) {
		Random rand = new Random();
		boolean flag = (rand.nextDouble()<=0.25);
		return flag;
		
	}
	public static ArrayList<String> readComments(String address) {
		
		ArrayList<String>coms=new ArrayList<String>();
		
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(address),"gbk"));
			
			
			String line = null;
			while((line = br.readLine()) != null) {
				//System.out.println(line);
				
				coms.add(line);}
			br.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return coms;
	}
	
	
}