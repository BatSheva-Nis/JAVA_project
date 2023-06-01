/**
 * 
 */
package renderer;
import primitives.*;
import primitives.Vector;
import geometries.*;
import scene.*;
import java.util.*;
import geometries.Intersectable.GeoPoint;
import lighting.LightSource;


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
		List<GeoPoint> intersections = sn.geometries.findGeoIntersections(ray);
		if(intersections == null)
			return sn.background;
		//The closest point
		GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
		//Returns the color of the closest point
		return calcColor(closestPoint,ray);
	}
	
	/***
	 * Calculates the color of the point
	 * Helped by the ambient lighting of the scene object
	 * @param point
	 * @returns the color of the point he received
	 */
	private Color calcColor(GeoPoint gp,Ray ray) {
		return sn.ambient.getIntensity().add(calcLocalEffects(gp, ray));
	}
/**
 * calculate  how much the light effect the shape
 * @param gp GeoPoint
 * @param ray Ray
 * @returns the color in account of the lights
 */
	private Color calcLocalEffects(GeoPoint gp, Ray ray) {
		Color color = gp.geometry.getEmission();//gets the emission
		Vector v = ray.getDir (); 
		Vector n = gp.geometry.getNormal(gp.point);
		double nv = Util.alignZero(n.dotProduct(v));//checks if normal to the point and ray are orthogonal to each other
		if (nv == 0)
			return color;
		Material material = gp.geometry.getMaterial(); //gets the material of the shape
		for (LightSource lightSource : sn.lights) //goes through all the light sources in the picture
		{
			Vector l = lightSource.getL(gp.point);//gets the direction the light is going
			double nl = Util.alignZero(n.dotProduct(l));//nl = dotproduct between normal to the point and directional vector of the light
			if (nl * nv > 0) //check that the light effect the point on the shape
			{ // sign(nl) == sing(nv)
			Color iL = lightSource.getIntensity(gp.point);
			color = color.add(iL.scale(calcDiffusive(material, nl)),	
					          iL.scale(calcSpecular(material, n, l, nl, v)));
		}
		}
		return color;
	}
	/**
	 * calculate the shiny dots on the shape -specular
	 * @param material Material
	 * @param n Vector
	 * @param l Vector
	 * @param nl double
	 * @param v Vector
	 * @returns Double3 amount of specula
	 */
private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
	Vector ln = n.scale(l.dotProduct(n));//ln =(l*n)*n
	Vector r = l.subtract(ln.scale(2));//max = l-2*ln
	double vr = Util.alignZero(v.dotProduct(r)); //vr = v*r
	double max = Math.max(0, -vr) ;
	double pow =Math.pow(max, material.nShininess);
	
	 return material.kS.scale(pow);// ks * pow
	}

/**
 * calculate the way how the light spreads on the shape
 * @param material Material
 * @param nl double normal*directianl vector
 * @return amount of diffuse -Double3
 */
	private Double3 calcDiffusive(Material material, double nl) {
		return material.kD.scale(Math.abs(nl));
	}

}
