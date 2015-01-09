package eu.greenlightning.softengine.math;

public final class Matrix4Factory {

	public static Matrix4 createViewMatrixLookingAt(Vector3 position, Vector3 target, Vector3 up) {
		Vector3 zaxis = position.minus(target).normalize();
		Vector3 xaxis = up.cross(zaxis).normalize();
		Vector3 yaxis = zaxis.cross(xaxis);

		Matrix4 orientation = createTransformationMatrixFromNormalizedAxisVectors(xaxis, yaxis, zaxis);
		Matrix4 translation = createTranslationMatrix(position.negate());

		return translation.multiply(orientation);
	}

	/**
	 * @param angles in radians
	 */
	public static Matrix4 createRotationMatrixFromEulerAngles(Vector3 angles) {
		Matrix4 x = createRotationMatrixX(angles.getX());
		Matrix4 y = createRotationMatrixY(angles.getY());
		Matrix4 z = createRotationMatrixZ(angles.getZ());
		return z.multiply(y.multiply(x));
	}

	/**
	 * @param theta in radians
	 */
	public static Matrix4 createRotationMatrixX(double theta) {
		double s = Math.sin(theta);
		double c = Math.cos(theta);
		//@formatter:off
		return new Matrix4(new double[] {
			 1,  0,  0, 0,
			 0,  c, -s, 0,
			 0,  s,  c, 0,
			 0,  0,  0, 1,
		});
		//@formatter:on
	}

	/**
	 * @param theta in radians
	 */
	public static Matrix4 createRotationMatrixY(double theta) {
		double s = Math.sin(theta);
		double c = Math.cos(theta);
		//@formatter:off
		return new Matrix4(new double[] {
			 c,  0,  s, 0,
			 0,  1,  0, 0,
			-s,  0,  c, 0,
			 0,  0,  0, 1,
		});
		//@formatter:on
	}

	/**
	 * @param theta in radians
	 */
	public static Matrix4 createRotationMatrixZ(double theta) {
		double s = Math.sin(theta);
		double c = Math.cos(theta);
		//@formatter:off
		return new Matrix4(new double[] {
			 c, -s,  0, 0,
			 s,  c,  0, 0,
			 0,  0,  1, 0,
			 0,  0,  0, 1,
		});
		//@formatter:on
	}

	public static Matrix4 createOrthogonalProjectionMatrix(double width, double height, double near,
		double far) {
		//@formatter:off
		return new Matrix4(new double[] {
			2.0 / width, 0, 0, 0,
			0, 2.0 / height, 0, 0,
			0, 0, -2 / (far - near), 0,
			0, 0, 0, 1,
		});
		//@formatter:on
	}

	/**
	 * @param fov field of view; in degrees
	 * @param aspect aspect ratio; width / height
	 */
	public static Matrix4
		createPerspectiveProjectionMatrix(double fov, double aspect, double near, double far) {
		double horizontalScale = 1.0 / Math.tan(Math.toRadians(fov / 2));
		double verticalScale = horizontalScale * aspect;
		double depth = far - near;
		//@formatter:off
		return new Matrix4(new double[] {
			horizontalScale, 0, 0, 0,
			0, verticalScale, 0, 0,
			0, 0, -(far + near) / depth, - 2 * far * near / depth,
			0, 0, -1, 0,
		});
		//@formatter:on
	}

	public static Matrix4
		createTransformationMatrixFromNormalizedAxisVectors(Vector3 x, Vector3 y, Vector3 z) {
		//@formatter:off
		return new Matrix4(new double[] {
			x.getX(), y.getX(), z.getX(), 0,
			x.getY(), y.getY(), z.getY(), 0,
			x.getZ(), y.getZ(), z.getZ(), 0,
			0, 0, 0, 1,
		});
		//@formatter:on
	}

	public static Matrix4 createTranslationMatrix(Vector3 position) {
		//@formatter:off
		return new Matrix4(new double[] {
			1, 0, 0, position.getX(),
			0, 1, 0, position.getY(),
			0, 0, 1, position.getZ(),
			0, 0, 0, 1,
		});
		//@formatter:on
	}

	private Matrix4Factory() {}

}
