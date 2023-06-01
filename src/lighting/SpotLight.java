/**
 * 
 */
package lighting;
import primitives.*;
import java. lang. math;
/**
 * @author Rachelli Adler Yael Kahana Batsheva Nissim
 *this class represents light that has a starting point and has a direction. like a flashlight.
 */
public class SpotLight extends PointLight{
	/**
	 * the direction of the light
	 */
	private Vector direction;

	/**
	 * ctor
	 * @param intensity color
	 * @param position point
	 * @param direction vector
	 */
	public SpotLight(Color intensity, Point position, Vector direction) {
		super(intensity, position);
		this.direction = direction.normalize();
	}
	/**
	 * gets the intensity of the point
	 * @param p point
	 * @returns the intensity color
	 */
	@Override
	public Color getIntensity(Point p)
	{
      Color father = super.getIntensity(p);
      return  father.scale(Math.max(0,direction.dotProduct(getL(p))));
	}
}
