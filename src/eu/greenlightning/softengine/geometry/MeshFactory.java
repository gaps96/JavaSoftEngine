package eu.greenlightning.softengine.geometry;

import eu.greenlightning.softengine.math.Vector3;

public final class MeshFactory {

	public static Mesh createCube(double sideLength) {
		double halfLength = sideLength * 0.5;
		Vector3[] vertices = new Vector3[8];
		vertices[0] = new Vector3(+halfLength, +halfLength, +halfLength);
		vertices[1] = new Vector3(+halfLength, +halfLength, -halfLength);
		vertices[2] = new Vector3(+halfLength, -halfLength, +halfLength);
		vertices[3] = new Vector3(+halfLength, -halfLength, -halfLength);
		vertices[4] = new Vector3(-halfLength, +halfLength, +halfLength);
		vertices[5] = new Vector3(-halfLength, +halfLength, -halfLength);
		vertices[6] = new Vector3(-halfLength, -halfLength, +halfLength);
		vertices[7] = new Vector3(-halfLength, -halfLength, -halfLength);
		Face[] faces = new Face[2 * 6];

		// right face
		faces[0] = new Face(0, 1, 2);
		faces[1] = new Face(1, 3, 2);

		// left face
		faces[2] = new Face(5, 4, 7);
		faces[3] = new Face(4, 6, 7);

		// top face
		faces[4] = new Face(5, 1, 4);
		faces[5] = new Face(1, 0, 4);

		// bottom face
		faces[6] = new Face(6, 2, 7);
		faces[7] = new Face(2, 3, 7);

		// front face
		faces[8] = new Face(4, 0, 6);
		faces[9] = new Face(0, 2, 6);

		// back face
		faces[10] = new Face(1, 5, 3);
		faces[11] = new Face(5, 7, 3);
		return new Mesh(vertices, faces);
	}

	public static Mesh createSquarePyramid(double sideLength, double height) {
		double halfLength = sideLength * 0.5;
		Vector3[] vertices = new Vector3[5];
		vertices[0] = new Vector3(0, height, 0);
		vertices[1] = new Vector3(+halfLength, 0, +halfLength);
		vertices[2] = new Vector3(+halfLength, 0, -halfLength);
		vertices[3] = new Vector3(-halfLength, 0, +halfLength);
		vertices[4] = new Vector3(-halfLength, 0, -halfLength);
		Face[] faces = new Face[6];

		// base
		faces[0] = new Face(3, 1, 4);
		faces[1] = new Face(1, 2, 4);

		// sides
		faces[2] = new Face(0, 2, 1);
		faces[3] = new Face(0, 4, 2);
		faces[4] = new Face(0, 3, 4);
		faces[5] = new Face(0, 1, 3);
		return new Mesh(vertices, faces);
	}

	private MeshFactory() {}

}
