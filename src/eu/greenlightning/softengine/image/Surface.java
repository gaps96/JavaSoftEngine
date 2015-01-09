package eu.greenlightning.softengine.image;

public interface Surface {

	int getWidth();
	int getHeight();
	void setPixel(int x, int y, Color4 color);

}
