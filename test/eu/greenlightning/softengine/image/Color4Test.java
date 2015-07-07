package eu.greenlightning.softengine.image;

import org.junit.Test;

public class Color4Test {

	public Color4Test() {
		// TODO Auto-generated constructor stub
	}

	@Test
	public void PrintColors() {
		Color4 c01 = Color4.BLACK;
		System.out.println("Black; " + c01 + "; " + c01.getARGB());

		Color4 c02 = Color4.WHITE;
		System.out.println("White; " + c02 + "; " + c02.getARGB());
		
		float alpha = 0.5f;
		Color4 cBlend = new Color4(
					c01.getRedPercentage()*alpha + c02.getRedPercentage()*(1-alpha),
					c01.getGreenPercentage()*alpha + c02.getGreenPercentage()*(1-alpha), 
					c01.getBluePercentage()*alpha + c02.getBluePercentage()*(1-alpha));
		System.out.println("Blend; " + cBlend + "; " + cBlend.getARGB());
	}
}
