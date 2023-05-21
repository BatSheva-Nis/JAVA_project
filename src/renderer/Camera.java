/**
 * 
 */
package renderer;
import java.util.MissingResourceException;

import geometries.*;
import primitives.*;
import scene.*;


/**
 * @author Rachelli Adler Yael Kahana Batsheva Nissim
 *
 */
public class Camera {
	
	private Point location;
	private Vector vTo;
	private Vector vUp;
	private Vector vRight;
	private double height;
	private double width;
	private double distance;
	
	private ImageWriter im;
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
	 * 
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
	
	public void renderImage()
	{
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
	}
	
	 public Color castRay(int nX ,int nY,int j ,int i)
	 {
		 Ray ray = constructRay(nX, nY, i, j);
		 Color c = rtb.traceRay(ray);
		 return c;
		 
	 }
	
	public void printGrid(int interval, Color color)
	{
		if(im == null)
			throw new MissingResourceException("ERROR", null, null);
		//pixels
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
		im.writeToImage();
	}
	
	public void writeToImage()
	{
		if(im == null)
			throw new MissingResourceException("ERROR", null, null);
		im.writeToImage();
	}
	

}
