package xyz.openthinks.vimixer.ui.model.configure;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;
import javax.xml.bind.PropertyException;

import openthinks.vimixer.ui.model.configure.Segmentor;
import openthinks.vimixer.ui.model.configure.SimpleLinearSegmentor;
import openthinks.vimixer.ui.model.configure.SmartLinearSegmentor;
import openthinks.vimixer.ui.model.configure.ViMixerConfigure;

public class ViMixerConfigureTest {
	
	public void testMarshaller() throws PropertyException, FileNotFoundException, JAXBException{
		Segmentor segmentorP = new SmartLinearSegmentor();
		segmentorP= new SimpleLinearSegmentor();
		ViMixerConfigure configureP = new ViMixerConfigure("testP","123456",segmentorP);
		
		ViMixerConfigure.marshal(configureP, new File("D:\\testP.xml"));
	}
	
	public void testUnMarshaller() throws JAXBException{
		ViMixerConfigure configureP = ViMixerConfigure.unmarshal(new File("D:\\testP.xml"));
		System.out.println(configureP);
	}
	
	public static void main(String[] args) throws PropertyException, FileNotFoundException, JAXBException {
		ViMixerConfigureTest instance = new ViMixerConfigureTest();
		instance.testMarshaller();
		instance.testUnMarshaller();
	}
	
}
