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
	 * Public abstract method traceRay that accepts a ray as a parameter and returns a color
	 * @param ray
	 * @returns the color
	 */
	public Color traceRay(Ray ray)
	{
		List<Point> intersections = sn.geometries.findIntersections(ray);
		if(intersections == null)
			return sn.background;
		Point closestPoint = ray.findClosestPoint(intersections);
		return calcColor(closestPoint);
	}
	
	private Color calcColor(Point point) {
		return sn.ambient.getIntensity();
	}

}
