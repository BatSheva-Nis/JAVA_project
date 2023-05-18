package unittests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static java.lang.System.out;
import static primitives.Util.isZero;

import primitives.Point;
import primitives.Vector;


/**
 * Unit tests for primitives.Point class
 * @author Batsheva Nissim and Yael Kahana
 *
 */
class PointTests {
	Vector v1 = new Vector(1, 2, 3);
	Vector v2 = new Vector(-2, -4, -6);
	
	Point p1 = new Point(1, 2, 3);
	Point p2 = new Point(-2, -4, -6);
	/**
	 * Test method for {@link primitives.Point#subtract(primitives.Point)}.
	 */
	@Test
	void testSubtract() {
	    // ============ Equivalence Partitions Tests ==============  
	    // TC01:Test of point - point
		assertEquals(new Point(2, 3, 4).subtract(p1), new Vector(1, 1, 1), "ERROR: ERROR: Point - Point does not work correctly");
	    // =============== Boundary Values Tests ==================
		//TC11: Test subtract point - itself
		assertThrows(IllegalArgumentException.class,() -> p1.subtract(p1),"ERROR: Point - itself does not throw an exception");   
	}
	
	/**
	 * Test method for {@link primitives.Point#add(primitives.Vector)}.
	 */
	@Test
	void testAdd() {
		// ============ Equivalence Partitions Tests ==============
		//TC01: Test of point + vector
		assertEquals(p1.add(v2), new Point(-1, -2, -3), "ERROR: Point - Point does not work correctly");
		// =============== Boundary Values Tests ==================
		//TC11: Test of point + -vector
		assertEquals(p1.add(new Vector(-1, -2, -3)), new Point (0, 0, 0),"ERROR: point + -itself does not throw an exception");
	}
	
	/**
	 * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
	 */
	@Test
	void testDistanceSquared() {
	    // ============ Equivalence Partitions Tests ==============
	    // TC01: Test squared distance between 2 points
		assertEquals(p1.distanceSquared(p2), 126, 0.00001, "ERROR: squared distance between 2 points does not work correctly");
	}
	
	/**
	 * Test method for {@link primitives.Point#distance(primitives.Point)}.
	 */
	@Test
	void testDistance() {
	    // ============ Equivalence Partitions Tests ==============
	    // TC01: Test Distance between 2 points
		assertEquals(p1.distance(p2), Math.sqrt(126), 0.00000001, "ERROR: distance between 2 points does not work correctly");
	}

}
