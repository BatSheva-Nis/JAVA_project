/**
 * 
 */
package renderer;
import primitives.*;
import geometries.*;
import scene.*;
import java.util.*;

/**
 * The RayTracerBasic class that inherits from the abstract class RayTracerBase
 * @author Rachelli Adler Batsheva Nissim Yael Kahana 
 *
 */
public class RayTracerBasic extends RayTracerBase{

	/**
	 * constructor
	 */
	public RayTracerBasic(Scene sn) {
		super(sn);
	}
	
	/***
	 * Finds the list of intersection points of the ray with the scene 
	 * and returns the color of the closest point, 
	 * with this color we will color the pixel
	 * @param ray
	 * @returns the color
	 */
	public Color traceRay(Ray ray)
	{
		//List of intersection points
		List<Point> intersections = sn.geometries.findIntersections(ray);
		if(intersections == null)
			return sn.background;
		//The closest point
		Point closestPoint = ray.findClosestPoint(intersections);
		//Returns the color of the closest point
		return calcColor(closestPoint);
	}
	
	/***
	 * Calculates the color of the point
	 * Helped by the ambient lighting of the scene object
	 * @param point
	 * @returns the color of the point he received
	 */
	private Color calcColor(Point point) {
		return sn.ambient.getIntensity();
	}

}
