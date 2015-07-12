package eu.greenlightning.softengine.geometry;

public class Face {

	private final int[] indices;

	public Face(int index0, int index1, int index2) {
		this.indices = new int[] { index0, index1, index2 };
	}

	public int getIndexCount() {
		return indices.length;
	}

	public int getIndex(int index) {
		return indices[checkIndex(index)];
	}

	private int checkIndex(int index) {
		if (index < 0 || index >= getIndexCount()) {
			throw new IllegalArgumentException("Index must be in Range [0, " + getIndexCount()
				+ "[, but was " + index + ".");
		}
		return index;
	}

	@Override
	public String toString() {
		return "[" + indices[0] + ", " + indices[1] + ", " + indices[2] + "]";
	}
	
}
