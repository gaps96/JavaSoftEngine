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

	public void render (Camera camera, Mesh mesh) {
		Matrix4 transform = getTransform(camera, mesh);
		for (int index = 0; index < mesh.getFaceCount(); index++) {
			Face face = mesh.getFace(index);
			drawFaceEdges(mesh, face, transform);
		}
	}
	
	public void raster (Camera camera, Mesh mesh) {
		Matrix4 transform = getTransform(camera, mesh);
		
		for (int index = 0; index < mesh.getFaceCount(); index++) {
			Face face = mesh.getFace(index);
		    float colorIndex = 0.25f + (index % mesh.getFaceCount()) * 0.75f / mesh.getFaceCount();		    
		    setColor(new Color4(colorIndex, colorIndex, colorIndex, 1));
		    drawFace(mesh, face, transform);		    
		}
		
	}	
	
	private Matrix4 getTransform(Camera camera, Mesh mesh) {
		Matrix4 projection = camera.getProjectionMatrix(surface.getWidth(), surface.getHeight());
		Matrix4 view = camera.getViewMatrix();
		Matrix4 model = mesh.getTransformationMatrix();

		return projection.multiply(view.multiply(model));
	}	

	private void drawFaceEdges(Mesh mesh, Face face, Matrix4 transform) {
		for (int index = 0; index < face.getIndexCount(); index++) {
			Vector3 current = mesh.getVertex(face.getIndex(index));
			Vector3 next = mesh.getVertex(face.getIndex((index + 1) % face.getIndexCount()));
			drawLine(current, next, transform);
		}
	}

	private void drawFace(Mesh mesh, Face face, Matrix4 transform) {
		for (int index = 0; index < face.getIndexCount(); index++) {
			Vector3 vertexA = mesh.getVertex(face.getIndex(0));
			Vector3 vertexB = mesh.getVertex(face.getIndex(1));
			Vector3 vertexC = mesh.getVertex(face.getIndex(2));

			Vector3 pixelA3 = transform3D(vertexA, transform);
			Vector3 pixelB3 = transform3D(vertexB, transform);
			Vector3 pixelC3 = transform3D(vertexC, transform);

		    drawTriangle(pixelA3, pixelB3, pixelC3);		    			
		}
	}
	
	private void drawLine(Vector3 from, Vector3 to, Matrix4 transform) {
		Vector2 fromScreenCoordinates = transform2D(from, transform);
		Vector2 toScreenCoordinates = transform2D(to, transform);
		drawLine(fromScreenCoordinates, toScreenCoordinates);
	}

	private Vector2 transform2D (Vector3 vector, Matrix4 transform) {
		Vector4 clippedCoordinates = transform.multiply(Vector4.positionOf(vector));
		Vector4 normalizedDeviceCoordinates = Vector4.perspectiveDivide(clippedCoordinates);
		Vector2 screenCoordinates = project(normalizedDeviceCoordinates);
		return screenCoordinates;
	}
	
	private Vector3 transform3D (Vector3 vector, Matrix4 transform) {
		Vector4 clippedCoordinates = transform.multiply(Vector4.positionOf(vector));
		Vector4 normalizedDeviceCoordinates = Vector4.perspectiveDivide(clippedCoordinates);
		Vector2 screenCoordinates = project(normalizedDeviceCoordinates);
	    return (new Vector3(screenCoordinates.getX(), screenCoordinates.getY(), clippedCoordinates.getZ()));
	}		

	private Vector2 project(Vector4 vector) {
		double x = (1 + vector.getX()) * surface.getWidth() * 0.5;
		double y = (1 - vector.getY()) * surface.getHeight() * 0.5;
		return new Vector2(x, y);
	}

	public void clear() {
		fillRect(0, 0, surface.getWidth(), surface.getHeight());
		surface.resetDepth();
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
	
	public void drawTriangle(Vector3 p1, Vector3 p2, Vector3 p3)
	{
	    // Sorting the points in order to always have this order on screen p1, p2 & p3
	    // with p1 always up (thus having the.getY() the lowest possible to be near the top screen)
	    // then p2 between p1 & p3
	    if (p1.getY() > p2.getY())
	    {
	    	Vector3 temp = p2;
	        p2 = p1;
	        p1 = temp;
	    }

	    if (p2.getY() > p3.getY())
	    {
	    	Vector3 temp = p2;
	        p2 = p3;
	        p3 = temp;
	    }

	    if (p1.getY() > p2.getY())
	    {
	    	Vector3 temp = p2;
	        p2 = p1;
	        p1 = temp;
	    }

	    // inverse slopes
	    double dP1P2 = 0; 
	    double dP1P3 = 0;

	    // http://en.wikipedia.org/wiki/Slope
	    // Computing inverse slopes
	    if (p2.getY() - p1.getY() > 0)
	        dP1P2 = (p2.getX() - p1.getX()) / (p2.getY() - p1.getY());
	    else
	        dP1P2 = 0;

	    if (p3.getY() - p1.getY() > 0)
	        dP1P3 = (p3.getX() - p1.getX()) / (p3.getY() - p1.getY());
	    else
	        dP1P3 = 0;

	    // First case where triangles are like that:
	    // P1
	    // | \
	    // |  P2
	    // | /
	    // P3
	    if (dP1P2 > dP1P3)
	    {
	        for (int y = (int)p1.getY(); y <= (int)p3.getY(); y++)
	        {
	            if (y < p2.getY())
	            {
	                processScanLine(y, p1, p3, p1, p2);
	            }
	            else
	            {
	                processScanLine(y, p1, p3, p2, p3);
	            }
	        }
	    }
	    
	    // Second case where triangles are like that:
	    //    P1
	    //   / | 
	    // P2  | 
	    //   \ |
	    //    P3
	    else
	    {
	        for (int y = (int)p1.getY(); y <= (int)p3.getY(); y++)
	        {
	            if (y < p2.getY())
	            {
	                processScanLine(y, p1, p2, p1, p3);
	            }
	            else
	            {
	                processScanLine(y, p2, p3, p1, p3);
	            }
	        }
	    }
	}			

	// drawing line between 2 points from left to right
	// papb -> pcpd
	// pa, pb, pc, pd must then be sorted before
	// with z depth
	private void processScanLine(int y, Vector3 pa, Vector3 pb, Vector3 pc, Vector3 pd) {
	    // Thanks to current Y, we can compute the gradient to compute others values like
	    // the starting X (sx) and ending X (ex) to draw between
	    // if pa.Y == pb.Y or pc.Y == pd.Y, gradient is forced to 1
	    double gradient1 = pa.getY() != pb.getY() ? (y - pa.getY()) / (pb.getY() - pa.getY()) : 1;
	    double gradient2 = pc.getY() != pd.getY() ? (y - pc.getY()) / (pd.getY() - pc.getY()) : 1;
	            
	    int sx = (int)interpolate(pa.getX(), pb.getX(), gradient1);
	    int ex = (int)interpolate(pc.getX(), pd.getX(), gradient2);

	    // starting Z & ending Z
	    float z1 = (float)interpolate(pa.getZ(), pb.getZ(), gradient1);
	    float z2 = (float)interpolate(pc.getZ(), pd.getZ(), gradient2);
	    
	    // drawing a line from left (sx) to right (ex) 
	    for (int x = sx; x < ex; x++) {
	        float gradient = (x - sx) / (float)(ex - sx);
	        float z = (float)interpolate(z1, z2, gradient);
	    	this.setPixel(x, y, z);
	    }
	}

	// Clamping values to keep them between 0 and 1
	private double clamp(double value, double min, double max) {
	    return Math.max(min, Math.min(value, max));
	}

	// Interpolating the value between 2 vertices 
	// min is the starting point, max the ending point
	// and gradient the % between the 2 points
	private double interpolate(double min, double max, double gradient) {
	    return min + (max - min) * clamp(gradient,0d,1d);
	}

	public void setPixel(Vector2 pixel) {
		setPixel((int) pixel.getX(), (int) pixel.getY());
	}

	public void setPixel(int x, int y) {
		surface.setPixel(x, y, color);
	}

	public void setPixel(int x, int y, float z) {
		surface.setPixel(x, y, z, color);
	}
	
}
