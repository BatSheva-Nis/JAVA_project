package geometries;

import primitives.*;

/**
 * interface geometry is the basic behavior of all Geometries in Cartesian 3-Dimensional coordinate system
 *
 * @author Batsheva Nissim and Yael Kahana and Rachelli Adler
 */
public interface Geometry extends Intersectable {

    /**
     * return a normal vector to the geometry at the given point
     *
     * @param point point on the geometry
     * @return normal vector
     */
    public Vector getNormal(Point point);
}
