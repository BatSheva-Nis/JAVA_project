package geometries;
import static primitives.Util.isZero;
import primitives.*;
import java.util.List;
import static primitives.Util.*;



/**
 * class Plane represents two-dimensional plane in Cartesian 3D coordinate system
 *
 * @author Batsheva Nissim and Yael Kahana and Rachelli Adler
 */
public class Plane implements Geometry {
	private final Point q0;
    private final Vector normal;
    
    
    /**
     * constructor to initialize Plane according to 3 given Points on the Plane
     *
     * @param point1 first point
     * @param point2 second point
     * @param point3 third point
     */
    public Plane(Point point1, Point point2, Point point3) {
    	if(point1.equals(point2) || point1.equals(point3) || point2.equals(point3))
            throw new IllegalArgumentException("Can't create plane with 2 identical points");

    	Vector v1 = point2.subtract(point1);
    	Vector v2 = point3.subtract(point1);
    	if((v1.normalize()).equals(v2.normalize()))
            throw new IllegalArgumentException("Can't create plane when the three points are on the same line");

        this.q0 = point1;
        this.normal = (v1.crossProduct(v2)).normalize();
    }
    
    
    /**
     * constructor to initialize plane with point and normal
     *
     * @param q0 point on plane
     * @param normal normal vector to plane
     */
    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal.normalize();
    }
    
    /**
     * getter
     *
     * @return point on the plane
     */
    public Point getQ0() {
        return q0;
    }

    /**
     * getter
     *
     * @return normal vector to plane
     */
    public Vector getNormal() {
        return normal;
    }
    
    
    /**
     * getter
     * 
     * @param point on the plan
     * @return normal the point
     */
    @Override
    public Vector getNormal(Point point) {
        return getNormal();
    }
    
    
    /***
     * the function checks if there are intersection between the ray and the plane
     *  and if yes returns a list with the intersection
     * @param ray
     * @returns list of inersection. 1/0 intersections
     */  
    @Override
    public List<Point> findIntersections(Ray ray)
    {
          if(q0.equals(ray.getP0()))//if the ray starts on the plane
 	        	return null;
    
    	Vector v = q0.subtract(ray.getP0());//q0-p0 vector
    	double t = normal.dotProduct(v);
    	double check = normal.dotProduct(ray.getDir());
    	
        if (isZero(alignZero(check)))
            return null;//It means that the ray is parallel to the plane
        
        if(isZero(alignZero(t)))
           return null;
        
    	t= alignZero(t)/check;
    	
    	//p = ray.getP0().add(ray.getDir().scale(t)); //refactoring
    	Point p= ray.getPoint(t);
    	
    	if(t<=0)
    		return null;
    	return List.of(p);
    }
}


   