package geometries;


public class Vector3 {
	double x;
	double y;
	double z;
	
	public Vector3(){
		x=y=z=0;
	}
	
	public Vector3(double x, double y, double z){
		setXYZ(x,y,z);
	}
	
	public static Vector3 add(Vector3 v1, Vector3 v2){
		return new Vector3(v1.x+v2.x,
				v1.y+v2.y,
				v1.z+v2.z);
	}
	
	public static Vector3 subtract(Vector3 v1, Vector3 v2){
		return new Vector3(v1.x-v2.x,
				v1.y-v2.y,
				v1.z-v2.z);
	}
	
	public double norm(){
		return Math.sqrt(x*x+y*y+z*z);
	}
	
	public Vector3 unit(){
		double n=this.norm();
		return new Vector3(x/n,y/n,z/n);
	}
		
	public static Vector3 multi(Vector3 v, double k){
		return new Vector3(v.x*k,v.y*k,v.z*k);
	}
	
	public void setXYZ(double x, double y, double z){
		this.x=x;
		this.y=y;
		this.z=z;
	}
	
	
	public void setX(double x){
		this.x=x;
	}
	
	public double getX(){
		return x;
	}

	public void setY(double y){
		this.y=y;
	}
	
	public double getY(){
		return y;
	}
	public void setZ(double z){
		this.z=z;
	}
	
	public double getZ(){
		return z;
	}
	
	public String toString(){
		return String.format("(%.3f,%.3f,%.3f)",x,y,z);
	}
	
	public static double dot(Vector3 v1, Vector3 v2){
		return v1.x*v2.x+v1.y*v2.y+v1.z*v2.z;
	}

	public static Vector3 cross(Vector3 v1, Vector3 v2){
		return new Vector3(v1.y*v2.z-v1.z*v2.y, 
             	v1.z*v2.x-v1.x*v2.z, 
               	v1.x*v2.y-v1.y*v2.x);

	}

	/*public static void main(String args[]){
		
		// testing Vector3.dot and Vector3.cross
		Vector3 v1=new Vector3(1,2,3);
		Vector3 v2=new Vector3(4,5,6);
		double v3=Vector3.dot(v1,v2);
		Vector3 v4=Vector3.cross(v1,v2);
		
		System.out.println("******* Test Case I *******");
		System.out.println(v1+" * "+v2+"="+v3);
		System.out.println(v1+" x "+v2+"="+v4);
		
		// testing Geometry.reflectRay and Geometry.refractRay
		Ray incomingRay=new Ray(v1,new Vector3(125,6,9)); 
		 								// starts at (1,2,3) and the 
										// direction is (125,6,9)
		Vector3 normal=v4.unit(); // the surface normal is (-3,6,-3)
		    			   // note this normal is not unit vector
		Vector3 intersection=v1; // the incoming ray hits the surface @
		  						 // (1,2,3)
		
		Ray reflection = Geometry.reflectRay(incomingRay, intersection, normal);
		Ray refraction = Geometry.refractRay(incomingRay,intersection,normal,1.2);
		System.out.println("\nThe incoming ray is "+incomingRay);
		System.out.println("The surface normal is "+normal);
		System.out.println("The ray hits the surface @ "+intersection);
		System.out.println("The reflection ray is "+reflection);
		System.out.println("The refraction ray is "+refraction);

		
		v1=new Vector3(80,8,90);
		v2=new Vector3(75,71,2);
		v3=Vector3.dot(v1,v2);
		v4=Vector3.cross(v1,v2);
		
		System.out.println("\n\n******* Test Case II *******");
		System.out.println(v1+" * "+v2+"="+v3);
		System.out.println(v1+" x "+v2+"="+v4);
		
		// testing Geometry.reflectRay and Geometry.refractRay
		incomingRay=new Ray(v1,new Vector3(60,11,8)); 
		 								// starts at (80,8,90) and the 
										// direction is (60,11,8)
		normal=v4.unit(); 
		intersection=v1; // the incoming ray hits the surface @ v1
		
		reflection = Geometry.reflectRay(incomingRay, intersection, normal);
		refraction = Geometry.refractRay(incomingRay,intersection,normal,1.2);
		System.out.println("\nThe incoming ray is "+incomingRay);
		System.out.println("The surface normal is "+normal);
		System.out.println("The ray hits the surface @ "+intersection);
		System.out.println("The reflection ray is "+reflection);
		System.out.println("The refraction ray is "+refraction);
	}*/

}

