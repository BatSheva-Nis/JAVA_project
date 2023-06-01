/**
 * 
 */
package scene;
import lighting.AmbientLight;
import primitives.*;
import geometries.*;
import lighting.*;
import java.util.*;

/**
 * @author Rachelli Adler Batsheva Nissim Yael Kahana 
 *
 */
public class Scene {

   public String name;
   public Color background = Color.BLACK;
   public AmbientLight ambient = AmbientLight.NONE;
   public Geometries geometries = new Geometries();
   public List<LightSource> lights = new LinkedList<>(); //a list of all the lights in the scene
   
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
    * returns the scene itself
    */
   public Scene setBackground(Color background) {
	   this.background = background;
	   return this;
   }


   /**
    * @param ambientLight the ambientLight to set
    * returns the scene itself
    */
   public Scene setAmbientLight(AmbientLight ambientLight) {
	   this.ambient = ambientLight;
	   return this;
   }	

   /**
    * @param geometries the geometries to set
    * returns the scene itself
    */
   public Scene setGeometries(Geometries geometries) {
	   this.geometries = geometries;
	   return this;
   }


/**
 * @param lights the lights to set
 * returns the scene itself
 */
public Scene setLights(List<LightSource> lights) {
	this.lights = lights;
	return this;
}
   
   
}