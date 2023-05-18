package geometries;

import primitives.Point;
//import java.util.*;
import java.util.List;
import primitives.Ray;
/***
 *
 * @author Batsheva Nissim and Yael Kahana and Rachelli Adler
 * 
 * interface that checks the intersection between a ray and an object
 */
public interface Intersectable {
	/***
	 * 
	 * @param ray
	 * @return the list of intersections
	 */
	List<Point> findIntersections(Ray ray);

}
