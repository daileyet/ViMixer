package openthinks.crypto.mix.impl;

import java.util.Date;

import openthinks.crypto.mix.MixBlock;
import openthinks.crypto.mix.MixProcesser;

/**
 * The default mix processer
 * @author dailey.yet@outlook.com
 * @since v1.0
 */
public class DefaultMixProcesser implements MixProcesser {
	public final static MixProcesser get() {
		return new DefaultMixProcesser();
	}

	protected long startTime;
	protected long completeTime;

	@Override
	public void processed(MixBlock mixBlock) {
		System.out.println("Complete block:" + mixBlock);
	}

	@Override
	public void start() {
		startTime = new Date().getTime();
	}

	@Override
	public void completed() {
		completeTime = new Date().getTime();
		System.out.println("Cost time:" + cost());
	}

	@Override
	public long cost() {
		return this.completeTime - this.startTime;
	}

}
