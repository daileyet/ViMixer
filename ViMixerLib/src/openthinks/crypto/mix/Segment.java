package openthinks.crypto.mix;

/**
 * The block area.
 * @author minjdai
 * @since v1.0
 */
public final class Segment {

	public final static Segment create(long p, long l) {
		return new Segment(p, l);
	}

	private long position;
	private long length;

	public Segment() {
		super();
	}

	public Segment(long position, long length) {
		super();
		this.position = position;
		this.length = length;
	}

	/**
	 * block start position
	 * @return the position in {@link MixTarget}
	 */
	public long getPosition() {
		return position;
	}

	public void setPosition(long position) {
		this.position = position;
	}

	/**
	 * block length
	 * @return the block length
	 */
	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	@Override
	public String toString() {
		return "Segment [position=" + position + ", length=" + length + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (position ^ (position >>> 32));
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
		Segment other = (Segment) obj;
		if (position != other.position)
			return false;
		return true;
	}

}
