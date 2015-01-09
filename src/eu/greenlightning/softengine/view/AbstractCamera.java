package eu.greenlightning.softengine.view;

import static eu.greenlightning.softengine.math.Matrix4Factory.*;
import eu.greenlightning.softengine.math.Matrix4;
import eu.greenlightning.softengine.math.Vector3;

public abstract class AbstractCamera implements Camera {

	public Vector3 position = Vector3.ZERO;
	public Vector3 target = Vector3.ZERO;
	public Vector3 up = Vector3.UNIT_Y;

	@Override
	public Matrix4 getViewMatrix() {
		return createViewMatrixLookingAt(position, target, up);
	}

}
