/**
 * 
 */
package renderer;
import primitives.*;
import geometries.*;
import scene.*;

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
		return null;
	}

}
