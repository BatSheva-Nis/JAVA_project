/**
 * 
 */
package renderer;
import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;

import geometries.*;
import primitives.*;
import scene.*;
import java.util.Hashtable;
import renderer.Pixel;

/**
 * Camera class The representation of the camera in relation to the view plan
 * @author Rachelli Adler Yael Kahana Batsheva Nissim
 *
 */
public class Camera {
	
	//*******//
	//Save the pixel's num in the ImageWriter class
	//*******//
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
	
	
	
	//****************************MiniProject1****************************
	
	// number of rays from V.P. to the scene
	int amountRaysColomn=0;
	int amountRaysRow=0;
	

	//****************************MiniProject2****************************
		
	//how many times to do recursive function in adaptive
		private int maxAdaptiveLevel = 2;
		//true if we use adaptive supersampling
		private boolean useAdaptive = false;

		private int threadsCount = 0;
	    private static final int SPARE_THREADS = 2;
	    private boolean print =false;
	    
		
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
	
	  /**
     * function that calculates the pixels location
     *
     * @param nX the x resolution
     * @param nY the y resolution
     * @param i  the x coordinate
     * @param j  the y coordinate
     * @return the ray
     */
    private Point middlePixel(int nX, int nY, int j, int i) 
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
        return pIJ;
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
			Point pIJ = middlePixel(nX, nY , i,j);
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
	
	
	/**
     * function that casts ray and returns color
     *
     * @param nX the x resolution
     * @param nY the y resolution
     * @param j  the x coordinate
     * @param i  the y coordinate
     * @return the color
     */
    private Color castRay(int nX, int nY, int j, int i) {
        if (useAdaptive == true)
        {
        	Point p = middlePixel(nX, nY, i, j);      
            return adaptiveHelper(p, nX, nY);
        }
        else
        {
            return rtb.traceMultiRay(constructMultiRay(nX, nY, i, j));
        }
    }
    
    
//	/***
//	 * Calculating the color of the received pixel by creating a ray
//	 * and finding the closest object and its color
//	 * @param nX
//	 * @param nY
//	 * @param j
//	 * @param i
//	 * @returns the color in which we will paint the pixel
//	 */
//	 public Color castRay(int nX ,int nY,int j ,int i)
//	 {
//		 //Creates a ray from the camera to the pixel
//		 Ray ray = constructRay(nX, nY, i, j);
//		 //Finding the color
//		 Color c = rtb.traceRay(ray);
//		 return c;
//		 
//	 }
	
	 /***
	  * Coloring the pixels with the color we got, 
	  * so that each pixel has a size of interval
	  * @param interval
	  * @param color
	  */
	public Camera printGrid(int interval, Color color)
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
		return this;
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
	
	
	//****************************MiniProject1****************************
	
		/**
		 * @param amountRaysColomn the amountRaysColomn to set
		 */
		public Camera setAmountRaysColomn(int amountRaysColomn) {
			this.amountRaysColomn = amountRaysColomn;
			return this;
		}

		/**
		 * @param amountRaysRow the amountRaysRow to set
		 */
		public Camera setAmountRaysRow(int amountRaysRow) {
			this.amountRaysRow = amountRaysRow;
			return this;
		}
		
	   
		
		/***
		 * Building a rays from the camera to the pixel we received for the view plane
		 * @param nX amount of columns
		 * @param nY amount of rows
		 * @param j number column
		 * @param i number row
		 * @returns a ray from the camera to the ij pixel
		 */
		public List<Ray> constructMultiRay(int nX, int nY, int j, int i)
		{
			if(amountRaysRow <= 0 || amountRaysColomn <= 0)//no set amount of rays then default is 1.
				return List.of(constructRay(nX, nY, j, i));

				List<Ray> lstRays = new LinkedList<>();
				//size of 1 pixel
				double ry =height/nY;//height - orech
				double rx =width/nX;
				
				//the center of the pixel
				Point pIJ = location.add(vTo.scale(distance)); //middle of the view plane
				
				//the amount to move from the middle
				double yI = -1*ry* (i-(nY-1)/2d);//move row (+up, -down)
				double xJ = 1*rx* (j-(nX-1)/2d);//move colomn(+right, -left)

				//moves from the middle of the view plane
				if (xJ != 0) 
					pIJ = pIJ.add(vRight.scale(xJ));
				if (yI != 0) 
					pIJ = pIJ.add(vUp.scale(yI));
				
				
				ry= ry/amountRaysColomn;
				rx = rx/amountRaysRow;
				
				//move within the pixel
				for(int k=0; k< amountRaysRow; k++)//row
				{
					for(int l=0; l<amountRaysColomn;l++)
					{
						Point p =pIJ;
						double yii = -(k-(amountRaysColomn-1)/2d)*ry;
						double xjj = -(l-(amountRaysRow-1)/2d)*rx;
						if (!Util.isZero(yii)) 
							p = p.add(vUp.scale(yii));
						if (!Util.isZero(xjj)) 
							p = p.add(vRight.scale(xjj));
						
						lstRays.add(new Ray(location, p.subtract(location)));
					}
				}
				return lstRays;
		}
		
		
		
		/***
		 * actually colores the pixel!!
		 * Calculating the color of the received pixel by creating a ray
		 * and finding the closest object and its color
		 * @param nX amount of columns
		 * @param nY mount of rows
		 * @param j j number column
		 * @param i j number row
		 */
		 public void castMultiRay(int nX ,int nY,int j ,int i)
		 {
			 //Creates a ray from the camera to the pixel
			 List<Ray> lstRays = constructMultiRay(nX, nY, i, j);
			 //Finding the color
			 Color pixelColor = rtb.traceMultiRay(lstRays);
			 im.writePixel(i, j, pixelColor);
			 
		 }
		 
		 
		 /***
			 * Calculating the color of a pixel in the image and coloring it
			 * mini project 1 casting multi rays. anti alising 
			 */
			public Camera renderMultiImage()
			{
				try {
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
					
						castMultiRay(im.getNx(), im.getNy(),i ,j);//colors the pixel by average
						
					}
				}
				catch(MissingResourceException e) {
					throw new UnsupportedOperationException("Not implemented yet" + e.getClassName());
				}
				return this;
			}

			//****************************MiniProject2****************************

			
			 /**
		     * setter for UseAdaptive
		     * @param useAdaptive- the number of pixels in row/col of every pixel
		     * @return camera itself
		     */
		    public Camera setUseAdaptive(boolean useAdaptive) {
		        this.useAdaptive = useAdaptive;
		        return this;
		    }
		    
		    /**
		     * setter for maxAdaptiveLevel
		     * @param maxAdaptiveLevel- The depth of the recursion
		     * @return camera itself
		     */
		    public Camera setMaxAdaptiveLevel(int maxAdaptiveLevel) {
		        this.maxAdaptiveLevel = maxAdaptiveLevel;
		        return this;
		    }
		    
			 /**
		     * get the point and return the color of the ray to this point
		     *
		     * @param p- point on the view plane
		     * @return color of this point
		     */
		    private Color calcPointColor(Point p) {
		        return rtb.traceRay(new Ray(location, p.subtract(location)));
		    }
		    
		    
			  private Color adaptiveHelper(Point center, double nY, double nX)
			  {
		        Hashtable<Point, Color> pointColorTable = new Hashtable<Point, Color>();
		        double rY = height / nY / 2;
		        double rX = width / nX / 2;
		        Color upRight = calcPointColor(center.add(vUp.scale(rY)).add(vRight.scale(rX)));
		        Color upLeft = calcPointColor(center.add(vUp.scale(rY)).add(vRight.scale(-rX)));
		        Color downRight = calcPointColor(center.add(vUp.scale(-rY)).add(vRight.scale(rX)));
		        Color downLeft = calcPointColor(center.add(vUp.scale(-rY)).add(vRight.scale(-rX)));

		        return adaptive(1, center, rX, rY, pointColorTable, upLeft, upRight, downLeft, downRight);
		    }
			  /**
			     * recursive method that return the average color of the pixel- by checking the color of the four corners
			     *
			     * @param max-         the depth of the recursion
			     * @param center-      the center of the pixel
			     * @param rX-          the width of the pixel
			     * @param rY-          the height of the pixel
			     * @param upLeftCol-   the color of the vUp left corner
			     * @param upRightCol-  the color of the vUp vRight corner
			     * @param downLeftCol- the color of the down left corner
			     * @param downRightCol - the color of the down vRight corner
			     * @return the average color of the pixel
			     */
			    private Color adaptive(int max, Point center, double rX, double rY, Hashtable<Point, Color> pointColorTable,
			                           Color upLeftCol, Color upRightCol, Color downLeftCol, Color downRightCol) {
			        if (max == maxAdaptiveLevel) {
			            return downRightCol.add(upLeftCol).add(upRightCol).add(downLeftCol).reduce(4);
			        }
			        if (upRightCol.equals(upLeftCol) && downRightCol.equals(downLeftCol) && downLeftCol.equals(upLeftCol))
			            return upRightCol;
			        else {
			            Color rightPCol = getPointColorFromTable(center.add(vRight.scale(rX)), pointColorTable);
			            Color leftPCol = getPointColorFromTable(center.add(vRight.scale(-rX)), pointColorTable);
			            Color upPCol = getPointColorFromTable(center.add(vUp.scale(rY)), pointColorTable);
			            Color downPCol = getPointColorFromTable(center.add(vUp.scale(-rY)), pointColorTable);
			            Color centerCol = calcPointColor(center);

			            rX = rX / 2;
			            rY = rY / 2;
			            upLeftCol = adaptive(max + 1, center.add(vUp.scale(rY / 2)).add(vRight.scale(-rX / 2)), rX, rY, pointColorTable,
			                    upLeftCol, upPCol, leftPCol, centerCol);
			            upRightCol = adaptive(max + 1, center.add(vUp.scale(rY / 2)).add(vRight.scale(rX / 2)), rX, rY, pointColorTable,
			                    upPCol, upRightCol, centerCol, leftPCol);
			            downLeftCol = adaptive(max + 1, center.add(vUp.scale(-rY / 2)).add(vRight.scale(-rX / 2)), rX, rY, pointColorTable,
			                    leftPCol, centerCol, downLeftCol, downPCol);
			            downRightCol = adaptive(max + 1, center.add(vUp.scale(-rY / 2)).add(vRight.scale(rX / 2)), rX, rY, pointColorTable,
			                    centerCol, rightPCol, downPCol, downRightCol);
			            return downRightCol.add(upLeftCol).add(upRightCol).add(downLeftCol).reduce(4);
			        }
			    }

			    /**
			     * check if this point exist in the HashTable return his color. otherwise calculate the color and save it
			     *
			     * @param point-           certain point in the pixel
			     * @param pointColorTable- dictionary that save points and their color
			     * @return the color of the point
			     */
			    private Color getPointColorFromTable(Point point, Hashtable<Point, Color> pointColorTable)
			    {
			        if (!(pointColorTable.containsKey(point)))
                    {
			            Color color = calcPointColor(point);
			            pointColorTable.put(point, color);
			            return color;
			        }
			        return pointColorTable.get(point);
			    }
			    

			    public Camera setMultithreading(int threads)
			    {
			        if (threads < 0)
			            throw new IllegalArgumentException("Multithreading parameter must be 0 or higher");
			        if (threads != 0)
			            this.threadsCount = threads;
			        else {
			            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
			            this.threadsCount = cores <= 2 ? 1 : cores;
			        }
			        return this;
			    }

//			    
//			    Pixel.initialize(nY, nX, printInterval);
//			    while (threadsCount-- > 0) {
//			     new Thread(() -> {
//			     for (Pixel pixel = new Pixel(); pixel.nextPixel(); Pixel.pixelDone())
//			     castRay(nX, nY, pixel.col, pixel.row);
//			     }).start();
//			    }
//			    Pixel.waitToFinish();

			    private void ImageThreaded()
			    {
			        final int nX = im.getNx();
			        final int nY = im.getNy();
			                      
			    Pixel.initialize(nX, nY, 5);

			            Pixel p = new Pixel();
			            p.print=print;
			            
			            
			            
			            
			            
			            //create thread foreach ray calculation
			            Thread[] threads = new Thread[threadsCount];
			            for (int i = threadsCount - 1; i >= 0; --i) {
			                threads[i] = new Thread(() -> {
			                    while (p.nextPixel()!=null) {
			                     castRay(nX, nY, p.col(), p.row());
			                        p.pixelDone();
			                    }                
			                });
			                threads[i].start();                
			            }
			      
			    for (Thread thread : threads)
			            try {
			                thread.join();
			            } catch (Exception e) {
			            }
			    if (print)
			            System.out.print("\r100%");
			    }
			    
			    public Camera setDebugPrint() {
			        print = true;
			        return this;
			    } 
}