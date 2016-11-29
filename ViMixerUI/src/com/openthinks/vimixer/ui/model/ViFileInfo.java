package com.openthinks.vimixer.ui.model;

import java.util.concurrent.atomic.AtomicLong;

import com.openthinks.crypto.mix.MixBlock;

public class ViFileInfo {
	private volatile Long startTime;
	private volatile Long endTime;
	private volatile Long fileLength;
	private volatile Long blockLength;
	private volatile AtomicLong processedBlocks;
	private volatile MixBlock currentProcessedBlock;

	public ViFileInfo() {
		super();
		this.startTime = 0L;
		this.endTime = 0L;
		this.fileLength = 0L;
		this.blockLength = 0L;
		this.processedBlocks = new AtomicLong(0L);
		this.currentProcessedBlock = null;
	}

	public ViFileInfo setStartTime(Long startTime) {
		this.startTime = startTime;
		return this;
	}

	public ViFileInfo setEndTime(Long endTime) {
		this.endTime = endTime;
		return this;
	}

	public ViFileInfo setFileLength(Long fileLength) {
		this.fileLength = fileLength;
		return this;
	}

	public ViFileInfo setBlockLength(Long blockLength) {
		this.blockLength = blockLength;
		return this;
	}

	public Long getStartTime() {
		return startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public Long getFileLength() {
		return fileLength;
	}

	public Long getBlockLength() {
		return blockLength;
	}

	public Long getProcessedBlocks() {
		return processedBlocks.get();
	}

	public ViFileInfo setProcessedBlocks(Long processedBlocks) {
		this.processedBlocks.set(processedBlocks);
		return this;
	}

	public Long increase() {
		return this.processedBlocks.incrementAndGet();
	}

	public MixBlock getCurrentProcessedBlock() {
		return currentProcessedBlock;
	}

	public ViFileInfo setCurrentProcessedBlock(MixBlock currentProcessedBlock) {
		this.currentProcessedBlock = currentProcessedBlock;
		return this;
	}

	public Double computeProgress() {
		if (this.blockLength != null && this.blockLength != 0L) {
			return (this.processedBlocks.get() * 1.0D) / this.blockLength;
		}
		return 0D;
	}

	public String computeProgressPercent() {
		Double percent = computeProgress() * 100;
		return percent.intValue() + "%";
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		if (fileLength != null && fileLength > 0) {
			buffer.append("File length:" + fileLength);
		}
		if (blockLength != null && blockLength > 0) {
			buffer.append(";Block count:" + blockLength);
		}
		if (processedBlocks != null) {
			buffer.append(";Processed blocks count:" + processedBlocks);
		}
		if (currentProcessedBlock != null) {
			buffer.append(";Current block:" + currentProcessedBlock);
		}
		if (startTime != null && startTime > 0) {
			buffer.append(";Start time:" + startTime);
		}
		if (endTime != null && endTime > 0) {
			buffer.append(";Complete time:" + endTime);
		}

		return buffer.toString();
	}

}