/**
 * 
 */
package renderer;
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
		this.height =height;
		this.width =width;
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
	public Camera setIm(ImageWriter im)
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
	public Camera setRtb(RayTracerBase rtb)
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
		
	}
	public void printGr(int interval, Color color)
	{
		
	}

}
