package com.openthinks.crypto.mix;

/**
 * The block in {@link MixTarget} need to do mixing. Wrapper {@link Segment}
 * 
 * @author minjdai
 * @since v1.0
 */
public abstract class MixBlock {
	protected Segment segment;
	protected MixBlockStatus status;

	public MixBlock(Segment segment, MixBlockStatus status) {
		this.segment = segment;
		this.status = status;
	}

	public MixBlock(Segment segment) {
		this(segment, MixBlockStatus.NOT_PROCESSED);
	}

	@Override
	public String toString() {
		return "MixBlock [segment=" + segment + "]";
	}

	public void markProcessed() {
		setStatus(MixBlockStatus.PROCESSED);
	}

	/**
	 * reference its owner {@link MixTarget}
	 * 
	 * @return
	 */
	protected abstract MixTarget target();

	/**
	 * @return The byte array of block content
	 */
	public abstract byte[] getBytes();

	/**
	 * save the changes of current block into its owner
	 */
	public abstract void persist();

	public Segment getSegment() {
		return segment;
	}

	public void setSegment(Segment segment) {
		this.segment = segment;
	}

	public long getPosition() {
		if (segment != null) {
			return segment.getPosition();
		}
		return 0;
	}

	public long getLength() {
		if (segment != null) {
			return segment.getLength();
		}
		return 0;
	}

	/**
	 * @return the status
	 */
	public MixBlockStatus getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(MixBlockStatus status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((segment == null) ? 0 : segment.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MixBlock other = (MixBlock) obj;
		if (segment == null) {
			if (other.segment != null)
				return false;
		} else if (!segment.equals(other.segment))
			return false;
		return true;
	}

}
