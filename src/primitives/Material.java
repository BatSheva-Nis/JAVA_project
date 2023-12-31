/**
 * 
 */
package primitives;

/**
 * @author Rachelli Adler Yael Kahana Batsheva Nissim
 *this class represents the material of the shape( mirror or glass)
 */
public class Material {

	/**
	 * Attenuation coefficients of difuse color
	 */
	public Double3 kD = Double3.ZERO;
	/**
	 * Attenuation coefficients of specular color
	 */
	public Double3 kS =Double3.ZERO;
	
	/**
	 * Attenuation coefficients of transparency
	 * מקדם הנחתה עבור שקיפות 
	 */
	public Double3 kT = Double3.ZERO;
	/**
	 * Attenuation coefficients of reflection
	 * מקדם הנחתה עבור השתקפות
	 */
	public Double3 kR =Double3.ZERO;
	public int nShininess =0;
	
	/**
	 * @param kD the kD to set- Double3
	 * @returns the Material itself
	 */
	public Material setKd(Double3 kD) {
		this.kD = kD;
		return this;
	}
	/**
	 * @param kS the kS to set- Double3
	 * @returns the Material itself
	 */
	public Material setKs(Double3 kS) {
		this.kS = kS;
		return this;
	}
	/**
	 * @param nShininess the nShininess to set -int
	 * @returns the Material itself
	 */
	public Material setShininess(int nShininess) {
		this.nShininess = nShininess;
		return this;
	}
	
	
	/**
	 * @param kD the kD to set -double
	 * @returns the Material itself
	 */
	public Material setKd(double kD) {
		this.kD = new Double3(kD);
		return this;
	}
	/**
	 * @param kS the kS to set -double
	 * @returns the Material itself
	 */
	public Material setKs(double kS) {
		this.kS = new Double3(kS);
		return this;
	}
	
	////new stage_7////
	
	/**
	 * @param kT the kT to set -double
	 * @returns the Material itself
	 */
	public Material setKt(Double3 kT) {
		this.kT = kT;
		return this;
	}
	
	/**
	 * @param kR the kR to set -double
	 * @returns the Material itself
	 */
	public Material setKr(Double3 kR) {
		this.kR = kR;
		return this;
	}
	
	/**
	 * @param kT the kT to set -double
	 * @returns the Material itself
	 */
	public Material setKt(double kT) {
		this.kT = new Double3(kT);
		return this;
	}
	
	/**
	 * @param kR the kR to set -double
	 * @returns the Material itself
	 */
	public Material setKr(double kR) {
		this.kR = new Double3(kR);
		return this;
	}
	
	
	
}
