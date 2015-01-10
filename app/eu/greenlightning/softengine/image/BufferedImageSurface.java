package eu.greenlightning.softengine.image;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;

public class BufferedImageSurface implements Surface {

	public static final SurfaceFactory<BufferedImageSurface> FACTORY = new SurfaceFactory<BufferedImageSurface>() {
		@Override
		public BufferedImageSurface createSurface(int width, int height) {
			return new BufferedImageSurface(width, height);
		}
	};

	private final int width;
	private final int height;
	private BufferedImage image;
	private DataBuffer colorBuffer;

	private BufferedImageSurface(int width, int height) {
		this.width = validateSize(width, "Width");
		this.height = validateSize(height, "Height");
		createImage();
	}

	private int validateSize(int size, String description) {
		if (size < 0) {
			throw new IllegalArgumentException(description + " must not be negative.");
		}
		return size;
	}

	private void createImage() {
		// BufferedImage requires a minimum size of 1 x 1 pixels.
		image = new BufferedImage(Math.max(width, 1), Math.max(height, 1), BufferedImage.TYPE_INT_ARGB);
		colorBuffer = image.getRaster().getDataBuffer();
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	public BufferedImage getImage() {
		return image;
	}

	@Override
	public void setPixel(int x, int y, Color4 color) {
		if (x >= 0 && x < width && y >= 0 && y < height) {
			colorBuffer.setElem(y * width + x, color.getARGB());
		}
	}

}
