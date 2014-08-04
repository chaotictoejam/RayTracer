package geometries;

import java.awt.*;

public abstract class Geometry {
	public final static int DIFFUSE=1;
	public final static int SPECULAR=2;
	public final static int TRANSPARENT=4;
	
	private double refractionRadio; // how much intensity of the light 
							 // that can travel through
	private double refractionIndex; // ratio of sin_theta1 and sin_theta2
	private double diffuseReflectance; // how much intensity will be reflected diffusely

	private int surfaceType; // DIFFUSE, SPECULAR, TRANSPARENT
	
	private Color color; // The rgb under the light sources, whose intensity
	                     // is uniform
	
	public double getRefractionRadio(){
		return refractionRadio;
	}
	
	public void setRefractionRadio(double r){
		refractionRadio=r;
	}
		
	public Color getColor(){
		return color;
	}
	
	public void setColor(Color c){
		color=c;
	}
	
	public int getSurfaceType(){
		return surfaceType;
	}
	
	public void setSurfaceType(int t){
		surfaceType=t;
	}

	public double getRefractionIndex(){
		return refractionIndex;
	}
	
	public void setRefraction(double r){
		refractionIndex=r;
	}

	public double getDiffuse(){
		return diffuseReflectance;
	}
	
	public void setDiffuse(double d){
		diffuseReflectance=d;
	}
		
	public abstract Vector3 intersectRay(Ray r);
	public abstract Vector3 getNormal(Vector3 p);
	
	public static Ray reflectRay(Ray r, Vector3 intersection, Vector3 normal){
		
		Vector3 unitNormal = normal.unit();
		double tempD = 2*(Vector3.dot(unitNormal, r.getDirection()));
		Vector3 tempV = Vector3.multi(unitNormal, tempD);
		Vector3 reflection = Vector3.subtract(r.getDirection(), tempV);
		Ray r1 = new Ray(intersection, reflection); 
        return r1;
	}

	//This method calculates the direction of the refracted ray
	
	public static Ray refractRay(Ray r, Vector3 intersection, Vector3 normal, double refractionIndex){
		//theta1 is between r's direction and the normal
		//theta2 is between refraction's direction and the normal
		
		Vector3 unitNormal = normal.unit();
		Vector3 ray = r.getDirection();
		double cos_theta1= -(Vector3.dot(unitNormal, ray)/(ray.norm()));
		double sin_theta1 = Math.sqrt(1-cos_theta1*cos_theta1);
		double sin_theta2 = sin_theta1/refractionIndex;
		double cos_theta2 = Math.sqrt(1-sin_theta2*sin_theta2);
		Vector3 negUnitNormal = Vector3.multi(unitNormal, -1);
		Vector3 projection = Vector3.multi(negUnitNormal, (ray.norm()*cos_theta2));
		Vector3 temp = Vector3.multi(unitNormal, Vector3.dot(unitNormal, ray));
		temp = Vector3.subtract(ray, temp);
		Vector3 lowerHorizontal = Vector3.multi(temp, 1/refractionIndex);
		Vector3 refraction = Vector3.add(lowerHorizontal, projection);
		Ray r2 = new Ray(intersection, refraction); 
        return r2;
	}
	
	/*
	 * Returns the diffuse color.
	 * Parameters:
	 * 	objectColor: the object color under white light
	 * 	normal: object normal at intersection
	 * 	intersection: the point which this method calculates color for
	 * 	lightSource: single point light source
	 * 	diffuseConstant: how much incoming luminance will be reflected  
	 */
	public static Color diffuseColor(Color objectColor, Vector3 normal, Vector3 intersection,
			Vector3 lightSource, double diffuseConstant){
		
		double costheta = -Vector3.dot(normal, Vector3.subtract(lightSource, intersection))/ Vector3.subtract(lightSource, intersection).norm();
		double luminance = diffuseConstant*costheta;
		int r = (int)(objectColor.getRed()*luminance);
		int g = (int)(objectColor.getGreen()*luminance);
		int b = (int)(objectColor.getBlue()*luminance);
		
		return new Color (r, g, b);
		
	}
	
}
