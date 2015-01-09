package eu.greenlightning.softengine.view;

import eu.greenlightning.softengine.math.Matrix4;

public interface Camera {

	Matrix4 getViewMatrix();
	Matrix4 getProjectionMatrix(int width, int height);

}
