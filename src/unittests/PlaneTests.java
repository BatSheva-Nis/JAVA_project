/**
 *  Testing Plane
 * @author Batsheva Nissim and Yael Kahana
 *
 */
package unittests;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

import java.util.List;

//import java.awt.Point;

import org.junit.jupiter.api.Test;

import primitives.*;
import geometries.*;

class PlaneTests {
	/** Test method for {@link geometries.Plane#getNormal(primitives.Point)}. */
	@Test
	void testGetNormal() {
	    // ============ Equivalence Partitions Tests ==============
	    // TC01 simple test
	    Point p1=new Point(1,0,0);
	    Point p2=new Point(0,1,0);
	    Point p3=new Point(0,0,0);
	    Plane pl= new Plane(p1,p2, p3);
	    // ensure there are no exceptions
	    assertDoesNotThrow(() -> pl.getNormal(new Point(1,1,0)), "");
	    // generate the test result
	    Vector result = pl.getNormal(new Point(1,1,0));
	    // ensure |result| = 1
	    assertEquals(1, result.length(), 0.00000001, "Plane's normal is not a unit vector");
	    // ensure the result is orthogonal to all the edges
	    assertTrue(isZero(result.dotProduct(p1.subtract(p2))), "Plane's normal is not orthogonal to one of the Points");
	    assertTrue(isZero(result.dotProduct(p2.subtract(p3))), "Plane's normal is not orthogonal to one of the Points");
	    assertTrue(isZero(result.dotProduct(p3.subtract(p1))), "Plane's normal is not orthogonal to one of the Points");

	}
	/**
	 * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
	 */
	 @Test
	 void testFindIntersections() {
	     // Create a plane for testing
	     Plane plane = new Plane(new Point(0, 0, 1), new Vector(0, 0, 1));
	     
	     // ============ Equivalence Partitions Tests ==============
	     // The ray crosses the plane
	     Ray ray1 = new Ray(new Point(0, 0, -1), new Vector(0, 0, 1));
	     assertEquals(List.of(new Point(0, 0, 1)), plane.findIntersections(ray1),
	            "Wrong intersection point when the ray crosses the plane");
	     
	     // The ray does not cross the plane
	     Ray ray2 = new Ray(new Point(0, 0, -1), new Vector(0, 1, 0));
	     assertNull(plane.findIntersections(ray2),
	             "Wrong intersection point when the ray doesn't cross the plane");
	     
	     // =============== Boundary Values Tests ==================
	     // Group 1 - the ray is parallel to the plane (2):
	     //The ray is contained in a plane
	     Ray ray3 = new Ray(new Point(0, 0, 1), new Vector(0, 0, 1));
	     try {
	    	 plane.findIntersections(ray3);}
	     catch (IllegalArgumentException exception){}
	     
	      //The ray is not contained in the plane
	     Ray ray4 = new Ray(new Point(0, 0, -1), new Vector(0, 0, -1));
	     assertNull(plane.findIntersections(ray4),
	             "Wrong intersection point when the ray is parallel to the plane and doesn't intersect it");

	     // Group 2- the beam is perpendicular to the plane (3), according to P0:
	     // The ray before the plane
	     Ray ray5 = new Ray(new Point(0, 0, -1), new Vector(0, 1, 1));
	     assertEquals(List.of(new Point(0, 2, 1)), plane.findIntersections(ray5),
	             "Wrong intersection point when the ray is perpendicular to the plane and starts before it");

	     // The foundation is in the plane
	     Ray ray6 = new Ray(new Point(0, 0, 1), new Vector(0, 1, 0));
	     assertNull(plane.findIntersections(ray6),
	             "Wrong intersection point when the ray is perpendicular to the plane and starts on it");

	     // The ray after the plane
	     Ray ray7 = new Ray(new Point(0, 0, 2), new Vector(0, 1, 0));
	     assertNull(plane.findIntersections(ray7),
	             "Wrong intersection point when the ray is perpendicular to the plane and starts after it");

	     // The ray is not perpendicular/parallel, but is on the plane
	     Ray ray8 = new Ray(new Point(0, 1, 0), new Vector(1, 0, 0));
	     assertNull( plane.findIntersections(ray8),
	             "Wrong intersection point when the ray is on the plane but not perpendicular to it");

	     // The ray is not perpendicular/parallel, but leaves the point of defining the plane
	     Ray ray9 = new Ray(new Point(0, 1, 0), new Vector(1, 1, 0));
	     assertNull(plane.findIntersections(ray9),
	             "Wrong intersection point when the ray is not perpendicular/parallel to the plane and leaves the point of defining the plane");
	 }

	
}
