package comment;

import static org.junit.Assert.*;
import struct.FrameData;
import org.junit.Before;
import org.junit.Test;
import fighting.Fighting;
import struct.CharacterData;
import fighting.Character;
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
		assertEquals("not same",hl.getP1Damage(),30,0);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
