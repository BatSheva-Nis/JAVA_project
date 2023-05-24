/**
 * 
 */
package lighting;
import primitives.*;

/**
 * @author Rachelli Adler Yael Kahana Batsheva Nissim
 * this class represents all the lights besides ambient light.
 */
 abstract class Light {

	 /**
	  * the intensity of the light( what basic color)
	  */
	 private Color intensity;
	 
	/**
	 * c-tor that gets a color
	 */
	protected Light(Color intensity) {
		this.intensity =intensity;	
	}
	/**
	 * @returns the intensity
	 */
	public Color getIntensity() {
		return intensity;
	}

	

}
