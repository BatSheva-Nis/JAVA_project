package unittests;

import static org.junit.jupiter.api.Assertions.*;

import static java.lang.System.out;
import static primitives.Util.isZero;
import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

/**
 * Unit tests for primitives.Vector class
 * @author Batsheva Nissim and Yael Kahana
 *
 */
class VectorTests {
	Vector v1 = new Vector(1, 2, 3);
	Vector v2 = new Vector(-2, -4, -6);
	Vector v3 = new Vector(0, 3, -2);
	
	Point p1 = new Point(1, 2, 3);
	
	/** Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}. */
	@Test
	 public void testConstructor() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: Create vector 0
		assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0), "ERROR: zero vector throws wrong exception or throws wrong exception");
	}

	
	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	void testLengthSquared() {
	    // ============ Equivalence Partitions Tests ==============   
	    // TC01: A test that checks if the function gives the desired result
	    assertEquals(v1.lengthSquared(), 14, 0.00001, "ERROR: lengthSquared() wrong value");
	}
	
	
	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	void testLength() {
		Vector v = new Vector(0, 3, 4);
	    // ============ Equivalence Partitions Tests ==============
		// TC01: A test that checks if the function gives the desired result
	    assertEquals(v.length(), 5, 0.00001, "ERROR: length() wrong value");  
	}
	
	
	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	void testAdd() {
		// ============ Equivalence Partitions Tests ==============
		//TC01: Test of vector + vector
		assertEquals(v1.add(v2), new Vector(-1, -2, -3), "ERROR: Point - Point does not work correctly");
		// =============== Boundary Values Tests ==================
		//TC11: Test if the connection of a vector with the opposite vector is equal to 0 and throws an exception
		assertThrows(IllegalArgumentException.class,() -> v1.add(new Vector(-1, -2, -3)),"ERROR: Vector + -itself does not throw an exception");
	}

	
	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	void testScale() {
	    // ============ Equivalence Partitions Tests ==============
	    // TC01:Checking that scalar multiplication works properly
		assertEquals(v1.scale(2), new Vector(2, 4, 6), "ERROR: scalar * Vector does not work correctly");
		assertEquals(v1.scale(-2), new Vector(-2, -4, -6), "ERROR: scalar * Vector does not work correctly with negatine scalar");
	    // =============== Boundary Values Tests ==================
		//TC11:Checking edge case of scalar = 0
		 assertThrows(IllegalArgumentException.class, () -> v1.scale(0), "scale() with acalar = 0 does not throw an exception");
	}

	
	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	void testDotProduct() {
	    // ============ Equivalence Partitions Tests ==============
	    // TC01: Test if a scalar product of 2 vectors gives a correct result
		assertEquals(v1.dotProduct(v2), -28, 0.00001, "ERROR: dotProduct() wrong value");
		// =============== Boundary Values Tests ==================
		// TC11: Test on scalar product of 2 perpendicular vectors
		assertEquals(v1.dotProduct(v3),0 ,0.000001 , "ERROR: dotProduct() for orthogonal vectors is not zero");
	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	void testCrossProduct() {
		// ============ Equivalence Partitions Tests ==============
		 Vector vr = v1.crossProduct(v3);
		 // TC01: Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
		 assertEquals(v1.length() * v3.length(), vr.length(), 0.00001, "crossProduct() wrong result length");
		 // TC02: Test cross-product result orthogonality to its operands
		 assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
		 assertTrue(isZero(vr.dotProduct(v2)), "crossProduct() result is not orthogonal to 2nd operand");
		 // =============== Boundary Values Tests ==================
		 // TC11: test zero vector from cross-product of co-lined vectors
		 assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v2),
		 "crossProduct() for parallel vectors does not throw an exception");
	}

	
	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	void testNormalize() {
		// ============ Equivalence Partitions Tests ==============
	    // TC01:Checking that the function normalized the vector properly, 
		// so that the normalized vector will be the unit vector
		Vector u = v1.normalize();
		assertEquals(u.length(), 1, 0.00001, "ERROR: the normalized vector is not a unit vector");
		// TC02: Checking that the normalized vector and the original vector are parallel, 
		// a vector multiplication is equal to 0
		 assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(u),"ERROR: the normalized vector is not parallel to the original one");
	    // =============== Boundary Values Tests ==================
		//TC11: test that the normalized vector is the same as the original vector and not opposite in sign
		 assertEquals(v1.dotProduct(u), 0, v1.length(),"ERROR: the normalized vector is opposite to the original one");
	}
	
}
