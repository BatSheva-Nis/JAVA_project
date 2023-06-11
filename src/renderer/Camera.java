/**
 * 
 */
package renderer;
import java.util.MissingResourceException;

import geometries.*;
import primitives.*;
import scene.*;


/**
 * Camera class The representation of the camera in relation to the view plan
 * @author Rachelli Adler Yael Kahana Batsheva Nissim
 *
 */
public class Camera {
	
	//Camera location point
	private Point location;
	//direction vectors
	private Vector vTo;
	private Vector vUp;
	private Vector vRight;
	//The length and width of the view plan
	private double height;
	private double width;
	//The camera distance from the view plan
	private double distance;
	
	//field of ImageWriter
	private ImageWriter im;
	//field of RayTracerBase
	private RayTracerBase rtb;
	
	/**
	 * ctor that gets:
	 * @param location a point where the camra is
	 * @param vTo vector
	 * @param vUp vector
	 */
	public Camera(Point location, Vector to, Vector up) {
		
		if(to.dotProduct(up)!= 0)
		   throw new IllegalArgumentException("ERROR: the vectors are not orthoganal ");
		this.location = location;
		this.vTo = to.normalize();
		this.vUp = up.normalize();
		this.vRight =to.crossProduct(up).normalize();
	}
	
	/***
	 * sets the size of the veiw plane
	 * @param width double
	 * @param height double
	 * @returns the camera
	 */
	public Camera setVPSize(double width, double height)
	{
		this.height = height;
		this.width = width;
		return this;
	}
	
	/***
	 * sets the distance of the veiw plane from the camera
	 * @param distance double
	 * @returns the camera
	 */
	public Camera setVPDistance(double distance)
	{
		this.distance =distance;
		return this;
	}
	
	/***
	 * sets the object image writer
	 * @param width double
	 * @param height double
	 * @returns the camera
	 */
	public Camera setImageWriter(ImageWriter im)
	{
		this.im = im;
		return this;
	}
	
	/***
	 * sets the object ray tracer base
	 * @param width double
	 * @param height double
	 * @returns the camera
	 */
	public Camera setRayTracer(RayTracerBase rtb)
	{
		this.rtb = rtb;
		return this;
	}
	
	/***
	 * Building a ray from the camera to the pixel we received for the view plane
	 * @param nX amount of columns
	 * @param nY amount of rows
	 * @param j number column
	 * @param i number row
	 * @returns a ray from the camera to the ij pixel
	 */
	public Ray constructRay(int nX, int nY, int j, int i)
	{
		if(nY != 0 && nX != 0)//no view plane
		{
			//size of 1 pixel
			double ry =height/nY;
			double rx =width/nX;
			
			//the center of the pixel
			Point pIJ = location.add(vTo.scale(distance)); //middle of the view plane
			
			//the amount to move from the middle
			double yI = -1*ry* (i-(nY-1)/2d);
			double xJ = 1*rx* (j-(nX-1)/2d);
			
			//moves from the middle of the view plane
			if (xJ != 0) 
				pIJ = pIJ.add(vRight.scale(xJ));
			if (yI != 0) 
				pIJ = pIJ.add(vUp.scale(yI));
			
			Vector v = pIJ.subtract(location);
			return new Ray(location, v);
		}
		throw new IllegalArgumentException("ERROR: cant devide by 0");
	}
	
	/***
	 * Calculating the color of a pixel in the image and coloring it
	 */
	public Camera renderImage()
	{
//		try {
		if (vTo == null || vUp == null || vRight == null)
			throw new MissingResourceException("ERROR: missing resource", Vector.class.getName(), "");
		if (location == null)
			throw new MissingResourceException("ERROR: missing resource", Point.class.getName(), "");
		if (im== null)
			throw new MissingResourceException("ERROR: missing resource", ImageWriter.class.getName(), "");
		if (rtb == null)
			throw new MissingResourceException("ERROR: missing resource", RayTracerBase.class.getName(), "");
		//throw new UnsupportedOperationException();
		//iterator over the pixels in the image
		for(int i=0 ; i < im.getNy() ; i++)
			for(int j=0 ; j < im.getNx() ; j++)
			{
				Color color = castRay(im.getNx(), im.getNy(),i ,j);
				im.writePixel(j, i, color);
			}
//		}
//		catch(MissingResourceException e) {
//			throw new UnsupportedOperationException("Not implemented yet" + e.getClassName());
//		}
		return this;
	}
	
	/***
	 * Calculating the color of the received pixel by creating a ray
	 * and finding the closest object and its color
	 * @param nX
	 * @param nY
	 * @param j
	 * @param i
	 * @returns the color in which we will paint the pixel
	 */
	 public Color castRay(int nX ,int nY,int j ,int i)
	 {
		 //Creates a ray from the camera to the pixel
		 Ray ray = constructRay(nX, nY, i, j);
		 //Finding the color
		 Color c = rtb.traceRay(ray);
		 return c;
		 
	 }
	
	 /***
	  * Coloring the pixels with the color we got, 
	  * so that each pixel has a size of interval
	  * @param interval
	  * @param color
	  */
	public void printGrid(int interval, Color color)
	{
		if(im == null)
			throw new MissingResourceException("ERROR: missing resource", ImageWriter.class.getName(), "");
		//pixels:
		//columns
		//Interval is the length of each pixel
		for(int i=0; i< im.getNx(); i+=interval)
			for(int j=0; j< im.getNy(); j++)
				im.writePixel(i, j, color);
		//rows
		//Interval is the length of each pixel
		for(int i=0; i< im.getNy(); i+=interval)
			for(int j=0; j< im.getNx(); j++)
				im.writePixel(j, i, color);
		
		writeToImage();
	}
	
	/***
	 * Writing the image to a png file
	 */
	public void writeToImage()
	{
		if(im == null)
			throw new MissingResourceException("ERROR: missing resource", ImageWriter.class.getName(), "");
		im.writeToImage();
	}
	

}
