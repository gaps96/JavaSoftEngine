package eu.greenlightning.softengine;

import java.util.Objects;

import eu.greenlightning.softengine.geometry.Face;
import eu.greenlightning.softengine.geometry.Mesh;
import eu.greenlightning.softengine.image.Color4;
import eu.greenlightning.softengine.image.Surface;
import eu.greenlightning.softengine.math.*;
import eu.greenlightning.softengine.view.Camera;

public class Renderer<S extends Surface> {

	private S surface;
	private Color4 color = Color4.WHITE;

	public Renderer(S surface) {
		setSurface(surface);
	}

	public S getSurface() {
		return surface;
	}

	public void setSurface(S surface) {
		this.surface = Objects.requireNonNull(surface, "Surface must not be null.");
	}

	public Color4 getColor() {
		return color;
	}

	public void setColor(Color4 color) {
		this.color = Objects.requireNonNull(color, "Color must not be null.");
	}

	public void render(Camera camera, Mesh mesh) {
		Matrix4 projection = camera.getProjectionMatrix(surface.getWidth(), surface.getHeight());
		Matrix4 view = camera.getViewMatrix();
		Matrix4 model = mesh.getTransformationMatrix();

		Matrix4 transform = projection.multiply(view.multiply(model));

		for (int index = 0; index < mesh.getFaceCount(); index++) {
			Face face = mesh.getFace(index);
			drawFace(mesh, face, transform);
		}
	}

	private void drawFace(Mesh mesh, Face face, Matrix4 transform) {
		for (int index = 0; index < face.getIndexCount(); index++) {
			Vector3 current = mesh.getVertex(face.getIndex(index));
			Vector3 next = mesh.getVertex(face.getIndex((index + 1) % face.getIndexCount()));
			drawLine(current, next, transform);
		}
	}

	private void drawLine(Vector3 from, Vector3 to, Matrix4 transform) {
		Vector2 fromScreenCoordinates = transform(from, transform);
		Vector2 toScreenCoordinates = transform(to, transform);
		drawLine(fromScreenCoordinates, toScreenCoordinates);
	}

	private Vector2 transform(Vector3 vector, Matrix4 transform) {
		Vector4 clippedCoordinates = transform.multiply(Vector4.positionOf(vector));
		Vector4 normalizedDeviceCoordinates = Vector4.perspectiveDivide(clippedCoordinates);
		Vector2 screenCoordinates = project(normalizedDeviceCoordinates);
		return screenCoordinates;
	}

	private Vector2 project(Vector4 vector) {
		double x = (1 + vector.getX()) * surface.getWidth() * 0.5;
		double y = (1 - vector.getY()) * surface.getHeight() * 0.5;
		return new Vector2(x, y);
	}

	public void clear() {
		fillRect(0, 0, surface.getWidth(), surface.getHeight());
	}

	public void fillRect(int x, int y, int width, int height) {
		for (int currentX = x; currentX < x + width; currentX++) {
			for (int currentY = y; currentY < y + height; currentY++) {
				setPixel(currentX, currentY);
			}
		}
	}

	public void drawLine(Vector2 from, Vector2 to) {
		drawLine((int) from.getX(), (int) from.getY(), (int) to.getX(), (int) to.getY());
	}

	public void drawLine(int x0, int y0, int x1, int y1) {
		int dx = Math.abs(x1 - x0);
		int dy = Math.abs(y1 - y0);
		int sx = (x0 < x1) ? 1 : -1;
		int sy = (y0 < y1) ? 1 : -1;
		int err = dx - dy;

		setPixel(x0, y0);
		while ((x0 != x1) || (y0 != y1)) {
			int err2 = 2 * err;
			if (err2 > -dy) {
				err -= dy;
				x0 += sx;
			}
			if (err2 < dx) {
				err += dx;
				y0 += sy;
			}
			setPixel(x0, y0);
		}
	}

	public void setPixel(Vector2 pixel) {
		setPixel((int) pixel.getX(), (int) pixel.getY());
	}

	public void setPixel(int x, int y) {
		surface.setPixel(x, y, color);
	}

}
