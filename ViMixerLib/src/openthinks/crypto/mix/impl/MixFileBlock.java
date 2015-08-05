package openthinks.crypto.mix.impl;

import java.io.IOException;
import java.nio.MappedByteBuffer;

import openthinks.crypto.mix.MixBlock;
import openthinks.crypto.mix.MixTarget;
import openthinks.crypto.mix.Segment;

/**
 * File block
 * @author minjdai
 * @since v1.0
 */
public final class MixFileBlock extends MixBlock {
	private MixFile mixFile = null;
	private byte[] bytes = null;

	public MixFileBlock(Segment segment, MixFile mixFile) {
		super(segment);
		this.mixFile = mixFile;
	}

	@Override
	protected MixTarget target() {
		return mixFile;
	}

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

	@Override
	public void persist() {
		if (this.bytes == null)
			return;
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
