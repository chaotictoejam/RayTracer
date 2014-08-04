package geometries;

import utilities.*;

public class Plane extends Geometry{
	// normal*point=d
	private Vector3 normal;
	private double d;
	
	public Plane(double a, double b, double c, double d){
		normal = new Vector3(a,b,c);
		this.d=d;
	}
	
	public Plane(Vector3 p1, Vector3 p2, Vector3 p3){
		Vector3 v1 = Vector3.subtract(p1, p2);
		Vector3 v2 = Vector3.subtract(p1, p3);
		Vector3 v3 = Vector3.cross(v1, v2);
		normal = v3.unit();
		d = Vector3.dot(normal, p1);
	}
	
	public Plane(Vector3 normal, double d){
		this.normal=normal;
		this.d=d;
	}
	
	public Plane(){
		normal = null;
		d = 0;
	}
	
	public double getD(){
		return d;
	}
	
	public Vector3 getNormal(Vector3 p){
		if(p==null){
			return normal;
		}else if(Utility.equals(Vector3.dot(normal,p)-d,0)){
			return normal;
		}else{
			return null;
		}
	}
	
	public void setD(double d){
		this.d=d;
	}
	
	public void setNormal(Vector3 n){
		normal=n;
	}

	public Vector3 intersectRay(Ray r){
		// ray: p=orig+direction*t
		// plane: (p dot normal)-d=0

		// [(orig+direction*t) dot normal]-d=0
		// (orig dot normal)+(direction dot normal)*t-d=0;
		// (direction dot normal)*t=d-(orig dot normal)
		// t=[d-(orig dot normal)]/[direction dot normal]
		
		double t = (d-(Vector3.dot(r.getOrig(), normal)))/(Vector3.dot(r.getDirection(), normal));
		Vector3 p = Vector3.add(r.getOrig(),Vector3.multi(r.getDirection(), t));
		if(Utility.equals(Vector3.dot(r.getOrig(), normal),0)){
			return null;
		}
		else if(t<0){
			return null;
		}
		else {
			return p;
		}
		
	}
	
	public boolean onPlane(Vector3 p){
		double temp = Vector3.dot(normal,p)-d;
		return Utility.equals(temp,0);
	}
}
