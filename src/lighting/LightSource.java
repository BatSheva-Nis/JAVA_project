/**
 * 
 */
package lighting;
import primitives.*;

/**
 * @author Rachelli Adler Yael Kahana Batsheva Nissim
 *this interface is for representing the light sources functions
 */
public interface LightSource {
	/**
	 * gets the intensity of the point
	 * @param p point
	 * @returns the intensity color
	 */
	public Color getIntensity(Point p);
	/**
	 * calculates the direction from the light to the item
	 * @param p point
	 * @returns a vector directional vector
	 */
	public Vector getL(Point p);
}
