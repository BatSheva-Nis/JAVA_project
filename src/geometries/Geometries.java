/**
 * 
 */
package geometries;
import java.util.*;

import geometries.Intersectable.GeoPoint;
import primitives.Point;
import primitives.Ray;
import scene.*;

/**
 * class of several geometric bodies
 * @author Rachelli Adler Yael Kahana Batsheva Nissim
 * 
 */
public class Geometries extends Intersectable {
	private List<Intersectable> lst;
	
	/**
	 * @param lst
	 * empty ctor,makes the list empty
	 */
	public Geometries() {
		this.lst =  new ArrayList<Intersectable>();
	}
	/***
	 * ctor gets a list of geometries and puts them in the empty list
	 * @param geometries
	 */
	
	public Geometries(Intersectable... geometries )
	{
		
		this.lst=new ArrayList<Intersectable>(Arrays.asList(geometries));	
	}

	/***
	 * gets a list of geometries and adds them to the list
	 * @param geometries
	 */
	public void add(Intersectable... geometries )
	{
		if(geometries!= null)
			this.lst.addAll(Arrays.asList(geometries));
	}
	/***
	 * the function checks if there are intersection between the ray and the composite
	 *  and if yes returns a list with the intersection 
	 * @param ray
	 * @returns list of intersection geoPoint . no limit intersections
	 */
	
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray){
		List<GeoPoint> big= new ArrayList<GeoPoint>();
		List<GeoPoint> small= new ArrayList<GeoPoint>();
		for(Intersectable geo : lst)//goes through all the shapes in the composite
		{
			small =geo.findGeoIntersectionsHelper(ray);//gets the intersections of 1 shape and the ray
			if(small !=null)//if there are intersections
			    big.addAll(small);					
		}
		if(big.size() >0)
			return big;

		return null;
//		List<GeoPoint> intersections = null;
//		for (Intersectable geometry : lst)
//		{
//			List<GeoPoint> geometryIntersections = geometry.findGeoIntersections(ray);
//			if(geometryIntersections.size() != 0)
//			{
//				for(GeoPoint point: geometryIntersections)
//					intersections.add(point);
//			}	
//		}
//				
//		return intersections;
	}
	
	

}
