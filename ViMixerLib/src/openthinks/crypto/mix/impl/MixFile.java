/**
 * 
 */
package openthinks.crypto.mix.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.List;

import openthinks.crypto.mix.MixBlock;
import openthinks.crypto.mix.MixBlocks;
import openthinks.crypto.mix.MixSegmentor;
import openthinks.crypto.mix.MixTarget;
import openthinks.crypto.mix.Segment;

/**
 * File target
 * @author minjdai
 * @since v1.0
 */
public class MixFile implements MixTarget {
	private File file;
	private RandomAccessFile randomAccessFile;
	private MixSegmentor mixSegment;

	public MixFile(File file) {
		super();
		this.file = file;
		this.mixSegment = DefaultMixSegment.get();
	}

	public MixFile(File file, MixSegmentor mixSegment) {
		super();
		this.file = file;
		if (mixSegment != null) {
			this.mixSegment = mixSegment;
		} else {
			this.mixSegment = DefaultMixSegment.get();
		}

	}

	private MixBlocks cache;

	@Override
	public MixBlocks blocks() {
		if (cache != null && cache.size() > 0) {
			return cache;
		}
		long fileLength = file.length();
		Segment[] segments = mixSegment.calcuate(fileLength);
		List<MixBlock> list = new ArrayList<MixBlock>();
		for (Segment segment : segments) {
			list.add(new MixFileBlock(segment, this));
		}
		cache = MixBlocks.create(list);
		return cache;
	}

	protected MappedByteBuffer getMappedByteBuffer(MixBlock mixblock) throws IOException {
		MappedByteBuffer mappedByteBuffer = randomAccessFile.getChannel().map(MapMode.READ_WRITE,
				mixblock.getPosition(), mixblock.getLength());
		return mappedByteBuffer;
	}

	@Override
	public void initial() {
		if (randomAccessFile != null) {
			this.free();
		}
		try {
			randomAccessFile = new RandomAccessFile(file, "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void free() {
		if (randomAccessFile != null) {
			try {
				randomAccessFile.close();
				randomAccessFile = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
