package xyz.openthinks.crypto.mix;
/**
 * The generator for which {@link Segment} of {@link MixTarget} need to do mixing.
 * @author minjdai
 *
 */
public interface MixSegmentor {

	/**
	 * 
	 * @param lengthOfMixtarget The length of {@link MixTarget}
	 * @return The array of {@link Segment}
	 */
	public Segment[] calcuate(long lengthOfMixtarget);

}
