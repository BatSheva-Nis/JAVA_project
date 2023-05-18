/**
 * 
 */
package unittests;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

import primitives.*;
import geometries.*;

import org.junit.jupiter.api.Test;

import primitives.Ray;

/**
 *  Testing Tube
 * @author Batsheva Nissim and Yael Kahana
 *
 */
class TubeTests {

	/**
	 * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		//Point p0 = new Point(1, 1, 1);
		//Point p = new Point(2, 1, 1);
		//Point p1 = new Point(5, 1, 1);
		//Vector v = new Vector(1, 0, 0);
	    // TC01: There is a simple single test here - using a tube
	    //Tube tb = new Tube(new Ray(p0,v), 1.0);
	    // ensure there are no exceptions
	    //assertDoesNotThrow(() -> tb.getNormal(p), "");
	    // generate the test result
	    //Vector result = tb.getNormal(p);
	    // ensure |result| = 1
	    //assertEquals(1, result.length(), 0.00000001, "Tube's normal is not a unit vector");
	    // ensure the result is orthogonal to some point on the tube
	    //assertTrue(isZero(result.dotProduct(p0.subtract(p))),"Tube's normal is not orthogonal to one of the edges");
		// =============== Boundary Values Tests ==================
	    //TC11: Test vector p-p0 is orthogonal to v
	    //assertThrows(IllegalArgumentException.class,() -> v.crossProduct(p1.subtract(p)), "ERROR: vector p-p0 is orthogonal to v");
	}

}
