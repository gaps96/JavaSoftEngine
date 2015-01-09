package eu.greenlightning.softengine.geometry;

import static eu.greenlightning.softengine.math.Matrix4Factory.*;

import java.util.Objects;

import eu.greenlightning.softengine.math.Matrix4;
import eu.greenlightning.softengine.math.Vector3;

public class Mesh {

	private String name = "";
	private Vector3[] vertices = new Vector3[0];
	private Face[] faces = new Face[0];
	private Vector3 position = Vector3.ZERO;
	private Vector3 rotation = Vector3.ZERO;

	public Mesh() {}

	public Mesh(String name) {
		setName(name);
	}

	public Mesh(Vector3[] vertices) {
		setVertices(vertices);
	}

	public Mesh(String name, Vector3[] vertices) {
		setName(name);
		setVertices(vertices);
	}

	public Mesh(Vector3[] vertices, Face[] faces) {
		setVertices(vertices);
		setFaces(faces);
	}

	public Mesh(String name, Vector3[] vertices, Face[] faces) {
		setName(name);
		setVertices(vertices);
		setFaces(faces);
	}

	public void setName(String name) {
		this.name = Objects.requireNonNull(name, "Name must not be null.");
	}

	public String getName() {
		return name;
	}

	public void setVertices(Vector3[] vertices) {
		Objects.requireNonNull(vertices, "Vertices must not be null.");
		for (Vector3 vertex : vertices) {
			Objects.requireNonNull(vertex, "Vertices must not contain null elements.");
		}
		this.vertices = vertices;
	}

	public int getVertexCount() {
		return vertices.length;
	}

	public Vector3 getVertex(int index) {
		return vertices[checkVertexIndex(index)];
	}

	public void setVertex(int index, Vector3 vertex) {
		vertices[checkVertexIndex(index)] = Objects.requireNonNull(vertex, "Vertex must not be null.");
	}

	public void setFaces(Face[] faces) {
		Objects.requireNonNull(faces, "Faces must not be null.");
		for (Face face : faces) {
			Objects.requireNonNull(face, "Faces must not contain null elements.");
		}
		this.faces = faces;
	}

	public int getFaceCount() {
		return faces.length;
	}

	public Face getFace(int index) {
		return faces[checkFaceIndex(index)];
	}

	public void setFace(int index, Face face) {
		faces[checkFaceIndex(index)] = Objects.requireNonNull(face, "Face must not be null.");
	}

	private int checkVertexIndex(int index) {
		return checkIndex(index, getVertexCount());
	}

	private int checkFaceIndex(int index) {
		return checkIndex(index, getFaceCount());
	}

	private int checkIndex(int index, int count) {
		if (index < 0 || index >= count) {
			throw new IllegalArgumentException("Index must be in Range [0, " + count + "[, but was " + index
				+ ".");
		}
		return index;
	}

	public Vector3 getPosition() {
		return position;
	}

	public void setPosition(Vector3 position) {
		this.position = Objects.requireNonNull(position, "Position must not be null.");
	}

	public void translate(Vector3 offset) {
		Objects.requireNonNull(offset, "Offset must not be null.");
		position = position.plus(offset);
	}

	public Vector3 getRotation() {
		return rotation;
	}

	public void setRotation(Vector3 rotation) {
		this.rotation = Objects.requireNonNull(rotation, "Rotation must not be null.");
	}

	public void rotate(Vector3 offset) {
		Objects.requireNonNull(offset, "Offset must not be null.");
		rotation = rotation.plus(offset);
	}

	public Matrix4 getTransformationMatrix() {
		Matrix4 translationMatrix = createTranslationMatrix(position);
		Matrix4 rotationMatrix = createRotationMatrixFromEulerAngles(rotation);
		return translationMatrix.multiply(rotationMatrix);
	}

}
