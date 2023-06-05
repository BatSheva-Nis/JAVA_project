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
import lighting.DirectionalLight;
import lighting.LightSource;


/**
 * The RayTracerBasic class that inherits from the abstract class RayTracerBase
 * @author Rachelli Adler Batsheva Nissim Yael Kahana 
 *
 */
public class RayTracerBasic extends RayTracerBase{

	/**
	 * A recursion element stopping condition of transparency or reflection
	 */
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	private static final double MIN_CALC_COLOR_K = 0.001;
	
	private static final double INITIAL_K = 1.0;

	/**
	 * Rayhead move size for shading(צל) rays
	 */
	private static final double DELTA = 0.1;
	
	/**
	 * constructor
	 */
	public RayTracerBasic(Scene scene) {
		super(scene);
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
		////////////////List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
		//if(intersections == null)
			//return scene.background;
		//The closest point
		/////////////////GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
		//Returns the color of the closest point
		GeoPoint closestPoint = findClosestIntersection(ray);
		return closestPoint == null ? scene.background
					: calcColor(closestPoint,ray);
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
		for (LightSource lightSource : scene.lights) //goes through all the light sources in the picture
		{
			Vector l = lightSource.getL(gp.point);//gets the direction the light is going
			double nl = Util.alignZero(n.dotProduct(l));//nl = dotproduct between normal to the point and directional vector of the light
			if (nl * nv > 0) //check that the light effect the point on the shape
			{ // sign(nl) == sing(nv)
				if(unshaded(gp, lightSource, l, n, nl))
				{
					Color iL = lightSource.getIntensity(gp.point);
					color = color.add(iL.scale(calcDiffusive(material, nl)),	
					          	iL.scale(calcSpecular(material, n, l, nl, v)));
				}
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
	
    /**
     * Returns wether a certain point is shaded by other objects
     *
     * @param gp          the point
     * @param l           the direction of the light
     * @param n           normal to the point
     * @param lightSource the loght source
     * @return true if the point is shaded
     */
    private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource lightSource, double nv) {
        Vector lightDirection = l.scale(-1); //vector from the point to the light source

        Ray lightRay = new Ray(gp.point, lightDirection,n);

        double lightDistance = lightSource.getDistance(gp.point);
        //finding only points that are closer to the point than the light
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);

        //if there are no intersections return true (there is no shadow)
        if (intersections == null) {
            return true;
        }

        //for each intersection
        for (GeoPoint intersection : intersections) {
            //if the material is not transparent return false
            if (intersection.geometry.getMaterial().kT == Double3.ZERO) {
                return false;
            }

        }
        return true;
    }
	
	/**
	 * find the closest intersection point
	 * @param ray
	 * @returns the closest point	
	 */
	private GeoPoint findClosestIntersection(Ray ray)
	{
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
		GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
		return closestPoint;
	}

	
    /**
     * שקיפות
     * Construct the ray refracted by an intersection with the geometry
     * @param n normal to the geometry at intersection
     * @param point the intersection point
     * @param innerVec the ray entering
     * @return refracted ray (in our project there is no refraction incidence, so we return a new ray with the same characteristics)
     */
    private Ray constructRefractedRay(Vector n, Point point, Vector innerVec) { 
    	//סוג של המשך הקרן 
        return new Ray(point,n,innerVec);
    }
    /**
     * השתקפות
     * Construct the ray getting reflected on an intersection point
     * @param n normal to the point
     * @param point the intersection point
     * @param v the ray entering at the intersection
     * @return the reflected ray
     */
    private Ray constructReflectedRay(Vector n, Point point, Vector v) {
        //r = v - 2 * (v*n) * n
        //r is the reflected ray
        Vector r = null;
        try {
            r = v.subtract(n.scale(v.dotProduct(n)).scale(2)).normalize();
        } catch (Exception e) {
            return null;
        }
        return new Ray(point, n,r);
    }
    
	/***
	 * Calculates the color of the point
	 * Helped by the ambient lighting of the scene object
	 * @param point
	 * @returns the color of the point he received
	 */
    private Color calcColor(GeoPoint intersection, Ray ray,int level,Double3 k) {
    	Color color = scene.ambient.getIntensity()
    	.add(calcLocalEffects(gp, ray));
    	return 1 == level ? color : color = color.add(calcGlobalEffects(gp, ray, level, k));
    }
    
    /**
     * Calculate color using recursive function
     *
     * @param geopoint the point of intersection
     * @param ray      the ray
     * @return the color
     */
    private Color calcColor(GeoPoint geopoint, Ray ray) {
        return calcColor(geopoint, ray, MAX_CALC_COLOR_LEVEL,new Double3(INITIAL_K))
                .add(scene.ambientLight.getIntensity());

    }

	
    private Color calcGlobalEffects(GeoPoint intersection, Vector inRay, int level, Double3 k) {
        Color color = Color.BLACK; //base color
        Vector n = intersection.geometry.getNormal(intersection.point); //normal

        //reflection attenuation of the material
        Double3 kr = intersection.geometry.getMaterial().kR;
        //reflection level as affected by k
        Double3 kkr = kr.product(k);

        if (!kkr.lowerThan(MIN_CALC_COLOR_K)) { //if the reflection level is not lower than the minimum
            //construct a reflection  ray from the point
            Ray reflectedRay = constructReflectedRay(n, intersection.point, inRay);

            //add this color to the point by recursively calling calcGlobalEffect
            color = calcGlobalEffect(reflectedRay, level, kr, kkr);
        }


        //transparency  attenuation factor of the material
        Double3 kt = intersection.geometry.getMaterial().kT;
        //transparency level
        Double3 kkt = kt.product(k);

        if (!kkt.lowerThan(MIN_CALC_COLOR_K)) {//if the transparency level is not lower than the minimum
            //construct a refracted ray from the point
            Ray refractedRay = constructRefractedRay(n, intersection.point, inRay);

            //add to the color to the point by recursively calling calcGlobalEffect
            color = color.add(calcGlobalEffect(refractedRay, level, kt, kkt));
        }

        return color;
    }

	
}
