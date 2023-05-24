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
public class AmbientLight extends Light{

	//The initialized NONE field is black with an attenuation factor of 0
	public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);
	
	
	/**
	 * ctor that sets the intensity from light
	 * @param iA Color
	 * @param kA Double3
	 */
	public AmbientLight(Color iA, Double3 kA )
	{
		super(iA.scale(kA));
		//intensity =iA.scale(kA);//ip = ia*ka
	}
	
	/**
	 * ctor that sets the intensity from light
	 * @param iA color
	 * @param kA double
	 */
	public AmbientLight(Color iA, double kA )
	{
		super(iA.scale(kA));
		//intensity =iA.scale(kA);//ip = ia*ka 
	}
	
}