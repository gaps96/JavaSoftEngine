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

	private void drawLine(Vector3 one, Vector3 two, Matrix4 transform) {
		Vector4 oneClippedCoordinates = transform.multiply(Vector4.positionOf(one));
		Vector4 oneNormalizedDeviceCoordinates = Vector4.perspectiveDivide(oneClippedCoordinates);
		int screenX1 = projectX(oneNormalizedDeviceCoordinates.getX());
		int screenY1 = projectY(oneNormalizedDeviceCoordinates.getY());
		Vector4 twoClippedCoordinates = transform.multiply(Vector4.positionOf(two));
		Vector4 twoNormalizedDeviceCoordinates = Vector4.perspectiveDivide(twoClippedCoordinates);
		int screenX2 = projectX(twoNormalizedDeviceCoordinates.getX());
		int screenY2 = projectY(twoNormalizedDeviceCoordinates.getY());
		drawLine(screenX1, screenY1, screenX2, screenY2);
	}

	private int projectX(double x) {
		return (int) ((1 + x) * surface.getWidth() * 0.5);
	}

	private int projectY(double y) {
		return (int) ((1 - y) * surface.getHeight() * 0.5);
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

	public void setPixel(int x, int y) {
		surface.setPixel(x, y, color);
	}

}
