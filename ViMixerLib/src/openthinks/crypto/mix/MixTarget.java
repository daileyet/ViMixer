package openthinks.crypto.mix;
/**
 * The target which need to do mixing.
 * It can be file,text string
 * @author minjdai
 *
 */
public interface MixTarget {

	/**
	 * get all need to do mixing block on current target
	 * @return {@link MixBlocks}
	 */
	MixBlocks blocks();

	/**
	 * do some initial actions, prepare target
	 */
	void initial();
	
	/**
	 * do some clear actions, release target
	 */
	void free();
}
