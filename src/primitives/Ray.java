//package primitives;
//
//import java.util.Objects;
//import java.util.*;
//import geometries.Intersectable.GeoPoint;
//
//
///**
// * class Ray is the basic class representing a ray in Cartesian 3-Dimensional coordinate system
// * 
// * @author Rachelli Adler Yael Kahana Batsheva Nissim
// */
//public class Ray {
//	private final Point p0;  /**נקודה*/
//	private final Vector dir; /**כיוון*/
//	/**
//	 * Constant for moving the beginning of the ray for shading rays
//	 */
//	private static final double DELTA = 0.1;
//	
//	/** getter
//    *
//    * @return base point of ray
//    */
//	public Point getP0() {
//		return p0;
//	}
//	
//	/** getter
//    *
//    * @return direction of ray
//    */
//	public Vector getDir() {
//		return dir;
//	}
//	
//	
//	/** constructor to initiate ray with given point and direction
//    *
//    * @param p0 base point
//    * @param dir direction vector
//    */
//	 public Ray(Point p0, Vector dir) {
//		 this.p0 = p0;
//	     this.dir = dir.normalize();
//	 }
//	
//	  /**
//	     * constructor for creating a ray with small movement of the starting point
//	     * @param point the starting point that on some geometry
//	     * @param dir the direction of the ray
//	     * @param n normal to the point on some geometry
//	     */
//	    public Ray(Point point, Vector dir, Vector n) {
//	        Vector delta = n.scale(n.dotProduct(dir) > 0 ? DELTA : - DELTA);
//	        this.p0 = point.add(delta);
//	        this.dir = dir.normalize();
//	    }
//	 
//	 
//	 @Override
//	 public boolean equals(Object obj) {
//		 if (this == obj) return true;
//		 if (obj == null) return false;
//		 if (obj instanceof Ray other)
//			return this.p0.equals(other.p0)&& this.dir.equals(other.dir);
//		return false;
//	}
//
//
//	@Override
//	public String toString() {
//		return "Ray [p0=" + p0 + ", dir=" + dir + "]";
//	}
//	
//	/***
//	 * gets a double that represents distanc from a dot
//	 * @param t
//	 * @return a point with the distance t from p0 in direction v
//	 */
//	public Point getPoint(double t)
//	{
//		return p0.add(dir.scale(t));
//	}
//	
//	/***
//	 * Finds the point closest to the beginning of the ray by finding the minimum distance between a point from the list and the top of the ray
//	 * @param lst
//	 * @returns the point closest to the top of the ray
//	 */
//	public Point findClosestPoint(List<Point> points) {
//		 return points == null || points.isEmpty() ? null
//		 : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
//		}
//	
//	/**
//	 * 
//	 * @param lst
//	 * @return
//	 */
//	public GeoPoint findClosestGeoPoint(List<GeoPoint> lst)
//	{
//		//if the list is empty
//		if(lst == null || lst.size() == 0)
//				return null;
//		
//		GeoPoint minP = lst.get(0);
//		
//		//We will go over all the points in the list
//		for (GeoPoint temp: lst)
//		{
//			
//			double tempMin = temp.point.distance(p0);
//			if (tempMin < minP.point.distance(p0))
//			{
//				minP = temp;
//			}
//		}
//		return minP;
//	
//		}
//
//}



package primitives;
import geometries.Intersectable.GeoPoint;
import java.util.List;

/**
 * A class representing a ray, the set of points on a line that are on one side of a given point on the line
called the head of the fund. Defined by point and direction
 * @author Lea and Moriya
 */
public class Ray {
	private static final double DELTA = 0.1;
	/**Point field to represent a beam*/
	final Point p0;
	/**A vector field to represent a ray*/
	final Vector dir;
	/**
	 * Constructor to initialize the foundation values
	 * @param p0 the point of the head of the horn
	 * @param dir the direction of the fund
	 */
	public Ray(Point p0, Vector dir) {
		this.p0 = p0;
		this.dir = dir.normalize();
	}
	public Ray(Point p0, Vector dir,Vector n) {
		
		Vector epsVector = n.scale(n.dotProduct(dir)>0? DELTA:-DELTA);
		this.p0 = p0.add(epsVector);
		this.dir = dir.normalize();
	}
	/**
	 * @return the p0 (The starting point of the fund) value
	 */
	public Point getP0() {
		return p0;
	}
	/**
	 * @return dir,the ray direction vector
	 */
	public Vector getDir() {
		return dir;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj instanceof Ray other)
		return this.p0.equals(other.p0)&& this.dir.equals(other.dir);
		return false;
	}
	@Override
	public String toString() {
		return "Ray [p0=" + p0.toString() + ", dir=" + dir.toString() + "]";
	}	
	/**
	 * @param t, 
	 * @return the adding of the point of the ray with scaling the vector of the ray with t
	 */
	public Point getPoint(double t)
	{
		return p0.add(dir.scale(t));
	}
	/**
	 * @param poin list of points
	 * @return the closes point to the head of the ray
	 */
	public Point findClosestPoint(List<Point> points) {
		 return points == null || points.isEmpty() ? null
		 : findGeoClosestPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
		}

	public GeoPoint findGeoClosestPoint(List<GeoPoint> poin)
	{
		//if the list is empty
		if (poin == null) {
	        return null;
	    }
		GeoPoint minPoint=poin.get(0);
		//for the all point in the list
		for(GeoPoint po:poin)
		{
			if(po.point.distance(p0)<minPoint.point.distance(p0))
			{
				minPoint=po;
			}
		}
		return minPoint;
	}
}


