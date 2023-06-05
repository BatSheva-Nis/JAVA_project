/**
 * 
 */
package lighting;
import primitives.*;
/**
 * @author Rachelli Adler Yael Kahana Batsheva Nissim
 *this class represents light that has a starting point but NO direction. like a regular lightbulb
 */
public class PointLight extends Light implements LightSource {
	/**
	 * starting point of the light
	 */
	private Point position;
	/**
	 * parameters -(mkadmey hanchata)
	 */
    private double kc =1;
    private double kl =0;
    private double kq =0;
	/**
	 * @param kC the kC to set
	 */
	public PointLight setKc(double kC) {
		this.kc = kC;
		return this;
	}
	/**
	 * @param kL the kL to set
	 */
	public PointLight setKl(double kL) {
		this.kl = kL;
		return this;
	}
	/**
	 * @param kQ the kQ to set
	 */
	public PointLight setKq(double kQ) {
		this.kq = kQ;
		return this;
	}
	/**
	 * ctor
	 * @param intensity color
	 * @param position vector
	 */
	public PointLight(Color intensity, Point position) {
		super(intensity);
		this.position = position;
		
	}
	/**
	 * gets the intensity of the point
	 * @param p point
	 * @returns the intensity color
	 */
	public Color getIntensity(Point p)
	{
		double d=position.distance(p);
		double iL = kc + kl*d + kq*d*d;
		return getIntensity().reduce(iL);// division
	}
	/**
	 * calculates the direction from the light to the item
	 * @param p point
	 * @returns a vector directional vector
	 */
	public Vector getL(Point p)
	{
		return p.subtract(position).normalize();
	}
    
	/**
	 * return the distance from the light source to the point
	 * @param point
	 * @returns distance
	 */
	public double getDistance(Point point)
	{
		return position.distance(point);
	}
	
}
