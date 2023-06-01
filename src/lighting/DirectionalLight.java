/**
 * 
 */
package lighting;
import primitives.*;
/**
 * @author Rachelli Adler Yael Kahana Batsheva Nissim
 * this class represents light that only has a direction. NO starting point. like the sun
 */
public class DirectionalLight extends Light implements LightSource{

	/**
	 * the directional vector 
	 */
	private Vector direction;

	/**
	 * ctor
	 * @param intensity color
	 * @param direction vector
	 */
	public DirectionalLight(Color intensity, Vector direction) {
		super(intensity);
		this.direction = direction.normalize();
	}
	public Color getIntensity(Point p)
	{
		return getIntensity();
	}
	/**
	 * calculates the direction from the light to the item
	 * @param p point
	 * @returns a vector directional vector
	 */
	public Vector getL(Point p)
	{
		return direction;//.normalize();
	}
	
}
