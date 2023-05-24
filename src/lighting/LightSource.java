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
	 * gets the intensity
	 * @param p point
	 * @returns the intensity color
	 */
	public Color getIntensity(Point p);
	/**
	 * 
	 * @param p point
	 * @returns a vector
	 */
	public Vector getL(Point p);
}
