package eu.greenlightning.softengine.math;

import static org.junit.Assert.*;

import org.junit.*;

public class Vector2Test {

	private Vector2 oneTwo;
	private Vector2 threeFour;
	private Vector2 fourThree;

	@Before
	public void createVectors() {
		oneTwo = new Vector2(1, 2);
		threeFour = new Vector2(3, 4);
		fourThree = new Vector2(4, 3);
	}

	@After
	public void checkVectorsDidNotChange() {
		assertVector2(oneTwo, 1, 2);
		assertVector2(threeFour, 3, 4);
		assertVector2(fourThree, 4, 3);
	}

	@Test
	public void plusVector() {
		Vector2 result = oneTwo.plus(threeFour);
		assertVector2(result, 4, 6);
	}

	@Test(expected = NullPointerException.class)
	public void plusNull() {
		oneTwo.plus(null);
	}

	@Test
	public void plus() {
		Vector2 result = oneTwo.plus(3, 4);
		assertVector2(result, 4, 6);
	}

	@Test
	public void minusVector() {
		Vector2 result = oneTwo.minus(fourThree);
		assertVector2(result, -3, -1);
	}

	@Test(expected = NullPointerException.class)
	public void minusNull() {
		oneTwo.minus(null);
	}

	@Test
	public void minus() {
		Vector2 result = oneTwo.minus(4, 3);
		assertVector2(result, -3, -1);
	}

	@Test
	public void scale() {
		assertVector2(oneTwo.multiply(-2), -2, -4);
	}

	private void assertVector2(Vector2 vector, double x, double y) {
		assertEquals(x, vector.getX(), 0);
		assertEquals(y, vector.getY(), 0);
	}

}
