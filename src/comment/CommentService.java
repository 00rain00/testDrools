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
			hl.setFrameNumber(fd.currentFrameNumber);
			CharacterData p1 = fd.getCharacter(true);
			CharacterData p2 = fd.getCharacter(false);
			hl.setP1Damage(oriHp-p1.getHp());
			hl.setP2Damage(oriHp-p2.getHp());
			hl.setP1Hits(p1.getHitCount());
			hl.setP2Hits(p2.getHitCount());
			hl.setP1Energy(p1.getEnergy());
			hl.setP2Energy(p2.getEnergy());
			double d1 = 1.0 - ((double)Math.abs(480.0 - p1.getCenterX())/480.0); //0->left corner 1-> right corner
			double d2 = 1.0 - ((double)Math.abs(480.0 - p2.getCenterX())/480.0);
			hl.setP1Position(d1);
			hl.setP2Position(d2);
			hl.setDifDis((double)Math.abs(p1.getCenterX()-p2.getCenterX()/480.0));
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
