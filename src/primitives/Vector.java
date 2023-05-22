package primitives;
import primitives.Util.*;


/**
 * Class Vector is the basic class representing a vector of Euclidean geometry in Cartesian 3-Dimensional coordinate system
 *
 * @author Rachelli Adler Yael Kahana Batsheva Nissim
 */
public class Vector extends Point {
	
	
	/**
     * constructor to initialize vector wth 3 given coordinates
     *
     * @param d1 first number value
     * @param d2 second number value
     * @param d3 third number value
     */
	public Vector(double d1, double d2, double d3) {
        super(d1, d2, d3);
        if (this.xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("Can't create Zero vector");
    }
	
	
	/**
     * constructor to initialize the Vector object with values from a given Double3 object
     *
     * @param xyz Double3 object of Vector's coordinates
     */
    Vector(Double3 xyz) {
        this(xyz.d1, xyz.d2, xyz.d3);
    }
    
    
    /**
     * sum two vectors into a new vector
     *
     * @param vector vector to add to vector
     * @return result of add
     */
    public Vector add(Vector vector) {
        return new Vector(this.xyz.add(vector.xyz));
    }

    /**
     * multiply vector and scalar
     *
     * @param number The scalar in the multiplication
     * @return result of multiplication
     */
    public Vector scale(double number) {
        return new Vector(this.xyz.scale(number));
    }
    
    /**
     * scalar multiplication between two vectors
     *
     * @param vector other vector
     * @return result of multiplication
     */
    public double dotProduct(Vector vector2) {
        return Util.alignZero(this.xyz.d1 * vector2.xyz.d1 + this.xyz.d2 * vector2.xyz.d2 + this.xyz.d3 * vector2.xyz.d3);
    }
    
    
    /**
     * Vector multiplication cross multiplication between two vectors
     *
     * @param vector other vector
     * @return result of cross multiplication
     */
    public Vector crossProduct(Vector vector) {
        // calculates each coordinate of the result vector according to the determinant of the cross multiplication matrix
        double coorI = Util.alignZero(this.xyz.d2 * vector.xyz.d3 - this.xyz.d3 * vector.xyz.d2);
        double coorJ = Util.alignZero(this.xyz.d1 * vector.xyz.d3 - this.xyz.d3 * vector.xyz.d1);
        double coorK = Util.alignZero(this.xyz.d1 * vector.xyz.d2 - this.xyz.d2 * vector.xyz.d1);
        return new Vector(coorI, coorJ * -1, coorK);
    }
    
    
    /**
     * calculates squared length of vector
     *
     * @return squared length
     */
    public double lengthSquared() {
        return Util.alignZero(xyz.d1 * xyz.d1 + xyz.d2 * xyz.d2 + xyz.d3 * xyz.d3);
    }

    
    /**
     * calculate length of vector
     *
     * @return length of vector
     */
    public double length() {
        return Util.alignZero(Math.sqrt(this.lengthSquared()));
    }

    /**
     * reduce vector by its length to normalize original vector
     * @return normalized vector
     */
    public Vector normalize() {
        return new Vector(xyz.reduce(this.length()));
    }

    
    
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj instanceof Vector other)
			return super.equals(other);
			return false;
	}
	
	
	@Override
	public String toString() { return "Vector:" + xyz; }
	
}
