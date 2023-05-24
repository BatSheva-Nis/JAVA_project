package geometries;

import java.util.*;

import geometries.Intersectable.GeoPoint;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Triangle class represents two-dimensional triangles in 3D coordinate system
 *
 * @author Batsheva Nissim and Yael Kahana and Rachelli Adler
 */
public class Triangle extends Polygon {

    /**
     * constructor to initialize Triangle with 3 points
     *
     * @param point1 first point
     * @param point2 second point
     * @param point3 third point
     */
    public Triangle(Point point1, Point point2, Point point3) {
        super(point1, point2, point3);
    }

	
	/**
	 * the function checks if there are intersection between the ray and the plane
	 *  and if yes returns a list with the intersection
	 * @param ray
	 * @returns list of inersection. 1/0 intersections .The point and the shape the point is on
	 */
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray){
		Point p0=ray.getP0();
		Vector v = ray.getDir();
		
		Point p1 = vertices.get(0);
		Point p2 = vertices.get(1);
		Point p3 = vertices.get(2);
		Vector v1 = p1.subtract(p0);
		Vector v2 = p2.subtract(p0);
		Vector v3 = p3.subtract(p0);
		
		
		Vector n1 = v1.crossProduct(v2);
		Vector n2 = v2.crossProduct(v3);
		Vector n3 = v3.crossProduct(v1);
		
		boolean flag = false;
		if((v.dotProduct(n1) > 0 && v.dotProduct(n2) > 0 && v.dotProduct(n3) > 0 )||(v.dotProduct(n1) < 0 && v.dotProduct(n2) < 0 && v.dotProduct(n3) < 0))//checks that they have the same sign (+/-)
			flag = true;
		
		if(flag == true)
		{
			List<GeoPoint> lst = plane.findGeoIntersectionsHelper(ray); //list of the intersections with the plane that the triangle is in	
			if(lst!= null)
			{
				lst.get(0).geometry= this;//changes the shape to say triangle
				return lst;
			}
		}
		   
		return null;//if it hits the plane but not the triangle
	}
	

}
