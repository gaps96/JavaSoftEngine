package eu.greenlightning.softengine.image;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferFloat;
import java.util.Arrays;

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
	private DataBufferFloat depthBuffer;

	private BufferedImageSurface(int width, int height) {
		this.width = validateSize(width, "Width");
		this.height = validateSize(height, "Height");
		createImage();
		resetDepth();
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
		depthBuffer = new DataBufferFloat(colorBuffer.getSize());
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

	public void resetDepth() {
		Arrays.fill(depthBuffer.getBankData()[0], Float.MAX_VALUE);
	}	
	
	@Override
	public void setPixel(int x, int y, Color4 color) {
		if (x >= 0 && x < width && y >= 0 && y < height) {
			colorBuffer.setElem(y * width + x, color.getARGB());
		}
	}
	
	// Called to put a pixel on screen at a specific X,Y,Z coordinates
	public void setPixel(int x, int y, float z, Color4 color) {
	    // As we have a 1-D Array for our back buffer
	    // we need to know the equivalent cell in 1-D based
	    // on the 2D coordinates on screen
	    int index = (y * width + x);

	    if (depthBuffer.getElemFloat(index) < z) {
	        return; // Discard
	    }

	    if (x >= 0 && x < width && y >= 0 && y < height) {
	    	depthBuffer.setElemFloat(index, z);	    	
			colorBuffer.setElem(y * width + x, color.getARGB());		    
		}
	}		

}
