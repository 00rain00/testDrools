package gamescene;

import static org.lwjgl.glfw.GLFW.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

import enumerate.GameSceneName;
import fighting.Fighting;
import informationcontainer.RoundResult;
import input.KeyData;
import input.Keyboard;
import loader.ResourceLoader;
import manager.GraphicManager;
import manager.InputManager;
import manager.SoundManager;
import py4j.Py4JException;
import setting.FlagSetting;
import setting.GameSetting;
import struct.CharacterData;
import struct.FrameData;
import struct.GameData;
import struct.ScreenData;
import util.DebugActionData;
import util.LogWriter;
import util.ResourceDrawer;
/////////////////////////////
import comment.Message;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import comment.Fightbot;
import comment.CommentBot;
import comment.Highlight;
import comment.CommentService;
import com.cavariux.twitchirc.Chat.*;
//import com.darkprograms.speech.synthesiser.SynthesiserV2;

import com.mathworks.engine.*;
import java.math.*;
/**
 * 対戦中のシーンを扱うクラス．
 */
public class Play extends GameScene {

	/**
	 * 対戦処理を行うクラスのインスタンス．
	 */
	private Fighting fighting;

	/**
	 * 現在のフレーム．
	 */
	private int nowFrame;

	/**
	 * 各ラウンド前に行う初期化処理内における経過フレーム数．
	 */
	private int elapsedBreakTime;

	/**
	 * 現在のラウンド．
	 */
	private int currentRound;

	/**
	 * 各ラウンドの開始時かどうかを表すフラグ．
	 */
	private boolean roundStartFlag;

	/**
	 * 対戦処理後のキャラクターデータなどのゲーム情報を格納したフレームデータ．
	 */
	private FrameData frameData;

	/**
	 * 対戦処理後のゲーム画面の情報．
	 */
	private ScreenData screenData;

	/**
	 * 対戦処理に用いるP1, P2の入力情報．
	 */
	private KeyData keyData;

	/**
	 * 各ラウンド終了時のP1, P2の残り体力, 経過時間を格納するリスト．
	 */
	private ArrayList<RoundResult> roundResults;

	/**
	 * Replayファイルに出力するための出力ストリーム．
	 */
	private DataOutputStream dos;

	/**
	 * 現在の年月日, 時刻を表す文字列．
	 */
	private String timeInfo;

	/**
	 * クラスコンストラクタ．
	 */
	
	 public KieServices ks;
	 public  KieContainer kContainer;
	 public KieSession kSession;
	 public Message msg;
	 static int commentLimit;
	 public Fightbot fbot;
	 public CommentBot cb;
	public  Channel channel;
	public String j;
	//public static SynthesiserV2 synthesizer = new SynthesiserV2("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw");
	public ArrayList<Highlight> hlList;
	public  ArrayList<Double>hlScore;
	public MatlabEngine eng;
	 static int frequency=100;
	public Play() {
		// 以下4行の処理はgamesceneパッケージ内クラスのコンストラクタには必ず含める
		this.gameSceneName = GameSceneName.PLAY;
		this.isGameEndFlag = false;
		this.isTransitionFlag = false;
		this.nextGameScene = null;
		//////////////////////////////////////

	}

	@Override
	public void initialize() {
		InputManager.getInstance().setSceneName(GameSceneName.PLAY);
		hlList = new ArrayList<Highlight>();
		hlScore=new ArrayList<Double>();
		this.fighting = new Fighting();
		this.fighting.initialize();

		this.nowFrame = 0;
		this.elapsedBreakTime = 0;
		this.currentRound = 1;
		this.roundStartFlag = true;

		this.frameData = new FrameData();
		this.screenData = new ScreenData();
		this.keyData = new KeyData();
		this.roundResults = new ArrayList<RoundResult>();

		this.timeInfo = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd-HH.mm.ss", Locale.ENGLISH));
		
		
		if (!FlagSetting.trainingModeFlag) {
			openReplayFile();
		}

		if (FlagSetting.debugActionFlag) {
			DebugActionData.getInstance().initialize();
		}
		if (FlagSetting.jsonFlag) {
			System.out.println("initialize json flag");
			String jsonName = LogWriter.getInstance().createOutputFileName("./log/replay/", this.timeInfo);
			LogWriter.getInstance().initJson(jsonName + ".json");
			
		}

		GameData gameData = new GameData(this.fighting.getCharacters());

		try {
			InputManager.getInstance().createAIcontroller();
			InputManager.getInstance().startAI(gameData);
		}catch (Py4JException e){
			Logger.getAnonymousLogger().log(Level.SEVERE, "Fail to Initialize AI");
			Launcher lunch = new Launcher(GameSceneName.PLAY);
			this.setTransitionFlag(true);
			this.setNextGameScene(lunch);
		}
		if (FlagSetting.enableWindow && !FlagSetting.muteFlag) {
			//SoundManager.getInstance().play(SoundManager.getInstance().getBackGroundMusic());
		}
		if (FlagSetting.enableComment) {
			this.ks = KieServices.Factory.get();
		    this.kContainer = ks.getKieClasspathContainer();
		    this.kSession = kContainer.newKieSession("ksession-rules");
			
			
		}
		if(FlagSetting.enableTwitchChat) {
			try {
			String cName = "#hunteer_999";
//			System.out.println("initial bot connections :"+cName);
			this.fbot = new Fightbot();
			this.cb = new CommentBot();
			fbot.connect();
			cb.connect();
			channel= fbot.joinChannel(cName);
			 cb.joinChannel(cName);
			
		} catch(Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "Cannot connect to twitch");
		}
			
		}
		
		

	}
	

	@Override
	public void update() {
		if (this.currentRound <= GameSetting.ROUND_MAX) {
			// ラウンド開始時に初期化
			if (this.roundStartFlag) {
				initRound();

			} else if (this.elapsedBreakTime < GameSetting.BREAKTIME_FRAME_NUMBER) {
				// break time
				processingBreakTime();
				this.elapsedBreakTime++;

			} else {
				// processing
				processingGame();
				this.nowFrame++;
			}

		} else {
			Logger.getAnonymousLogger().log(Level.INFO, "Game over");
			if (FlagSetting.enableWindow && !FlagSetting.muteFlag) {
				// BGMを止める
				SoundManager.getInstance().stop(SoundManager.getInstance().getBackGroundMusic());
			}

			Result result = new Result(this.roundResults, this.timeInfo);
			this.setTransitionFlag(true);
			this.setNextGameScene(result);
		}

		if (Keyboard.getKeyDown(GLFW_KEY_ESCAPE)) {
			if (FlagSetting.enableWindow && !FlagSetting.muteFlag) {
				// BGMを止める
				SoundManager.getInstance().stop(SoundManager.getInstance().getBackGroundMusic());
			}

			HomeMenu homeMenu = new HomeMenu();
			this.setTransitionFlag(true);
			this.setNextGameScene(homeMenu);
		}

	}

	/**
	 * 各ラウンド開始時に, 対戦情報や現在のフレームなどの初期化を行う．
	 */
	private void initRound() {
		this.fighting.initRound();
		this.nowFrame = 0;
		this.roundStartFlag = false;
		this.elapsedBreakTime = 0;
		this.keyData = new KeyData();
		commentLimit = 50;
		InputManager.getInstance().clear();
		 this.hlList = new ArrayList<Highlight>();
	      msg = new Message();
	      try {
			eng=MatlabEngine.startMatlab();
		} catch (EngineException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 各ラウンド開始時における, インターバル処理を行う．
	 */
	private void processingBreakTime() {
		// ダミーフレームをAIにセット
		InputManager.getInstance().setFrameData(new FrameData(), new ScreenData());
		
		if (FlagSetting.enableWindow) {
			GraphicManager.getInstance().drawQuad(0, 0, GameSetting.STAGE_WIDTH, GameSetting.STAGE_HEIGHT, 0, 0, 0, 0);
			GraphicManager.getInstance().drawString("Waiting for Round Start", 350, 200);
		}
		this.fighting.initRound();
	}

	/**
	 * 対戦処理を行う.<br>
	 *
	 * 1. P1, P2の入力を受け取る.<br>
	 * 2. 対戦処理を行う.<br>
	 * 3. 対戦後のFrameDataを取得する.<br>
	 * 4. リプレイファイルにログを出力する.<br>
	 * 5. ゲーム画面を描画する.<br>
	 * 6. 対戦後の画面情報(ScreenData)を取得する．<br>
	 * 7. AIにFrameData及びScreenDataを渡す．<br>
	 * 8. ラウンドが終了しているか判定する.<br>
	 */
	private void processingGame() {
		this.keyData = new KeyData(InputManager.getInstance().getKeyData());
		this.fighting.processingFight(this.nowFrame, this.keyData);
		this.frameData = this.fighting.createFrameData(this.nowFrame, this.currentRound);
		int fn=this.frameData.currentFrameNumber;
		 Highlight hl = CommentService.setHighlight(this.frameData);  
		 hlList.add(hl);
		 boolean hlFlag= false;
		 CompletableFuture<Void> future;
		// Set<String>comments = new HashSet<String>();
		 try {
				  if(fn%frequency==0&&fn>=10) {
					  
						if(FlagSetting.enableMatlab) {
							 double hls=0;
						hls = CommentService.evaluateHl(eng,CommentService.prepareHLData(hlList));
						//System.out.println(hls);
						 hlScore.add(hls);
						 if(hlScore.size()>=3) {
						   if(Math.abs(hlScore.get(hlScore.size()-1)-hlScore.get(hlScore.size()-2))>=0.1) {
							  hlFlag=true;
						   }
						   if(hls>0.55) {
							   hlFlag=true;
						   }
						 }
						  
						}
					  
					  
					  if(FlagSetting.enableComment) {
						   
						  this.msg = CommentService.generateComment(this.frameData, this.kSession,hlFlag);
					   }
					  
					  if(FlagSetting.enableTwitchChat) {
						   if(commentLimit>0) {
//							   System.out.println("comment limit :"+commentLimit);
							   double rand= Math.random();
							   int sent=0;
							   
									   if(rand>0.5) {
									sent =CommentService.sendComment(this.msg.getComments(), fbot, channel);
									   }else {
										   sent =CommentService.sendComment(this.msg.getComments(),cb, channel);
									   }
						  		commentLimit -=sent;
						   }
//						   //  50 comments per 30s 
						   if(this.frameData.currentFrameNumber>=1700) {
							   commentLimit =50;
						   }
						   
						   
					   }
					   if(FlagSetting.enableTTS) {
						  
							  
								CommentService.GTTS(this.msg.getComments(), hlFlag);
								
						}
					  
				  }
			  
			  
			  
			 
//			   if( fn%180==0) {
//				  
//				  for(String com : comments) {
//					  System.out.println(com);
//				  }
//				   this.msg.emptyComments();
//				   System.out.println("empty msg comments");
//			   }
//			   
//			   
			 
			   }catch(Exception e) {
					e.printStackTrace();
				}
		   
		// リレイログ吐き出し
		if (!FlagSetting.trainingModeFlag) {
			LogWriter.getInstance().outputLog(this.dos, this.keyData, this.fighting.getCharacters());
		}

		if (FlagSetting.jsonFlag) {
			//LogWriter.getInstance().updateJson(this.frameData, this.keyData);
			LogWriter.getInstance().updateJsonHl(hl, this.frameData);
		}

		if (FlagSetting.enableWindow) {
			// 画面をDrawerクラスで描画
			ResourceDrawer.getInstance().drawResource(this.fighting.getCharacters(), this.fighting.getProjectileDeque(),
					this.fighting.getHitEffectList(), this.frameData.getRemainingTimeMilliseconds(), this.currentRound);
		}

		// P1とP2の行った各アクションの数を数える
		if (FlagSetting.debugActionFlag) {
			DebugActionData.getInstance().countPlayerAction(this.fighting.getCharacters());
		}

		this.screenData = new ScreenData();

		// AIにFrameDataをセット
		InputManager.getInstance().setFrameData(this.frameData, this.screenData);

		// 体力が0orタイムオーバーならラウンド終了処理
		if (isBeaten() || isTimeOver()) {
			processingRoundEnd();
		}
	}

	/**
	 * ラウンド終了時の処理を行う.
	 */
	private void processingRoundEnd() {
		this.fighting.processingRoundEnd();
		RoundResult roundResult = new RoundResult(this.frameData);
		this.roundResults.add(roundResult);

		// AIに結果を渡す
		InputManager.getInstance().sendRoundResult(roundResult);
		this.currentRound++;
		this.roundStartFlag = true;

		// P1とP2の行った各アクションの数のデータをCSVに出力する
		if (FlagSetting.debugActionFlag) {
			DebugActionData.getInstance().outputActionCount();
		}
		try {
			eng.close();
		} catch (EngineException e) {
			e.printStackTrace();
		}
	}

	/**
	 * キャラクターが倒されたかどうかを判定する.
	 *
	 * @return {@code true}: P1 or P2が倒された，{@code false}: otherwise
	 */
	private boolean isBeaten() {
		return FlagSetting.limitHpFlag
				&& (this.frameData.getCharacter(true).getHp() <= 0 || this.frameData.getCharacter(false).getHp() <= 0);
	}

	/**
	 * 1ラウンドの制限時間が経過したかどうかを判定する.<br>
	 * Training modeのときは, Integerの最大との比較を行う.
	 *
	 * @return {@code true}: 1ラウンドの制限時間が経過した， {@code false}: otherwise
	 */
	private boolean isTimeOver() {
		if (FlagSetting.trainingModeFlag) {
			return this.nowFrame == Integer.MAX_VALUE;
		} else {
			return this.nowFrame == GameSetting.ROUND_FRAME_NUMBER - 1;
		}

	}

	/**
	 * リプレイファイルを作成し, 使用キャラクターを表すインデックスなどのヘッダ情報を記述する.
	 */
	private void openReplayFile() {
		String fileName = LogWriter.getInstance().createOutputFileName("./log/replay/", this.timeInfo);
		this.dos = ResourceLoader.getInstance().openDataOutputStream(fileName + ".dat");

		LogWriter.getInstance().writeHeader(this.dos);
	}

	@Override
	public void close() {
		this.fighting = null;
		this.frameData = null;
		this.screenData = null;
		this.keyData = null;
		// AIの実行を終了する
		InputManager.getInstance().closeAI();
		this.roundResults.clear();

		if (FlagSetting.debugActionFlag) {
			DebugActionData.getInstance().closeAllWriters();
		}

		try {
			if (this.dos != null) {
				this.dos.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (FlagSetting.jsonFlag) {
			LogWriter.getInstance().finalizeJson();
		}
	}
}
