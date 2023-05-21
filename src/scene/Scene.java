/**
 * 
 */
package scene;
import lighting.AmbientLight;
import primitives.*;
import geometries.*;


/**
 * @author Rachelli Adler Batsheva Nissim Yael Kahana 
 *
 */
public class Scene {

   public String name;
   public Color background = Color.BLACK;
   public AmbientLight ambient = AmbientLight.NONE;
   public Geometries geometries =new Geometries();
   
   /**
    * ctor that sets just the name
    * @param name string
    */
   public Scene(String name)
   {
	   this.name = name;
   }


/**
 * @param background the background to set
 */
public Scene setBackground(Color background) {
	this.background = background;
	return this;
}


/**
 * @param ambientLight the ambientLight to set
 */
public Scene setAmbientLight(AmbientLight ambientLight) {
	this.ambient = ambientLight;
	return this;
}

/**
 * @param geometries the geometries to set
 */
public Scene setGeometries(Geometries geometries) {
	this.geometries = geometries;
	return this;
}
   
}