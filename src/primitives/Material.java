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
	 * amount of 
	 */
	public Double3 kD = Double3.ZERO;
	public Double3 kS =Double3.ZERO;
	public int nShininess =0;
	
	/**
	 * @param kD the kD to set- Double3
	 * @returns the Material itself
	 */
	public Material setkD(Double3 kD) {
		this.kD = kD;
		return this;
	}
	/**
	 * @param kS the kS to set- Double3
	 * @returns the Material itself
	 */
	public Material setkS(Double3 kS) {
		this.kS = kS;
		return this;
	}
	/**
	 * @param nShininess the nShininess to set -int
	 * @returns the Material itself
	 */
	public Material setnShininess(int nShininess) {
		this.nShininess = nShininess;
		return this;
	}
	
	
	/**
	 * @param kD the kD to set -double
	 * @returns the Material itself
	 */
	public Material setkD(double kD) {
		this.kD = new Double3(kD);
		return this;
	}
	/**
	 * @param kS the kS to set -double
	 * @returns the Material itself
	 */
	public Material setkS(double kS) {
		this.kS = new Double3(kS);
		return this;
	}
	
	
	
}
