package eu.greenlightning.softengine.image;

import eu.greenlightning.softengine.Renderer;

public class SurfaceManager<S extends Surface> {

	private final SurfaceFactory<? extends S> factory;
	private final Renderer<S> renderer;

	public SurfaceManager(SurfaceFactory<? extends S> factory, int width, int height) {
		this(factory, new Renderer<S>(factory.createSurface(width, height)));
	}

	public SurfaceManager(SurfaceFactory<? extends S> factory, Renderer<S> renderer) {
		this.factory = factory;
		this.renderer = renderer;
	}

	public Renderer<S> getRenderer() {
		return renderer;
	}

	public S getSurface() {
		return renderer.getSurface();
	}

	public void resize(int width, int height) {
		if (width != getSurface().getWidth() || height != getSurface().getHeight()) {
			createSurface(width, height);
		}
	}

	private void createSurface(int width, int height) {
		renderer.setSurface(factory.createSurface(width, height));
	}

}
