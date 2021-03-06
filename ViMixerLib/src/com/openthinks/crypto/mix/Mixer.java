package com.openthinks.crypto.mix;

/**
 * interface Mixer, which can mix file,text, makes them could not be recognized. Just like encrypt them with special way.
 * @author minjdai
 * @since v1.0
 */
public interface Mixer {

	/**
	 * the action of doing mix
	 * @throws InterruptedException
	 */
	public void mix() throws InterruptedException;

}
