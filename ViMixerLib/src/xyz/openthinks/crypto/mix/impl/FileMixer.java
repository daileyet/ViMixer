/**
 * 
 */
package xyz.openthinks.crypto.mix.impl;

import java.io.File;

import xyz.openthinks.crypto.mix.AbstractMixer;
import xyz.openthinks.crypto.mix.MixProcesser;
import xyz.openthinks.crypto.mix.MixStrategy;
import xyz.openthinks.crypto.mix.MixTarget;

/**
 * @author minjdai
 *
 */
public class FileMixer extends AbstractMixer {
	private MixTarget mixFile;
	private MixStrategy mixStrategy;
	private MixProcesser mixProcesser;
	
	public FileMixer(MixTarget mixFile, MixStrategy mixStrategy,
			MixProcesser mixProcesser) {
		super();
		this.mixFile = mixFile;
		this.mixStrategy = mixStrategy;
		this.mixProcesser = mixProcesser;
	}

	public FileMixer(MixTarget mixFile, MixStrategy mixStrategy) {
		this(mixFile,mixStrategy,DefaultMixProcesser.get());
	}
	
	public FileMixer(File file, MixStrategy mixStrategy) {
		this(new MixFile(file),mixStrategy,DefaultMixProcesser.get());
	}

	public FileMixer(File file,String key){
		this(new MixFile(file),DefaultMixStrategy.get(key),DefaultMixProcesser.get());
	}

	@Override
	protected MixTarget getMixTarget() {
		return mixFile;
	}

	@Override
	protected MixStrategy getMixStrategy() {
		return mixStrategy;
	}

	@Override
	protected MixProcesser getMixProcesser() {
		return mixProcesser;
	}
	
}
