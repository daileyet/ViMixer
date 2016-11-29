/**
 * 
 */
package com.openthinks.crypto.mix.impl;

import com.openthinks.crypto.DAESer;
import com.openthinks.crypto.mix.IllegalMixStrategyException;
import com.openthinks.crypto.mix.MixBlock;
import com.openthinks.crypto.mix.MixBlockStatus;
import com.openthinks.crypto.mix.MixStrategy;

/**
 * The default mix action strategy
 * @author minjdai
 * @since v1.0
 */
public final class DefaultMixStrategy implements MixStrategy {
	public final static DefaultMixStrategy get(String userKey) {
		return new DefaultMixStrategy(userKey);
	}

	private transient DAESer daeSer;
	private transient final byte[] MIX_MARKS = { 67, 82, 89, 80, 84, 64, 79, 80, 69, 78, 84, 72, 73, 78, 75, 83 };
	private transient byte[] encrypt;

	private DefaultMixStrategy(String userKey) {
		daeSer = new DAESer(userKey);
		try {
			encrypt = daeSer.encrypt(MIX_MARKS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void action(MixBlock mixBlock) {
		long blockLen = mixBlock.getLength();
		long encryptLen = encrypt.length;
		byte[] _encrypt = encrypt;
		if (encryptLen == 0)
			throw new IllegalMixStrategyException();
		long spare = blockLen / encryptLen;
		if (spare > 1) {
			_encrypt = new byte[(int) (spare * encryptLen)];
			for (int i = 0; i < spare; i++) {
				System.arraycopy(encrypt, 0, _encrypt, (int) (i * encryptLen), (int) encryptLen);
			}
		}
		byte[] blockBytes = mixBlock.getBytes();
		for (int i = 0, j = blockBytes.length; i < j; i++) {
			blockBytes[i] = (byte) (blockBytes[i] ^ _encrypt[i]);
		}
		mixBlock.setStatus(MixBlockStatus.PROCESSED);
	}

}
