package geometries;

public class Ray {
	private Vector3 orig;
	private Vector3 direction;
	
	public Ray(Vector3 orig, Vector3 direction){
		this.orig=orig;
		this.direction=direction;
	}
	
	public Vector3 getOrig(){
		return orig;
	}
	
	public Vector3 getDirection(){
		return direction;
	}
	
	public String toString(){
		return "@"+orig+"-->"+direction;
	}
}
