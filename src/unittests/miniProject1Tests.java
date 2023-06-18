package unittests;
import geometries.*;
import lighting.AmbientLight;
import lighting.SpotLight;

import static java.awt.Color.BLUE;
import static java.awt.Color.GREEN;
import static java.awt.Color.RED;
import static java.awt.Color.WHITE;

import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

/**
 * this is our first image
 * we create a sunrise scene
 * including grass, birds and the sun
 * using plane, sphere and triangles
 */
public class miniProject1Tests {
	  @Test
	   public void allFeature() {
	       Camera camera = new Camera(new Point(0, 0, 5000),
	               new Vector(0, 0, -1), new Vector(0, 1, 0)) //
	               .setVPSize(200, 200).setVPDistance(1000);

	       Scene scene = new Scene("Test scene")
	               .setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)))
	               .setBackground(new Color(94, 97, 99));

	       scene.geometries.add( //
	               new Triangle(new Point(450, -350, 0),
	                       new Point(-500, -400, 0), new Point(0, 500, -80)) //
	                       .setEmission(new Color(75, 75, 75)) //
	                       .setMaterial(new Material().setKr(1).setKs(0.1).setShininess(5)),//
	               new Sphere(new Point(-100, 100, 300), 100d).setEmission(new Color(BLUE)) //
	                       .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(30).setKt(0.6)),//
	               new Sphere(new Point(100, -200, 400), 100).setEmission(new Color(RED)) //
	                       .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10).setKt(0.8)),//
	               new Sphere(new Point(200, 100, 800), 50d).setEmission(new Color(GREEN)) //
	                       .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(20).setKt(0.3)));

	       scene.lights.add(new SpotLight(new Color(700, 400, 400),
	               new Point(0, 0, 900), new Vector(1.5, 2.5, -3.5)) //
	               .setKl(4E-5).setKq(2E-7));

	       ImageWriter imageWriter = new ImageWriter("allFeature", 2000, 2000);
	       camera.setImageWriter(imageWriter) //
	               .setRayTracer(new RayTracerBasic(scene)) //
	               .setAmountRaysColomn(3)
	               .setAmountRaysRow(3)
	               .renderMultiImage() //
	               .writeToImage();
	   }
}
