package geometries;

import java.io.*;
import java.net.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.*;

public class TexturedRectangle3D extends Rectangle3D{

	private BufferedImage texture;
	
	public TexturedRectangle3D(){
		super();
	}
	
	// points are in clockwise order, p0 is the bottom-left corner
	public TexturedRectangle3D(Vector3 p1, Vector3 p2, Vector3 p3, 
			Vector3 p4, String path){
		super(p1,p2,p3,p4);
		/*File inputFile=new File(path);
		try{
			texture=ImageIO.read(inputFile);
		}catch(IOException e){
			System.out.println("File "+path
					+" does not exist or is corrupted.");
			System.exit(1);
		}*/
		try{
		    URL                url; 
		    URLConnection      urlConn; 
		    DataInputStream    dis;

		    url = new URL(path);

		    // Note:  a more portable URL: 
		    //url = new URL(getCodeBase().toString() + "/test.jpg");

		    urlConn = url.openConnection(); 
		    urlConn.setDoInput(true); 
		    urlConn.setUseCaches(false);

		    dis = new DataInputStream(urlConn.getInputStream()); 
		    
		    texture=ImageIO.read(url);
		   
		 }

		 catch (MalformedURLException mue) {
			 
		 } 
		 catch (IOException ioe) {
			 
		 } 
	} 

	
	/*
	 * If the rectangle encloses p, map this point to the texture and 
	 * return the corresponding color; otherwise, return null.
	 */
	public Color getColor(Vector3 p){
		if(encloses(p)){
			Vector3 v1 = Vector3.subtract(points[1], points[2]);
			Vector3 v2 = Vector3.subtract(points[1], points[0]);
			Vector3 v3 = Vector3.subtract(points[1], p);
			double x = Vector3.dot(v1, v3)/v1.norm();
			double y = Vector3.dot(v2, v3)/v2.norm();
		
			double horizontalPercentage = x/v1.norm();
			double verticalPercentage = y/v2.norm();
		
			int u=(int)(horizontalPercentage*texture.getWidth());
			int v=(int)(verticalPercentage*texture.getHeight());
			int rgb=texture.getRGB(u,v);
			java.awt.Color color=new java.awt.Color(rgb,true);
			return color;
		}
		else{
			return null;
		}
	}

	public static void main(String args[]){
		Vector3 p0=new Vector3(-1.5,-1,-0.5);
		Vector3 p1=new Vector3(-1.5,-1,-3.0);
		Vector3 p2=new Vector3(1.5,-1,-3.0);
		Vector3 p3=new Vector3(1.5,-1,-0.5);
		
		TexturedRectangle3D rect=
			new TexturedRectangle3D(p0,p1,p2,p3,
					"C:\\test.bmp");
		
		// center: (0,-0.5,-2.0)
		// radius: 0.5
		Sphere sphere=new Sphere(new Vector3(0,-0.5,-2.0),0.5);
		
		/* Test case I */
		System.out.println("*** Test Case I ***");
		Ray r1=new Ray(new Vector3(-0.266667,1.576513,0.806597),
				new Vector3(-0.266667,-2.423487,-3.193403));
		Ray r2=new Ray(new Vector3(-0.495360,-0.501871,-1.932068),
				new Vector3(-0.584908,-2.424688,-3.149760));
		
		Vector3 intersection1=sphere.intersectRay(r1);
		Vector3 intersection2=rect.intersectRay(r2);
		Color color=rect.getColor(intersection2);
		
		System.out.println("r1 intersects the sphere @ "+intersection1);
		System.out.println("r2 intersects the rect @ "+intersection2);
		System.out.println("The color @ "+intersection2+" is "+color);

		/* Test case II */
		System.out.println("\n*** Test Case II ***");
		Ray r3=new Ray(new Vector3(-0.207,1.692,0.706),
				new Vector3(-0.207,-2.308,-3.294));
		Ray r4=new Ray(new Vector3(-0.893,1.008,1.301),
				new Vector3(-0.893,-2.992,-2.699));
		
		intersection1=sphere.intersectRay(r3);
		intersection2=rect.intersectRay(r4);
		color=rect.getColor(intersection2);
		
		System.out.println("r3 intersects the sphere @ "+intersection1);
		System.out.println("r4 intersects the rect @ "+intersection2);
		System.out.println("The color @ "+intersection2+" is "+color);
		
	}
}
