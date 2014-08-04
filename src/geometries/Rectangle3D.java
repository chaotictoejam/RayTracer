package geometries;

public class Rectangle3D extends Plane{
	protected Vector3 points [] = new Vector3[4];
	
	
	public Rectangle3D(){
		points[0]=null;
		points[1]=null;
		points[2]=null;
		points[3]=null;
	}
	
	/*
	 * Points are in clockwise order, starting with the left-bottom corner.
	 * Therefore, p0p1 is the left side and p3p0 is the bottom line
	 */ 
	public Rectangle3D(Vector3 p0, Vector3 p1, Vector3 p2, Vector3 p3){
		super(p0,p1,p2);
		points[0]=p0;
		points[1]=p1;
		points[2]=p2;
		points[3]=p3;
	}
	
	/*
	 * If the rectangle encloses point p, returns true; false, otherwise.
	 * 
	 * See Pages 22-26 in 2.Geometries.ppt
	 */
	public boolean encloses(Vector3 p){	
		if(!onPlane(p)){
		    return false;
		}
		Vector3 v1 = Vector3.subtract(points[2], points[1]);
		Vector3 v2 = Vector3.subtract(points[0], points[1]);
		Vector3 v3 = Vector3.subtract(p, points[1]);
		double x = Vector3.dot(v1, v3);
		double y = Vector3.dot(v2, v3);
		
		double t1 = (x/v1.norm());
		double t2 = (y/v2.norm());
		
		if(t1>0&&t1<v1.norm()&&t2>0&&t2<v2.norm()){
			return true;
		}
		else{
			return false;
		}
	}
	
	/*
	 * Calculate the intersection of the incoming ray and the rectangle
	 */
	public Vector3 intersectRay(Ray r){
		Vector3 result=super.intersectRay(r);
		if(result==null){
			return null;
		}
		if(encloses(result)){
			return result;
		}else{
			return null;
		}
	}
}
