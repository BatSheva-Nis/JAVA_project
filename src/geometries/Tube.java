package geometries;

import primitives.*;

import java.util.List;

import geometries.*;
import geometries.Intersectable.GeoPoint;

/**
 * Tube class represents three-dimensional Tube in 3D Cartesian coordinate
 *
 * @author Rachelli Adler Yael Kahana Batsheva Nissim
 */
public class Tube extends RadialGeometry {
	/**
     * the axis of the tube
     */
    protected final Ray axisRay;

    /**
     * constructor to initialize Tube with radius and axis ray
     *
     * @param axisRay axis ray of tube
     * @param radius  radius of tube
     */
    public Tube(Ray axisRay, double radius) {
        super(radius);
        this.axisRay = axisRay;
    }
    
    /**
     * getter
     *
     * @return axis ray of tube
     */
    public Ray getAxisRay() {
        return axisRay;
    }
    
    /**
	 * returns the normal
	 */
    public Vector getNormal(Point point) {
    	return null;
    }
    /***
	 * 
	 * @param ray
	 * @return the list of intersections
	 */
//    @Override
//	public List<Point> findIntersections(Ray ray)
//	{
//		return null;
//	}
    
    /**
	 * 
	 * @param ray
	 * @returns The point and the shape the point is on
	 */
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray){
		List<Point> intersections = findIntersections(ray);
		if(intersections == null)
			return null;
		Point point = intersections.get(0);
		return List.of(new GeoPoint(this, point));
	}

}
