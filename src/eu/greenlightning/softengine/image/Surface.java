package eu.greenlightning.softengine.image;

import java.awt.Image;
import java.awt.image.BufferedImage;

public interface Surface {

	int getWidth();
	int getHeight();
	void setPixel(int x, int y, Color4 color);
	void setPixel(int x, int y, float z, Color4 color);
	public Image getImage();
	public void resetDepth();

}
