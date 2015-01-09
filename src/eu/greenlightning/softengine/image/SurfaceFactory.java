package eu.greenlightning.softengine.image;

public interface SurfaceFactory<S extends Surface> {

	S createSurface(int width, int height);

}
