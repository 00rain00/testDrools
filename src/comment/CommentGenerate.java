package comment;
//import org.apache.maven.model.building.Result;
import java.io.IOException;
import com.cavariux.twitchirc.Chat.*;
//import testPbot.CavsBot;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import comment.Fightbot;
import comment.Highlight;
import struct.FrameData;
import struct.CharacterData;
public class CommentGenerate {
	static  double maxHp = 400;
	public static void checkKie() {
		// TODO Auto-generated method stub
	System.out.println("commentgenerate called");
	KieServices ks = KieServices.Factory.get();
	KieContainer kContainer = ks.newKieClasspathContainer();
	try {
		kContainer.verify();
		
	}catch (Exception e) {
		System.out.println(e);
	}
	
		
		
		
	}
	
	public static synchronized void twitchBot(String msg) {
		Fightbot bot = new Fightbot();
		bot.connect();
        Channel channel = bot.joinChannel("#strecno");
        try {
			//Thread.sleep(1500);
			bot.sendMessage(msg, channel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
