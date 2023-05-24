package primitives;

import java.util.Objects;
import java.util.*;
import geometries.Intersectable.GeoPoint;


/**
 * class Ray is the basic class representing a ray in Cartesian 3-Dimensional coordinate system
 * 
 * @author Rachelli Adler Yael Kahana Batsheva Nissim
 */
public class Ray {
	private final Point p0;  /**נקודה*/
	private final Vector dir; /**כיוון*/
	
	/** getter
    *
    * @return base point of ray
    */
	public Point getP0() {
		return p0;
	}
	
	/** getter
    *
    * @return direction of ray
    */
	public Vector getDir() {
		return dir;
	}
	
	
	/** constructor to initiate ray with given point and direction
    *
    * @param p0 base point
    * @param dir direction vector
    */
	 public Ray(Point p0, Vector dir) {
		 this.p0 = p0;
	     this.dir = dir.normalize();
	 }
	
	 
	 
	 @Override
	 public boolean equals(Object obj) {
		 if (this == obj) return true;
		 if (obj == null) return false;
		 if (obj instanceof Ray other)
			return this.p0.equals(other.p0)&& this.dir.equals(other.dir);
		return false;
	}


	@Override
	public String toString() {
		return "Ray [p0=" + p0 + ", dir=" + dir + "]";
	}
	
	/***
	 * gets a double that represents distanc from a dot
	 * @param t
	 * @return a point with the distance t from p0 in direction v
	 */
	public Point getPoint(double t)
	{
		return p0.add(dir.scale(t));
	}
	
	/***
	 * Finds the point closest to the beginning of the ray by finding the minimum distance between a point from the list and the top of the ray
	 * @param lst
	 * @returns the point closest to the top of the ray
	 */
	public Point findClosestPoint(List<Point> points) {
		 return points == null || points.isEmpty() ? null
		 : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
		}
	
	
	public GeoPoint findClosestGeoPoint(List<GeoPoint> lst)
	{
		if(lst == null || lst.size() < 2)
			return null;
		
		GeoPoint minP = lst.get(0);
		
		for (GeoPoint temp: lst)
		{
			double tempMin = temp.point.distance(p0);
			if (tempMin < minP.point.distance(p0))
			{
				minP = temp;
			}
		}
		return minP;
	
		}
	

}
