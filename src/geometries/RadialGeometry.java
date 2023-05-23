package geometries;

import java.util.List;

//import geometries.Intersectable.GeoPoint;
import primitives.Ray;

/**
 * class RadialGeometry is the basic class representing all Euclidean geometry with radius in Cartesian 3-Dimensional coordinate system
 *
 * @author Rachelli Adler Yael Kahana Batsheva Nissim
 */
//???????????????????????????????????????????????
public abstract class RadialGeometry extends Geometry {
	

    /**
     * radius of the radial geometry
     */
    final protected double radius;
    
    /**
     * constructor to initialize the radial geometry with a given radius
     *
     * @param radius radius of geometry
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }
    
    
    /**
     * getter
     *
     * @return radius
     */
    public double getRadius() {
        return radius;
    }

	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray){
		// TODO Auto-generated method stub
		return null;
	}
}
