package comment;

import static org.junit.Assert.*;
import struct.FrameData;
import org.junit.Before;
import org.junit.Test;
import fighting.Fighting;
import struct.CharacterData;
import fighting.Character;
import com.darkprograms.speech.synthesiser.SynthesiserV2;
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
	public void testText2Speech() {
		try {
		Message msg = new Message();
		msg.addComments("ZEN use high kick");
		msg.addComments("ZEN use low kick");
		msg.addComments("ZEn use super kick");
		SynthesiserV2 synthesizer = new SynthesiserV2("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw");
		//CommentService.text2Speech(msg, synthesizer);
		
		
		Thread.sleep(10000);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testTestMatlab() {
		try {
		CommentService.testMatlab();
		
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	@Test
	public void testTTS() {
		try {
			//CommentService.testTTS();
			Thread.sleep(10000);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
