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
    private double kC =1;
    private double kL =0;
    private double kQ =0;
	/**
	 * @param kC the kC to set
	 */
	public PointLight setkC(double kC) {
		this.kC = kC;
		return this;
	}
	/**
	 * @param kL the kL to set
	 */
	public PointLight setkL(double kL) {
		this.kL = kL;
		return this;
	}
	/**
	 * @param kQ the kQ to set
	 */
	public PointLight setkQ(double kQ) {
		this.kQ = kQ;
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
	
    
    
}
