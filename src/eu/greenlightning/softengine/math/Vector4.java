package eu.greenlightning.softengine.math;

import java.util.Objects;

public final class Vector4 {

	public static Vector4 positionOf(Vector3 position) {
		Objects.requireNonNull(position, "Position must not be null.");
		return new Vector4(position.getX(), position.getY(), position.getZ(), 1);
	}

	public static Vector4 perspectiveDivide(Vector4 vector) {
		Objects.requireNonNull(vector, "Vector must not be null.");
		if (vector.w == 0) {
			return vector;
		} else {
			return new Vector4(vector.x / vector.w, vector.y / vector.w, vector.z / vector.w, 1);
		}
	}

	private final double x, y, z, w;

	public Vector4(double x, double y, double z, double w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
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

	public double getW() {
		return w;
	}

	public Vector4 plus(Vector4 other) {
		Objects.requireNonNull(other, "Other must not be null.");
		return plus(other.x, other.y, other.z, other.w);
	}

	public Vector4 plus(double x, double y, double z, double w) {
		return new Vector4(this.x + x, this.y + y, this.z + z, this.w + w);
	}

	public Vector4 minus(Vector4 other) {
		Objects.requireNonNull(other, "Other must not be null.");
		return minus(other.x, other.y, other.z, other.w);
	}

	public Vector4 minus(double x, double y, double z, double w) {
		return new Vector4(this.x - x, this.y - y, this.z - z, this.w - w);
	}

	public double scalar(Vector4 other) {
		Objects.requireNonNull(other, "Other must not be null.");
		return x * other.x + y * other.y + z * other.z + w * other.w;
	}

	@Override
	public int hashCode() {
		int hashX = new Double(x).hashCode();
		int hashY = new Double(y).hashCode() * 31;
		int hashZ = new Double(z).hashCode() * 31 * 31;
		int hashW = new Double(w).hashCode() * 31 * 31 * 31;
		return hashX ^ hashY ^ hashZ ^ hashW;
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Vector4) {
			Vector4 other = (Vector4) object;
			return x == other.x && y == other.y && z == other.z && w == other.w;
		}
		return false;
	}

	@Override
	public String toString() {
		return "[" + x + ", " + y + ", " + z + ", " + w + "]";
	}

}
