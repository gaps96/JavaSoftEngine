package eu.greenlightning.softengine.math;

import static org.junit.Assert.*;

import org.junit.*;

public class Vector4Test {

	private Vector4 oneTwoThreeFour;
	private Vector4 fiveSixSevenEight;
	private Vector4 eightSevenSixFive;

	@Before
	public void createVectors() {
		oneTwoThreeFour = new Vector4(1, 2, 3, 4);
		fiveSixSevenEight = new Vector4(5, 6, 7, 8);
		eightSevenSixFive = new Vector4(8, 7, 6, 5);
	}

	@After
	public void checkVectorsDidNotChange() {
		assertVector4(oneTwoThreeFour, 1, 2, 3, 4);
		assertVector4(fiveSixSevenEight, 5, 6, 7, 8);
		assertVector4(eightSevenSixFive, 8, 7, 6, 5);
	}

	@Test
	public void positionOf() {
		assertVector4(Vector4.positionOf(new Vector3(4, 3, 2)), 4, 3, 2, 1);
	}

	@Test
	public void perspectiveDivide() {
		assertVector4(Vector4.perspectiveDivide(oneTwoThreeFour), 0.25, 0.5, 0.75, 1);
	}

	@Test
	public void perspectiveDivideByZero() {
		assertVector4(Vector4.perspectiveDivide(new Vector4(1, 2, 3, 0)), 1, 2, 3, 0);
	}

	@Test(expected = NullPointerException.class)
	public void perspectiveDivideNull() {
		Vector4.perspectiveDivide(null);
	}

	@Test
	public void plusVector() {
		Vector4 result = oneTwoThreeFour.plus(fiveSixSevenEight);
		assertVector4(result, 6, 8, 10, 12);
	}

	@Test(expected = NullPointerException.class)
	public void plusNull() {
		oneTwoThreeFour.plus(null);
	}

	@Test
	public void plus() {
		Vector4 result = oneTwoThreeFour.plus(5, 6, 7, 8);
		assertVector4(result, 6, 8, 10, 12);
	}

	@Test
	public void minusVector() {
		Vector4 result = oneTwoThreeFour.minus(eightSevenSixFive);
		assertVector4(result, -7, -5, -3, -1);
	}

	@Test(expected = NullPointerException.class)
	public void minusNull() {
		oneTwoThreeFour.minus(null);
	}

	@Test
	public void minus() {
		Vector4 result = oneTwoThreeFour.minus(8, 7, 6, 5);
		assertVector4(result, -7, -5, -3, -1);
	}

	@Test
	public void scalar() {
		assertEquals(70, oneTwoThreeFour.scalar(fiveSixSevenEight), 0);
	}

	@Test(expected = NullPointerException.class)
	public void scalarNull() {
		oneTwoThreeFour.scalar(null);
	}

	private void assertVector4(Vector4 vector, double x, double y, double z, double w) {
		assertEquals(x, vector.getX(), 0);
		assertEquals(y, vector.getY(), 0);
		assertEquals(z, vector.getZ(), 0);
		assertEquals(w, vector.getW(), 0);
	}

}
