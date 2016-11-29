package com.openthinks.crypto.mix;

/**
 * The progress of doing mixing, it will track the action and status of {@link Mixer}
 * @author minjdai
 * @since v1.0
 */
public interface MixProcesser {

	/**
	 * represent the instance of {@link Mixer} has been started
	 */
	void start();

	/**
	 * represent the instance of {@link Mixer} in processing of the special {@link MixBlock}
	 * @return current processed {@link MixBlock} 
	 */
	void processed(MixBlock block);

	/**
	 * represent the instance of {@link Mixer} has been completed
	 */
	void completed();

	/**
	 * 
	 * @return the cost time of whole process on mixing.
	 */
	long cost();
}
