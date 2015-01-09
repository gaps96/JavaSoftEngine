package eu.greenlightning.softengine.math;

import java.util.Objects;

public final class Matrix4 {

	public static final Matrix4 IDENTITY;

	static {
		//@formatter:off
		IDENTITY = new Matrix4(new double[] {
			 1, 0, 0, 0,
			 0, 1, 0, 0,
			 0, 0, 1, 0,
			 0, 0, 0, 1,
		});
		//@formatter:on
	}

	private final double[] values;

	public Matrix4(double[] values) {
		this.values = values;
		validateValues();
	}

	private void validateValues() {
		Objects.requireNonNull(values, "Values must not be null.");
		validateValuesLength();
	}

	private void validateValuesLength() {
		if (values.length != 16) {
			throw new IllegalArgumentException("Values must have length 16, but found length "
				+ values.length + ".");
		}
	}

	public Vector4 multiply(Vector4 vector) {
		Objects.requireNonNull(vector, "Vector must not be null.");
		//@formatter:off
		return new Vector4(
			rowVector(0).scalar(vector),
			rowVector(1).scalar(vector),
			rowVector(2).scalar(vector),
			rowVector(3).scalar(vector)
		);
		//@formatter:on
	}

	public Matrix4 multiply(Matrix4 matrix) {
		Objects.requireNonNull(matrix, "Matrix must not be null.");
		double[] result = new double[16];
		for (int row = 0; row < 4; row++) {
			for (int column = 0; column < 4; column++) {
				result[4 * row + column] = rowVector(row).scalar(matrix.columnVector(column));
			}
		}
		return new Matrix4(result);
	}

	public Vector4 rowVector(int row) {
		validateIndex(row, "Row");
		//@formatter:off
		return new Vector4(
			getInternal(row, 0),
			getInternal(row, 1),
			getInternal(row, 2),
			getInternal(row, 3)
		);
		//@formatter:on
	}

	public Vector4 columnVector(int column) {
		validateIndex(column, "Column");
		//@formatter:off
		return new Vector4(
			getInternal(0, column),
			getInternal(1, column),
			getInternal(2, column),
			getInternal(3, column)
		);
		//@formatter:on
	}

	public double get(int row, int column) {
		validateIndex(row, "Row");
		validateIndex(column, "Column");
		return getInternal(row, column);
	}

	private double getInternal(int row, int column) {
		return values[4 * row + column];
	}

	private void validateIndex(int index, String description) {
		if (index < 0 || index >= 4) {
			throw new IllegalArgumentException(description + " must be in range [0, 4[, but was " + index
				+ ".");
		}
	}

}
