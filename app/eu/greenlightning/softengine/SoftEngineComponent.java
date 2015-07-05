package eu.greenlightning.softengine;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;

import eu.greenlightning.softengine.application.Application;
import eu.greenlightning.softengine.application.SwingApplicationTimer;
import eu.greenlightning.softengine.geometry.Mesh;
import eu.greenlightning.softengine.geometry.MeshFactory;
import eu.greenlightning.softengine.image.*;
import eu.greenlightning.softengine.math.Vector3;
import eu.greenlightning.softengine.view.PerspectiveCamera;

public class SoftEngineComponent extends JComponent implements KeyListener, Application {

	private SurfaceManager<BufferedImageSurface> manager;
	private Renderer<BufferedImageSurface> renderer;
	private Mesh cube;
	private PerspectiveCamera camera;

	private boolean autoRotate = true;
	private boolean xUp, xDown, yUp, yDown, zUp, zDown;
	private int renderMode = 0;

	public SoftEngineComponent() {
		manager = new SurfaceManager<>(BufferedImageSurface.FACTORY, getWidth(), getHeight());
		renderer = manager.getRenderer();

		cube = MeshFactory.createCube(2);
		cube.setName("Cube");

		camera = new PerspectiveCamera();
		camera.position = new Vector3(0, 0, 4);

		setFocusable(true);
		addKeyListener(this);

		new SwingApplicationTimer(this);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			autoRotate = !autoRotate;
		} else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			cube.setRotation(Vector3.ZERO);
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			xUp = true;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			xDown = true;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			yUp = true;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			yDown = true;
		} else if (e.getKeyCode() == KeyEvent.VK_PAGE_UP) {
			zUp = true;
		} else if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
			zDown = true;
		} else if (e.getKeyCode() == KeyEvent.VK_R) {
			renderMode = ++renderMode % 2;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			xUp = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			xDown = false;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			yUp = false;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			yDown = false;
		} else if (e.getKeyCode() == KeyEvent.VK_PAGE_UP) {
			zUp = false;
		} else if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
			zDown = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void updateAndRender(double delta) {
		manager.resize(getWidth(), getHeight());
		renderer.setColor(Color4.BLACK);
		renderer.clear();
		renderer.setColor(Color4.WHITE);
		Vector3 rotationSpeed = Vector3.ZERO;
		if (autoRotate) {
			rotationSpeed = rotationSpeed.plus(new Vector3(0.5, 1, 0));
		}
		if (xUp) {
			rotationSpeed = rotationSpeed.plus(Vector3.UNIT_X);
		}
		if (xDown) {
			rotationSpeed = rotationSpeed.minus(Vector3.UNIT_X);
		}
		if (yUp) {
			rotationSpeed = rotationSpeed.plus(Vector3.UNIT_Y);
		}
		if (yDown) {
			rotationSpeed = rotationSpeed.minus(Vector3.UNIT_Y);
		}
		if (zUp) {
			rotationSpeed = rotationSpeed.plus(Vector3.UNIT_Z);
		}
		if (zDown) {
			rotationSpeed = rotationSpeed.minus(Vector3.UNIT_Z);
		}
		cube.rotate(rotationSpeed.multiply(delta));
		switch(renderMode) {		
			case 0: renderer.render(camera, cube); break;
			case 1: renderer.raster(camera, cube); break;
		}
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(manager.getSurface().getImage(), 0, 0, null);
	}

}
