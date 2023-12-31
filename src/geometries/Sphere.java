package geometries;

import static primitives.Util.*;

import java.util.List;

import geometries.Intersectable.GeoPoint;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Sphere class represents a 3D sphere in Cartesian 3D coordinate system
 *
 * @author Rachelli Adler Yael Kahana Batsheva Nissim
 */
public class Sphere extends RadialGeometry {
	private final Point center;
	
	
	/**
     * constructor to initialize Sphere with center point and radius
     *
     * @param center center point of sphere
     * @param radius radius of sphere
     */
    public Sphere(Point center, double radius) {
        super(radius);
        this.center = center;
    }

    /**
     * getter
     *
     * @return center point
     */
    public Point getCenter() {
        return center;
    }

    public Vector getNormal(Point point) {
        return (point.subtract(center)).normalize();
    }
      
    
//    /**
//	 * the function checks if there are intersection between the ray and the sphere
//	 *  and if yes returns a list with the intersection
//	 * @param ray
//	 * @returns list of intersection. 1/0/2 intersections .The point and the shape the point is on
//	 */
//    @Override
//	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray){
//		   Point p0 = ray.getP0();
//		   Vector v = ray.getDir();
//		   Vector u = center.subtract(p0);//center(o)-p0
//		   double tm =alignZero( v.dotProduct(u));//v*u
//		   double d = alignZero(Math.sqrt(u.lengthSquared() - tm*tm));//sqrt(u*u - tm*tm)
//		   if(d >= radius)//If the points are outside the sphere
//			   return null;
//		   double th = alignZero(Math.sqrt(radius*radius - d*d));//sgrt(r*r - d*D) 
//		   double t1 = alignZero(tm + th);
//		   double t2= alignZero(tm - th);
//		   Point p1= null;
//		   Point p2 =null;
//		   
//		   boolean flag1 =false,flag2 =false;
//		   if(t1>0)//If the direction is negative, the point is outside the sphere
//		   { 
//			   flag2 = true;
//			 //  p1 = p0.add(v.scale(t1)); //refactoring
//			   p1 = ray.getPoint(t1);
//			   double len = alignZero(p1.subtract(center).lengthSquared());
//			   if(isZero(alignZero(len - radius*radius)))
//				   flag1 = true;
//			   if(p1.equals(p0))//makes sure not at the begining of the ray  
//				   flag1 = false;
//				   
//		     }
//		   if(t2 >0)//If the direction is negative, the point is outside the sphere
//		     {
//			    //p2 =p0.add(v.scale(t2)); //refactoring
//			    p2 = ray.getPoint(t2);
//			    double len =alignZero(p2.subtract(center).lengthSquared());
//				if(isZero(alignZero(len - radius*radius)))
//				    flag2 = true;
//			    if(p2.equals(p0))//makes sure not at the begining of the ray
//				   flag2 = false;
//			   
//		     }
//		   
//		   //Checking which points are available and creating a list
//		   if(!flag1 && !flag2)//no intersections
//			   return null;
//		   if(flag1 && flag2)//2 intersections
//		   {
//			   GeoPoint g1 = new GeoPoint(this,p1);
//			   GeoPoint g2 = new GeoPoint(this,p2);
//			   return  List.of(g1,g2);
//		   }
//		   if(flag1 && !flag2)//p1 yes
//		   {
//			   GeoPoint g1 =new GeoPoint(this,p1);
//			   return  List.of(g1);
//		   }
//		   if(!flag1 && flag2)//p2 yes
//		   {
//			   GeoPoint g2 =new GeoPoint(this,p2);
//			   return  List.of(g2);
//		   }
//	       return null;
//	}
    
    
    protected     List<GeoPoint> findGeoIntersectionsHelper(Ray ray)
    {
        double tm=ray.getDir().dotProduct(center.subtract(ray.getP0()));
        double d=Math.sqrt(((center.subtract(ray.getP0())).lengthSquared())-tm*tm);
        if(d>=radius)
            return null;
        double th= alignZero(Math.sqrt(radius*radius-d*d));
        double t1=alignZero(tm+th);
        double t2=alignZero(tm-th);
        if(t1>0&&t2>0)//if there are two points 
        {
            return List.of(new GeoPoint(this,ray.getPoint(t1)),new GeoPoint(this,ray.getPoint(t2)));
        }
        else if(t1>0)//if there is one point
        {
            return List.of(new GeoPoint(this,ray.getPoint(t1)));
        }
        else if(t2>0)//if there is one point
        {
            return List.of(new GeoPoint(this,ray.getPoint(t2)));
        }
        return null;
    }
}
