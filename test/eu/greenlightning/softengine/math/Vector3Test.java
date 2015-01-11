package eu.greenlightning.softengine.math;

import static org.junit.Assert.*;

import org.junit.*;

public class Vector3Test {

	private Vector3 oneTwoThree;
	private Vector3 fourFiveSix;
	private Vector3 sixFiveFour;

	@Before
	public void createVectors() {
		oneTwoThree = new Vector3(1, 2, 3);
		fourFiveSix = new Vector3(4, 5, 6);
		sixFiveFour = new Vector3(6, 5, 4);
	}

	@After
	public void checkVectorsDidNotChange() {
		assertVector3(oneTwoThree, 1, 2, 3);
		assertVector3(fourFiveSix, 4, 5, 6);
		assertVector3(sixFiveFour, 6, 5, 4);
	}

	@Test
	public void plusVector() {
		Vector3 result = oneTwoThree.plus(fourFiveSix);
		assertVector3(result, 5, 7, 9);
	}

	@Test(expected = NullPointerException.class)
	public void plusNull() {
		oneTwoThree.plus(null);
	}

	@Test
	public void plus() {
		Vector3 result = oneTwoThree.plus(4, 5, 6);
		assertVector3(result, 5, 7, 9);
	}

	@Test
	public void minusVector() {
		Vector3 result = oneTwoThree.minus(sixFiveFour);
		assertVector3(result, -5, -3, -1);
	}

	@Test(expected = NullPointerException.class)
	public void minusNull() {
		oneTwoThree.minus(null);
	}

	@Test
	public void minus() {
		Vector3 result = oneTwoThree.minus(6, 5, 4);
		assertVector3(result, -5, -3, -1);
	}

	@Test
	public void negate() {
		assertVector3(oneTwoThree.negate(), -1, -2, -3);
	}

	@Test
	public void normalize() {
		Vector3 vector = new Vector3(1, 2, 2);

		Vector3 normalized = vector.normalize();

		assertVector3(vector, 1, 2, 2);
		assertVector3(normalized, 1.0 / 3.0, 2.0 / 3.0, 2.0 / 3.0);
	}

	@Test
	public void normalizeZero() {
		Vector3 normalized = Vector3.ZERO.normalize();
		assertVector3(normalized, 0, 0, 0);
	}

	@Test
	public void length() {
		assertEquals(Math.sqrt(14), oneTwoThree.length(), 0);
	}

	@Test
	public void multiply() {
		assertVector3(oneTwoThree.multiply(-2), -2, -4, -6);
	}

	@Test
	public void scalar() {
		assertEquals(32, oneTwoThree.scalar(fourFiveSix), 0);
	}

	@Test(expected = NullPointerException.class)
	public void scalarNull() {
		oneTwoThree.scalar(null);
	}

	@Test
	public void cross() {
		assertVector3(oneTwoThree.cross(fourFiveSix), -3, 6, -3);
		assertVector3(fourFiveSix.cross(oneTwoThree), 3, -6, 3);
		assertVector3(oneTwoThree.cross(Vector3.ZERO), 0, 0, 0);
	}

	@Test(expected = NullPointerException.class)
	public void crossNull() {
		oneTwoThree.cross(null);
	}

	private void assertVector3(Vector3 vector, double x, double y, double z) {
		assertEquals(x, vector.getX(), 0);
		assertEquals(y, vector.getY(), 0);
		assertEquals(z, vector.getZ(), 0);
	}

}
