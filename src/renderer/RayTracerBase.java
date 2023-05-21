/**
 * 
 */
package renderer;
import primitives.*;
import geometries.*;
import scene.*;

/**
 * A RayTracer Base abstract class that will contain a scene field
 * @author Rachelli Adler Batsheva Nissim Yael Kahana 
 *
 */
public abstract class RayTracerBase {
	final protected Scene sn;

	/**
	 * constructor
	 */
	public RayTracerBase(Scene sn) {
		this.sn = sn;

	}
	
	/***
	 * Public abstract method traceRay that accepts a ray as a parameter and returns a color
	 * @param ray
	 * @returns the color
	 */
	public abstract Color traceRay(Ray ray);

}
