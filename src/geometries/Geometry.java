package geometries;
import primitives.*;

/**
 * interface geometry is the basic behavior of all Geometries in Cartesian 3-Dimensional coordinate system
 *
 * @author Batsheva Nissim and Yael Kahana and Rachelli Adler
 */
public abstract class Geometry extends Intersectable {

	protected Color emission = Color.BLACK;
	
	/**
	 * getter emission.
	 * @returns the emission.
	 */
    public Color getEmission() {
		return emission;
	}
    
	/**
	 * setter of emission.
	 * @returns the Geometry itself and sets the emission.
	 */
    public Geometry setEmission(Color e) {
    	this.emission = e;
		return this;
	}

	/**
     * return a normal vector to the geometry at the given point
     *
     * @param point point on the geometry
     * @return normal vector
     */
    public abstract Vector getNormal(Point point);

	/***
	 * the function checks if there are intersection between the ray and the plane
	 *  and if yes returns a list with the intersection
	 * @param ray
	 * @returns list of intersection. 1/0 intersections
	 */
}
