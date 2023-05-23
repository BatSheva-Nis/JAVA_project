package geometries;

import primitives.Point;
//import java.util.*;
import java.util.List;
import java.util.Objects;

import primitives.Ray;
/***
 *
 * @author Batsheva Nissim and Yael Kahana and Rachelli Adler
 * 
 * interface that checks the intersection between a ray and an object
 */
public abstract class Intersectable {
	
	/***
	 * geo point saves the shape and a point on the shape. 
	 * @author Rachelli Adler Batsheva Nissim Yael Kahana 
	 *
	 */
	public static class GeoPoint {
		 public Geometry geometry;
		 public Point point;
		 
		 public GeoPoint(Geometry geometry, Point point)
		 {
			 this.geometry = geometry;
			 this.point = point;
		 }
		@Override
		public String toString() {
			return "GeoPoint [geometry=" + geometry + ", point=" + point + "]";
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			GeoPoint other = (GeoPoint) obj;
			return Objects.equals(geometry, other.geometry) && Objects.equals(point, other.point);
		}
		}

	/***
	 * 
	 * @param ray
	 * @return the list of intersections
	 */
	public List<Point> findIntersections(Ray ray){
		return null;
	}
	
	/**
	 * 
	 * @param ray
	 * @returns The point and the shape the point is on
	 */
	public List<GeoPoint> findGeoIntersections(Ray ray){
		return findGeoIntersectionsHelper(ray);
	}
	protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

}
