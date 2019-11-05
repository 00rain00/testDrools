package util;

import static org.junit.Assert.*;
import comment.Highlight;
import struct.FrameData;
import org.junit.Before;
import org.junit.Test;

public class LogWriterTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testUpdateJsonHl() {
		System.out.println("test update Json hl");
		FrameData fd  = new FrameData();
		Highlight hl = new Highlight();
		
		try {
			String jsonName = LogWriter.getInstance().createOutputFileName("./log/replay/", "jsontest");
			LogWriter.getInstance().initJson(jsonName + ".json");
			fd.setCurrentFrameNumber(1);
			LogWriter.getInstance().updateJsonHl(hl, fd);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
