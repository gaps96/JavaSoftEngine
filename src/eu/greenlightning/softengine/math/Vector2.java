package eu.greenlightning.softengine.math;

import java.util.Objects;

public final class Vector2 {

	private final double x, y;

	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public Vector2 plus(Vector2 other) {
		Objects.requireNonNull(other, "Other must not be null.");
		return plus(other.x, other.y);
	}

	public Vector2 plus(double x, double y) {
		return new Vector2(this.x + x, this.y + y);
	}

	public Vector2 minus(Vector2 other) {
		Objects.requireNonNull(other, "Other must not be null.");
		return minus(other.x, other.y);
	}

	public Vector2 minus(double x, double y) {
		return new Vector2(this.x - x, this.y - y);
	}

	public Vector2 multiply(double factor) {
		return new Vector2(x * factor, y * factor);
	}

}
