package xyz.openthinks.crypto.mix.impl;

import java.io.File;

import xyz.openthinks.crypto.mix.MixTarget;
import xyz.openthinks.crypto.mix.Mixer;

public class FileMixerTest {
	
	
	public static void main(String[] args) throws InterruptedException {
		File file = 
				//new File("D:\\TianmuLake.jpg");
				//new File("D:\\hcm_upg_schedule.xml");
				new File("D:\\2013-3-26.avi");
		MixTarget mixFile =new MixFile(file, SmartMixSegment.get());
		Mixer mixer = 
				//new FileMixer(file,"123456");
				new FileMixer(mixFile , DefaultMixStrategy.get("123456"));
		mixer.mix();
	}
	
}
