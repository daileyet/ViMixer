/**
 * 
 */
package xyz.openthinks.crypto.mix.impl;

import java.io.IOException;
import java.nio.MappedByteBuffer;

import xyz.openthinks.crypto.mix.MixBlock;
import xyz.openthinks.crypto.mix.MixTarget;
import xyz.openthinks.crypto.mix.Segment;

/**
 * @author minjdai
 *
 */
public final class MixFileBlock extends MixBlock {
	private MixFile mixFile = null;
	private byte[] bytes = null;
	
	public MixFileBlock(Segment segment,MixFile mixFile) {
		super(segment);
		this.mixFile=mixFile;
	}
	
	/* (non-Javadoc)
	 * @see xyz.openthinks.crypto.mix.MixBlock#target()
	 */
	@Override
	protected MixTarget target() {
		return mixFile;
	}

	/* (non-Javadoc)
	 * @see xyz.openthinks.crypto.mix.MixBlock#getBytes()
	 */
	@Override
	public byte[] getBytes() {
		MappedByteBuffer mappedByteBuffer = null;
		bytes = new byte[(int) segment.getLength()];
		try {
			mappedByteBuffer = mixFile.getMappedByteBuffer(this);
			mappedByteBuffer.get(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bytes;
	}

	/* (non-Javadoc)
	 * @see xyz.openthinks.crypto.mix.MixBlock#persist()
	 */
	@Override
	public void persist() {
		if(this.bytes==null) return;
		MappedByteBuffer mappedByteBuffer = null;
		try {
			mappedByteBuffer = mixFile.getMappedByteBuffer(this);
			mappedByteBuffer.put(this.bytes);
			mappedByteBuffer.force();
			mappedByteBuffer.clear();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
