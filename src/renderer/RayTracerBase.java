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
	
	/***
	 * 3D model of a scene
	 * 
	 */
	final protected Scene scene;

	/**
	 * constructor
	 */
	public RayTracerBase(Scene scene) {
		this.scene = scene;

	}
	
	/***
	 * Finds the list of intersection points of the ray with the scene 
	 * and returns the color of the closest point, 
	 * with this color we will color the pixel
	 * Public abstract method traceRay that accepts a ray as a parameter and returns a color
	 * @param ray
	 * @returns the color
	 */
	public abstract Color traceRay(Ray ray);

}
