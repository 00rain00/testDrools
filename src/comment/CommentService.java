package comment;
import struct.FrameData;
import struct.CharacterData;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;

import comment.Highlight;
public class CommentService {
	static int oriHp = 400;
	public static Highlight setHighlight(FrameData fd) {
		org.slf4j.Logger logger = LoggerFactory.getLogger(CommentService.class);
		Highlight hl = new Highlight();
		if(fd.currentFrameNumber>=1) {
			try {
			//p1
			hl.setFrameNumber(fd.currentFrameNumber);
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
			
			}catch(Exception e) {
				logger.error(e.getMessage(), e);
			}
		}else {
			logger.info(" current frame <1");
		}
		
		return hl;
	}
	
}
