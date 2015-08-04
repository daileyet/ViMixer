/**
 * 
 */
package openthinks.crypto.mix.impl;

import openthinks.crypto.DAESer;
import openthinks.crypto.mix.IllegalMixStrategyException;
import openthinks.crypto.mix.MixBlock;
import openthinks.crypto.mix.MixBlockStatus;
import openthinks.crypto.mix.MixStrategy;

/**
 * @author minjdai
 *
 */
public final class DefaultMixStrategy implements MixStrategy {
	public final static DefaultMixStrategy get(String userKey){
		return new DefaultMixStrategy(userKey);
	}
	
	private transient DAESer daeSer;
	private transient final byte[] MIX_MARKS = {67,82,89,80,84,64,79,80,69,78,84,72,73,78,75,83};
	private transient byte[] encrypt;
	private DefaultMixStrategy(String userKey) {
		daeSer = new DAESer(userKey);
		try {
			encrypt = daeSer.encrypt(MIX_MARKS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see example.crypto.mix.MixStrategy#action(example.crypto.mix.MixBlock)
	 */
	@Override
	public void action(MixBlock mixBlock) {
		long blockLen  = mixBlock.getLength();
		long encryptLen = encrypt.length;
		byte[] _encrypt = encrypt;
		if(encryptLen == 0)
			throw new IllegalMixStrategyException();
		long spare = blockLen/encryptLen;
		if(spare>1){
			_encrypt = new byte[(int) (spare*encryptLen)];
			for(int i=0;i<spare;i++){
				System.arraycopy(encrypt, 0, _encrypt, (int)(i*encryptLen), (int)encryptLen);
			}
		}
		byte[] blockBytes = mixBlock.getBytes();
		for(int i=0,j=blockBytes.length;i<j;i++){
			blockBytes[i] = (byte) (blockBytes[i] ^ _encrypt[i]);
		}
		mixBlock.setStatus(MixBlockStatus.PROCESSED);
	}

}
