package primitives;
import geometries.Intersectable.GeoPoint;
import java.util.List;

/**
 * A class representing a ray, the set of points on a line that are on one side of a given point on the line
called the head of the fund. Defined by point and direction
 * @author Rachelli Adler Batsheva Nissim Yael Kahana 
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


