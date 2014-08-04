package ray_tracing;

import java.awt.image.*;
import java.util.*;
import java.awt.*;

import geometries.*;
import utilities.*;

public class RayTracer {
	// the texture for the textured rectangle
	private BufferedImage screen=null;
	// the objects in the scene
	private ArrayList geometries=new ArrayList();
	// this application only consider single light source
	private Vector3 lightSource;
	// eye and world coordinate
	private CoordinateSystem eyeCoordinateSystem=null;
	// the screen
	private Vector3 pixels[][]=null;
	
	// the constructor sets up the coordinate system
	public RayTracer(int width, int height, Vector3 eye, 
			Vector3 lookat, Vector3 up){
		setUpView(width,height,eye,lookat,up);
	}
	
	public void addGeometry(Geometry object){
		geometries.add(object);
	}
	
	public void removeGeometry(Geometry object){
		geometries.remove(object);
	}
	
	public void setLightSource(Vector3 light){
		lightSource=light;
	}
	
	private void setBackground(int rgb){
		for(int i=0;i<screen.getWidth();i++)
			for(int j=0;j<screen.getHeight();j++){
				screen.setRGB(i,j,rgb);
			}
	}
	
	/*
	 * This method updates the screen. It calls rayTrace().
	 */
	public void update(){
		for(int i=0;i<screen.getWidth();i++)
			for(int j=0;j<screen.getHeight();j++){
				
				/* insert your code here */
					// create the ray that begins at the eye and
					// points to pixel (i,j)
				Vector3 eye=eyeCoordinateSystem.getEye();
				Vector3 x = pixels[i][j];
				Ray r = new Ray (eye, Vector3.subtract(x,eye)); 

				Color color= rayTrace(r,null);// skipping nothing when tracing
				screen.setRGB(i,screen.getHeight()-1-j,color.getRGB());
			}
	}
	
	/*
	 * Parameter "skip" means an object which will be ignored when tracing
	 * the ray. Sometimes, a ray begins at an object in the scene, then this object should
	 * not be considered when finding intersection. "skip" being null means that
	 * it begins at the eye position
	 */
	private Color rayTrace(Ray r, Geometry skip){
		Intersection inter=getNearestPoint(r,skip);

		if(inter==null){
			if(skip==null)
				return new Color(50,50,50); // background color
			else
				return new Color(0,0,0); // black
		}
		
		
		switch(inter.geom.getSurfaceType()){
			case Geometry.DIFFUSE:
				return diffuse(inter.intersection, inter.geom);
			case Geometry.SPECULAR:
				Ray r1 =Geometry.reflectRay(r, inter.intersection, 
										inter.geom.getNormal(inter.intersection));
				return rayTrace(r1, inter.geom);
			case Geometry.TRANSPARENT:
				Ray r2 = Geometry.refractRay(r, inter.intersection, inter.geom.getNormal(inter.intersection), 
												inter.geom.getRefractionIndex());	
				Color c4 = rayTrace(r2, inter.geom);
				return Utility.weaken(c4, inter.geom.getRefractionIndex());
			default:
				return new Color(0,0,0);
		}
	}
	
	private Color diffuse(Vector3 point, Geometry onSurface){
			
		Color color;
		Color tempColor=null;
		if(onSurface instanceof TexturedRectangle3D){
			tempColor=((TexturedRectangle3D)onSurface).
					getColor(point);
		}else{
			tempColor=onSurface.getColor();
		}
	
		Vector3 normal=onSurface.getNormal(point);
		color=Geometry.diffuseColor(tempColor,normal,point,
				lightSource,onSurface.getDiffuse());
		return color;
	}
	
	/*
	 * Please look at the comments for method rayTrace to understand
	 * what parameter "skip" means
	 */
	private Intersection getNearestPoint(Ray r, Geometry skip){
		double min=1e+10;
		Geometry nearestObj=null;
		Vector3 nearestIntersection=null;
		Vector3 eye=eyeCoordinateSystem.getEye();
		Iterator iterator=geometries.iterator();
		double current;
		while(iterator.hasNext()){
			Geometry obj=(Geometry)iterator.next();
			
			if(obj==skip){
				continue;
			}
			
			/* insert your code here */
			Vector3 Intersection = obj.intersectRay(r);
			if (Intersection != null) {
				current = Vector3.subtract(Intersection, r.getOrig()).norm();
				if (current < min){
					min = current;
					nearestObj = obj;
					nearestIntersection = Intersection;
				}
			}
		}
	
		if(nearestObj!=null)	
			return new Intersection(nearestIntersection, nearestObj);
		else
			return null;
	}
	
	public void setUpView(int width, int height, Vector3 eye, 
			Vector3 lookat, Vector3 up){
		// width and height are the dimensions of the  screen
		// width # of columns height is # of rows
		if(screen==null || screen.getHeight()!=height || screen.getWidth()!=width){
			// this change only happens when the 
			// image size is changed
			screen=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			pixels=new Vector3[width][height];
		}
		eyeCoordinateSystem=new CoordinateSystem(eye, lookat, up);
		
		// initialize the pixel positions in the eye reference basis,
		// the screen is (-2,-2) by (2,2)
		// then convert the pixel's coordinates in the eye reference basis to 
		// their world coordinates
		double top=2, bottom=-2, right=2, left=-2;
		double viewPlane=-4; // from the eye point
				     // the center of the screen 
				     // is at the opposite of w axis 
		
		/* insert your code here */
		double pixelSizeX = 4.0/width;
		double pixelSizeY = 4.0/height;
		
		for (int i = 0; i<width; i++){
			for (int j = 0; j<height; j++){
				Vector3 temp = new Vector3((-2+i*pixelSizeX),(-2+j*pixelSizeY), -4);
				temp = eyeCoordinateSystem.convertToWorld(temp);
				pixels[i][j] = temp;
			}
		}
	}
	
	public BufferedImage getScreen(){
		return screen;
	}
	
	private class Intersection{
		public Vector3 intersection;
		public Geometry geom;
		public Intersection(Vector3 i, Geometry g){
			intersection = i;
			geom=g;
		}
	}
}
