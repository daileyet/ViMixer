package com.openthinks.crypto.mix;

/**
 * The strategy of how to do the mixing
 * @author minjdai
 * @since v1.0
 */
public interface MixStrategy {

	/**
	 * will do the details mixing
	 * @param mixBlock
	 */
	void action(MixBlock mixBlock);

}
