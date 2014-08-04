package geometries;

import utilities.*;

public class Sphere extends Geometry{
	private Vector3 center;
	private double radius;
	
	public Sphere(){
		center=null;
		radius=0;
	}
	
	public Sphere(Vector3 center, double radius){
		this.center=center;
		this.radius=radius;
	}
	
	public void setCenter(Vector3 c){
		center=c;
	}
	
	public void setR(double r){
		radius=r;
	}
	
	public Vector3 getCenter(){
		return center;
	}
	
	public double getR(){
		return radius;
	}
	
	public Vector3 getNormal(Vector3 p){
		if(Utility.equals(Vector3.subtract(p,center).norm(),radius)){
			return Vector3.subtract(p,center).unit();
		}else{
			System.out.println("p not at edge of sphere (getNormal of sphere).");
			return null;
		}
	}
			
	public Vector3 intersectRay(Ray r){
		
		double a = Vector3.dot(r.getDirection(), r.getDirection());
		double b = 2*Vector3.dot(r.getDirection(), (Vector3.subtract(r.getOrig(), center)));
		double c = Vector3.subtract(r.getOrig(), center).norm()
						*Vector3.subtract(r.getOrig(), center).norm()-radius*radius;
		double t1 = (-b+Math.sqrt(b*b-4*a*c))/(2*a);
		double t2 = (-b-Math.sqrt(b*b-4*a*c))/(2*a);
		
		if (Math.sqrt(b*b-4*a*c)<0){
			return null;
		}
		else if(t1<0 && t2<0){
			return null;
		}
		else if(t1<0 && t2>0|t1>0 && t2<0){
			return null;
		}
		else{
			double t = Math.min(t1, t2);
			return Vector3.add(r.getOrig(),Vector3.multi(r.getDirection(), t));
		}
		
		
		
	}
}
