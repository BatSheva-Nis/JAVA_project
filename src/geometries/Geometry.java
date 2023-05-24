package geometries;
import primitives.*;

/**
 * interface geometry is the basic behavior of all Geometries in Cartesian 3-Dimensional coordinate system
 *
 * @author Batsheva Nissim and Yael Kahana and Rachelli Adler
 */
public abstract class Geometry extends Intersectable {

	/**
	 * the color emission default black
	 */
	protected Color emission = Color.BLACK;
	
	/**
	 * the shapes material.(is it glass? is it a mirror?)
	 */
	private Material material =new Material();
	
	/**
	 * getter emission.
	 * @returns the emission.
	 */
    public Color getEmission() {
		return emission;
	}
    
	/**
	 * setter of emission.
	 * @returns the Geometry itself and sets the emission.
	 */
    public Geometry setEmission(Color e) {
    	this.emission = e;
		return this;
	}

	/**
     * return a normal vector to the geometry at the given point
     *
     * @param point point on the geometry
     * @return normal vector
     */
    public abstract Vector getNormal(Point point);

	/**
	 * @returns the material
	 */
	public Material getMaterial() {
		return material;
	}

	/**
	 * @param material the material to set
	 * returns the geometry itself
	 */
	public Geometry setMaterial(Material material) {
		this.material = material;
		return this;
	}



	
}
