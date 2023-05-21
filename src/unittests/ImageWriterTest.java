/**
 * 
 */
package unittests;

import static org.junit.jupiter.api.Assertions.*;
import renderer.*;
import primitives.*;
import org.junit.jupiter.api.Test;

/**
 * @author Rachelli Adler Batsheva Nissim Yael Kahana 
 *
 */
class ImageWriterTest {

	/**
	 * Test method for {@link renderer.ImageWriter#ImageWriter(java.lang.String, int, int)}.
	 */
	@Test
	void testImageWriter() {
		
		ImageWriter im;
		im =new ImageWriter("my picture",801,501);
		
		Color cYellow = new Color(255, 255, 0);
		Color cRed = new Color(255,0, 0);
		for(int i=0 ; i < im.getNy() ; i++)
			for(int j=0 ; j < im.getNx() ; j++)
				im.writePixel(j, i, cYellow);
		//pixels
		//columns
		for(int i=0; i< im.getNx(); i+= (im.getNx()/16))
			for(int j=0; j< im.getNy(); j++)
				im.writePixel(i, j, cRed);
		//rows
		for(int i=0; i< im.getNy(); i+= (im.getNy()/10))
			for(int j=0; j< im.getNx(); j++)
				im.writePixel(j, i, cRed);
		
		
		im.writeToImage();
	}

//	/**
//	 * Test method for {@link renderer.ImageWriter#writeToImage()}.
//	 */
//	@Test
//	void testWriteToImage() {
//		//fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link renderer.ImageWriter#writePixel(int, int, primitives.Color)}.
//	 */
//	@Test
//	void testWritePixel() {
//		//fail("Not yet implemented");
//	}

}