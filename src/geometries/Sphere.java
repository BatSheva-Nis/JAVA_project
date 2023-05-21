package geometries;

import static primitives.Util.*;

import java.util.List;

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
    
	
	/***
	 * the function checks if there are intersection between the ray and the sphere
	 *  and if yes returns a list with the intersection
	 * @param ray
	 * @returns list of inersection. 1/0/2 intersections
	 */
    @Override
	public List<Point> findIntersections(Ray ray)
	{
	   Point p0 = ray.getP0();
	   Vector v = ray.getDir();
	   Vector u =center.subtract(p0);
	   double tm = v.dotProduct(u);
	   double d = Math.sqrt(u.lengthSquared() - tm*tm);
	   if(d >= radius)
		   return null;
	   double th =Math.sqrt(radius*radius - d*d);
	   double t1 =tm + th;
	   double t2 =tm - th;
	   Point p1=null;
	   Point p2 =null;
	   boolean flag1 =false,flag2 =false;
	   if(t1>0)
	     {
		 //  p1 =p0.add(v.scale(t1)); //refactoring
		   p1 =ray.getPoint(t1);
		   double len =p1.subtract(center).lengthSquared();
		   if(isZero(alignZero(len - radius*radius)))
			   flag1 =true;
		   if(p1.equals(p0))//makes sure not at the begining of the ray
			   flag1 = false;
			   
	     }
	   if(t2 >0)
	     {
		    //p2 =p0.add(v.scale(t2)); //refactoring
		    p2 = ray.getPoint(t2);
		    double len =p2.subtract(center).lengthSquared();
			if(isZero(alignZero(len - radius*radius)))
			    flag2 =true;
		    if(p2.equals(p0))//makes sure not at the begining of the ray
			   flag2 = false;
		   
	     }
	   if(!flag1 && !flag2)//no intersections
		   return null;
	   if(flag1 &&flag2)//2 intersections
	   {
	   return  List.of(p1,p2);
	   }
	   if(flag1 && !flag2)//p1 yes
	   {
	   return  List.of(p1);
	   }
	   if(!flag1 && flag2)//p2 yes
	   {
	   return  List.of(p2);
	   }
       return null;
	}
	
}
