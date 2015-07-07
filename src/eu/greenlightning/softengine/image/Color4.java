package eu.greenlightning.softengine.image;

public final class Color4 {

	public static final Color4 TRANSPARENT = new Color4(0, 0, 0, 0);
	public static final Color4 BLACK = new Color4(0, 0, 0);
	public static final Color4 WHITE = new Color4(255, 255, 255);

	public static final Color4 RED = new Color4(255, 0, 0);
	public static final Color4 GREEN = new Color4(0, 255, 0);
	public static final Color4 BLUE = new Color4(0, 0, 255);

	public static final Color4 YELLOW = new Color4(255, 255, 0);
	public static final Color4 CYAN = new Color4(0, 255, 255);
	public static final Color4 MAGENTA = new Color4(255, 0, 255);

	private final int value;

	public Color4(double red, double green, double blue) {
		this((int) (red * 255), (int) (green * 255), (int) (blue * 255));
	}

	public Color4(double red, double green, double blue, double alpha) {
		this((int) (red * 255), (int) (green * 255), (int) (blue * 255), (int) (alpha * 255));
	}

	public Color4(int red, int green, int blue) {
		this(red, green, blue, 255);
	}

	public Color4(int red, int green, int blue, int alpha) {
		//@formatter:off
		value = ((alpha & 0xff) << 24) |
				((red   & 0xff) << 16) |
				((green & 0xff) <<  8) |
			    ((blue  & 0xff) <<  0);
		//@formatter:on
	}

	public int getAlpha() {
		return value >> 24;
	}

	public double getAlphaPercentage() {
		return getAlpha() / 255d;
	}

	public int getRed() {
		return (value >> 8) & 0xff;
	}

	public double getRedPercentage() {
		return getRed() / 255d;
	}

	public int getGreen() {
		return value & 0xff;
	}

	public double getGreenPercentage() {
		return getGreen() / 255d;
	}

	public int getBlue() {
		return (value >> 16) & 0xff;
	}

	public double getBluePercentage() {
		return getBlue() / 255d;
	}

	public int getARGB() {
		return value;
	}
	
	public static Color4 parseInt(int color) {
		return new Color4((color >> 8) & 0xff, color & 0xff, (color >> 16) & 0xff);
	}

	@Override
	public String toString() {
		double r = getRedPercentage() * 100;
		double g = getGreenPercentage() * 100;
		double b = getBluePercentage() * 100;
		double a = getAlphaPercentage() * 100;
		return String.format("Color4(r: %f,g: %f, b: %f, a: %f)", r, b, g, a);
	}

}
