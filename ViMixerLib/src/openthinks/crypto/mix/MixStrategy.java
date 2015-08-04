package openthinks.crypto.mix;
/**
 * The strategy of how to do the mixing
 * @author minjdai
 *
 */
public interface MixStrategy {

	/**
	 * will do the details mixing
	 * @param mixBlock
	 */
	void action(MixBlock mixBlock);

}
