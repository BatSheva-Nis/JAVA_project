/**
 * 
 */
package lighting;

import primitives.Color;

import org.junit.Test.None;

import primitives.*;

/**
 * A class for ambient light
 * @author Rachelli Adler Batsheva Nissim Yael Kahana 
 *
 */
public class AmbientLight {
	//Color intensity
	private Color intensity;//iP
	//The initialized NONE field is black with an attenuation factor of 0
	public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);
	
	
	/**
	 * @returns the intensity
	 */
	public Color getIntensity() {
		return intensity;
	}
	
	/**
	 * ctor that sets the intensity
	 * @param iA Color
	 * @param kA Double3
	 */
	public AmbientLight(Color iA, Double3 kA )
	{
		intensity =iA.scale(kA);//ip = ia*ka
	}
	
	/**
	 * ctor that sets the intensity
	 * @param iA color
	 * @param kA double
	 */
	public AmbientLight(Color iA, double kA )
	{
		intensity =iA.scale(kA);//ip = ia*ka 
	}
	
}