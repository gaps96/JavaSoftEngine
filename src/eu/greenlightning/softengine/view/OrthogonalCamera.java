package eu.greenlightning.softengine.view;

import static eu.greenlightning.softengine.math.Matrix4Factory.*;
import eu.greenlightning.softengine.math.Matrix4;

public class OrthogonalCamera extends AbstractCamera {

	private double worldWidth;
	private double worldHeight;

	private double near;
	private double far;

	@Override
	public Matrix4 getProjectionMatrix(int screenWidth, int screenHeight) {
		return createOrthogonalProjectionMatrix(worldWidth, worldHeight, near, far);
	}

}
