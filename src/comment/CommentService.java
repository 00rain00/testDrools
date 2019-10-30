package comment;
import struct.FrameData;
import struct.CharacterData;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;

import comment.Highlight;
public class CommentService {
	static double oriHp = 400;
	public static Highlight setHighlight(FrameData fd) {
		org.slf4j.Logger logger = LoggerFactory.getLogger(CommentService.class);
		Highlight hl = new Highlight();
		if(fd!=null&&fd.currentFrameNumber>=1) {
			try {
			//p1
			CharacterData p1 = fd.getCharacter(true);
			CharacterData p2 = fd.getCharacter(false);
			hl.setP1Damage(oriHp-p1.getHp());
			
			//p2
			
			
			}catch(Exception e) {
				logger.error(e.getMessage(), e);
			}
		}else {
			logger.info("empty fd , current frame <1");
		}
		
		return hl;
	}
	
}
