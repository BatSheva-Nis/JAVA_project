package primitives;

import java.util.Objects;


/**
 * class Point is the basic class representing a point of a Euclidean geometry in Cartesian 3-Dimensional coordinate system
 *
 * @author Rachelli Adler Yael Kahana Batsheva Nissim
 */

public class Point{
	 /**
     * 3 coordinates of the Point
     **/
	final protected Double3 xyz;

    /**
     * constructor to initialize the Point object with 3 given coordinates
     *
     * @param d1 First number value
     * @param d2 Second number value
     * @param d3 Third number value
     */
    public Point(double d1, double d2, double d3) {
        this.xyz = new Double3(d1, d2, d3);
    }
    
    
    /**
     * constructor to initialize the Point object with values from a given Double3 object
     * @param xyz Double3 object of Point's coordinates
     */
    Point(Double3 xyz) {
        this(xyz.d1, xyz.d2,xyz.d3);
    }
    
    
    /**
     * vector subtraction between two Points
     * @param point second Point
     * @return vector between P1 and current Point
     */ 
    public Vector subtract(Point point) {
    	return new Vector(this.xyz.subtract(point.xyz));
    }
    
    
    /**
     * adds vector to point to create new point with summed values of the original point and vector's coordinates
     * @param vector vector to add to point
     * @return new point that
     */
    public Point add(Vector vector) {
    	return new Point(this.xyz.add(vector.xyz));
    }
    
    
    /**
     * calculates squared distance between two points
     * @param point other point
     * @return squared distance
     */
    public double distanceSquared(Point point) {
        return (this.xyz.d1 - point.xyz.d1) * (this.xyz.d1 - point.xyz.d1) + (this.xyz.d2 - point.xyz.d2) * (this.xyz.d2 - point.xyz.d2) + (this.xyz.d3 - point.xyz.d3) * (this.xyz.d3 - point.xyz.d3);	
	}
    
    
    /**
     * calculates distance between two points
     * @param point other point
     * @return distance
     */
    public double distance(Point point) {
        return Math.sqrt(this.distanceSquared(point));
    }

	/**
	 * returns x
	 * @return
	 */
	public double getX() {
		
		return xyz.d1;
	}
	/**
	 * returns y
	 * @return
	 */
	public double getY() {
		
		return xyz.d2;
	}
    
    @Override
    public boolean equals(Object obj) {
    	if (this == obj) return true;
    	if (obj instanceof Point other)
			return xyz.equals(other.xyz);
			return false;
    }


    @Override
    public String toString() { return "" + xyz; }
	
}
