package eu.greenlightning.softengine.view;

import static eu.greenlightning.softengine.math.Matrix4Factory.*;
import eu.greenlightning.softengine.math.Matrix4;

public class PerspectiveCamera extends AbstractCamera {

	public double fov = 90;

	public double near = 0.01;
	public double far = 100;

	@Override
	public Matrix4 getProjectionMatrix(int width, int height) {
		double aspect = width / (double) height;
		return createPerspectiveProjectionMatrix(fov, aspect, near, far);
	}

}
