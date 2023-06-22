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
	private static final Double3 INITIAL_K = new Double3(1.0);
	
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
		GeoPoint closestPoint = findClosestIntersection(ray);
		return closestPoint == null ? scene.background
					: calcColor(closestPoint,ray);
	}
	

	/***
	 * Calculates the color of the point
	 * Helped by the ambient lighting of the scene object
	 * @param gp
     * @param ray
     * @param level
     * @param k
	 * @returns the color of the point he received
	 */
    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
    	  Color color = calcLocalEffects(gp,ray,k);
          return 1 == level ? color : color.add(calcGlobalEffects(gp, ray, level, k));
    }
    
    
    /**
     * Calculate color using recursive function
     *
     * @param geopoint the point of intersection
     * @param ray      the ray
     * @return the color
     */
    private Color calcColor(GeoPoint geopoint, Ray ray) {
        return calcColor(geopoint, ray, MAX_CALC_COLOR_LEVEL,INITIAL_K)
                .add(scene.ambient.getIntensity());

    }
       
	
    /**
     * calculate  how much the light effect the shape
     * @param gp GeoPoint
     * @param ray Ray
     * @returns the color in account of the lights
     */
    	private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
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
    				Double3 ktr = transparency(gp, lightSource, l, n); //intensity of shadow
    				if(!ktr.product(k).lowerThan(MIN_CALC_COLOR_K))
    				{
    					Color iL = lightSource.getIntensity(gp.point);
    					color = color.add(iL.scale(calcDiffusive(material, nl)),	
    					          	iL.scale(calcSpecular(material, n, l, nl, v)));
    				}
    			}
    		}
    		return color;
    	}
  	
    
    private Color calcGlobalEffects(GeoPoint gp, Ray ray,int level, Double3 k) 
    {
        //Color color = Color.BLACK;
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        return calcGlobalEffect(constructReflectedRay(gp, v, n),level, k, material.kR).add(calcGlobalEffect(constructRefractedRay(gp, v, n),level, k, material.kT));
    }
    
    private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint gp = findClosestIntersection(ray);
        if (gp == null) return scene.background.scale(kx);
        return Util.isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getDir()))? Color.BLACK : calcColor(gp, ray, level-1, kkx).scale(kx);
    } 
    
	   /**
  * function will construct a reflection ray
  *
  * @param geoPoint geometry point to check
  * @param normal   normal vector
  * @param vector   direction of ray to point
  * @return reflection ray
  */
    private Ray constructReflectedRay(GeoPoint gp,Vector v,Vector n){
        //r = v - 2 * (v*n) * n
        //r is the reflected ray
    	Vector r =calcRDirection(n, v,n.dotProduct(v)).normalize();
    	return new Ray(gp.point, r,n);
    }
    
    /**
     * function will construct a refraction ray
     *
     * @param geoPoint geometry point to check
     * @param normal   normal vector
     * @param vector   direction of ray to point
     * @return refraction ray
     */
    private Ray constructRefractedRay(GeoPoint gp,Vector v,Vector n){
    	return new Ray(gp.point, v,n);
    }

    
    private Vector calcRDirection(Vector n, Vector l,double nl) {
    	//v - 2 * (v*n) * n
        return l.subtract(n.scale(nl*2));
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
    
//	  /**
//	  * Checking for non-shading between a point and the light source
//	  *
//	  * @param gp- the point
//	  * @param l- the direction of the light
//	  * @param n- normal to the point
//	  * @param lightSource- the current light source 
//	  * @return true if the point is shading, false if not shading
//	  */
//		private boolean unshaded(GeoPoint gp, LightSource light, Vector l, Vector normal)
//		{
//			Vector lightDirection = l.scale(-1); // from point to light source
//			//Vector DELTAVector = n.scale(nl < 0 ? DELTA : -DELTA);//We will move the ray a little
//			Vector DELTAVector = normal.scale(normal.dotProduct(lightDirection) > 0 ? DELTA : -DELTA); 
//			Point point = gp.point.add(DELTAVector);// up the point
			//We will check if this point is shading
//			Ray lightRay = new Ray(point, lightDirection);
//			
//			//finding points that are closer to the point than the light
//			List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
//			//if there are no intersections return true (there is no shading)
//			if (intersections == null) 
//				return true;//not shading
//			//double length = light.getDistance(point);
//			if (light instanceof DirectionalLight)
//				return false;//shading
//			
//			double lightDistance = light.getDistance(gp.point);//calculates the Distance between light and point
//	     	for (GeoPoint geoPoint : intersections) {
//	     	//checks if the point is behind light
//	     	if (geoPoint.point.distance(gp.point) <= lightDistance &&  geoPoint.geometry.getMaterial().kT.equals(new Double3(0.0)))
//	     		return false;//the point should be shaded
//	     }
//			// not shading
//			return true;
//		}
//	 
//	
	
	
	
	
    /**
     * Checking for non-shading between a point and the light source
     *
     * @param gp- the point
     * @param l- the direction of the light
     * @param n- normal to the point
     * @param lightSource- the current light source 
     * @return true if the point is shading, false if not shading
     */
	private Double3 transparency(GeoPoint gp, LightSource light, Vector l, Vector n)
	{
		Vector lightDirection = l.scale(-1); // from point to light source
		//Vector DELTAVector = n.scale(nl < 0 ? DELTA : -DELTA);//We will move the ray a little
		//Vector DELTAVector = normal.scale(normal.dotProduct(lightDirection) > 0 ? DELTA : -DELTA); 
		//Point point = gp.point.add(DELTAVector);// We will check if this point is shading
		Ray lightRay = new Ray(gp.point, lightDirection, n);
		
		double lightDistance = light.getDistance(gp.point);//calculates the Distance between light and point

		
		//finding points that are closer to the point than the light
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
		//if there are no intersections return true (there is no shading)
		if (intersections == null) 
			return Double3.ONE;//not shading
		//double length = light.getDistance(point);
		//if (light instanceof DirectionalLight)
			//return false;//shading
		
		Double3 ktr = new Double3(1d);
        for (GeoPoint geoPoint : intersections) {
        	//checks if the point is behind light
        	if (Util.alignZero(geoPoint.point.distance(gp.point) - lightDistance) <= 0) {
                ktr = ktr.product(geoPoint.geometry.getMaterial().kT); //the more transparency the less shadow
                if (ktr.lowerThan(MIN_CALC_COLOR_K)) return Double3.ZERO;
        	}
        }
		// not shading
		return ktr;
	}
	
	
	/**
	 * find the closest intersection point
	 * @param ray
	 * @returns the closest point	
	 */
	private GeoPoint findClosestIntersection(Ray ray)
	{
		if (ray == null)
			return null;
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
		if (intersections == null)
			return null;
		GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
		return closestPoint;
	}
	
	
	//****************************MiniProject1****************************
	/***
	 * Finds the list of intersection points of the ray with the scene 
	 * and returns the color of the closest point, 
	 * with this color we will color the pixel
	 * @param ray
	 * @returns the color
	 */
	public Color traceMultiRay(List<Ray> rays)
	{
		Color colorSum = Color.BLACK;
		for (Ray ray: rays)
		{
			GeoPoint closestPoint = findClosestIntersection(ray);
			if (closestPoint != null)
				colorSum = colorSum.add(calcColor(closestPoint, ray));
			else
				colorSum = colorSum.add(scene.background);
		}
	
		return colorSum.reduce(rays.size());
	}
	
}



