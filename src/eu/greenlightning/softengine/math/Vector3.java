package eu.greenlightning.softengine.math;

import java.util.Objects;

public final class Vector3 {

	public static final Vector3 ZERO = new Vector3(0, 0, 0);
	public static final Vector3 UNIT_X = new Vector3(1, 0, 0);
	public static final Vector3 UNIT_Y = new Vector3(0, 1, 0);
	public static final Vector3 UNIT_Z = new Vector3(0, 0, 1);

	private final double x, y, z;

	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public Vector3 plus(Vector3 other) {
		Objects.requireNonNull(other, "Other must not be null.");
		return plus(other.x, other.y, other.z);
	}

	public Vector3 plus(double x, double y, double z) {
		return new Vector3(this.x + x, this.y + y, this.z + z);
	}

	public Vector3 minus(Vector3 other) {
		Objects.requireNonNull(other, "Other must not be null.");
		return minus(other.x, other.y, other.z);
	}

	public Vector3 minus(double x, double y, double z) {
		return new Vector3(this.x - x, this.y - y, this.z - z);
	}

	public Vector3 negate() {
		return scale(-1);
	}

	/**
	 * Returns a new {@link Vector3} which points in the same direction as this vector, but has length 1.
	 * <p>
	 * If called on a vector with length 0 (a zero vector), which cannot be normalized, it will return the
	 * zero vector.
	 * 
	 * @return a unit vector pointing in the same direction as this vector or the zero vector, if this is the
	 *         zero vector.
	 */
	public Vector3 normalize() {
		if (x == 0 && y == 0 && z == 0) {
			return this;
		} else {
			return scale(1.0 / length());
		}
	}

	public double length() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	public Vector3 scale(double factor) {
		return new Vector3(x * factor, y * factor, z * factor);
	}

	public double scalar(Vector3 other) {
		Objects.requireNonNull(other, "Other must not be null.");
		return x * other.x + y * other.y + z * other.z;
	}

	public Vector3 cross(Vector3 other) {
		Objects.requireNonNull(other, "Other must not be null.");
		double cx = y * other.z - z * other.y;
		double cy = z * other.x - x * other.z;
		double cz = x * other.y - y * other.x;
		return new Vector3(cx, cy, cz);
	}

	@Override
	public int hashCode() {
		int hashX = new Double(x).hashCode();
		int hashY = new Double(y).hashCode() * 31;
		int hashZ = new Double(z).hashCode() * 31 * 31;
		return hashX ^ hashY ^ hashZ;
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Vector3) {
			Vector3 other = (Vector3) object;
			return x == other.x && y == other.y && z == other.z;
		}
		return false;
	}

	@Override
	public String toString() {
		return "[" + x + ", " + y + ", " + z + "]";
	}

}
