package ray_tracing;

import geometries.*;
/*
 * This class has the function that receives eye position and lookat direction,
 * and calculates the eye coordinate system (u,v,w)
 */
public class CoordinateSystem {
	private Vector3 eyePosition;
	private Vector3 u;
	private Vector3 v;
	private Vector3 w;
	
	private double worldMatrix[][]=new double[3][3]; // the matrix used to convert eye
							// coordinates to world coordinates
	
	/*
	 * Task 1
	 * Given the tree vector: eye, lookat, and up, calculate three axes
	 * for the eye reference basis
	 * 
	 * Task 2:
	 * Calculate the inverse of the matrix, formed by (u,v,w). The inverse matrix
	 * is for converting coordinates in the eye reference basis to the world 
	 * reference basis. Please save the inverse matrix to worldMatrix
	 */
	public CoordinateSystem(Vector3 eye, Vector3 lookat, Vector3 up){
		
		// Task 1
	
		w = Vector3.multi(Vector3.subtract(lookat, eye), -1).unit();
		u = Vector3.cross(up, w).unit();
		v = Vector3.cross(w,u).unit();
		
		eyePosition = eye;
		
				
		// Task 2
		getWorldMatrix();
	}
	
	/*
	 * Calculate the inverse of the matrix, formed by u,v,w.
	 * Save the inverse matrix to worldMatrix
	 */
	private void getWorldMatrix(){	
		double a = u.getX()*(v.getY()*w.getZ()-v.getZ()*w.getY())
				-u.getY()*(v.getX()*w.getZ()-w.getX()*v.getZ())
				-u.getZ()*(v.getX()*w.getY()-v.getY()*w.getX());
		double i0 = v.getY()*w.getZ()-v.getZ()*w.getY();
		double i1 = u.getZ()*w.getY()-u.getY()*w.getZ();
		double i2 = u.getY()*v.getZ()-u.getZ()*v.getY();
		double i3 = v.getZ()*w.getX()-v.getX()*w.getZ();
		double i4 = u.getX()*w.getZ()-u.getZ()*w.getX();
		double i5 = u.getZ()*v.getX()-u.getX()*v.getZ();
		double i6 = v.getX()*w.getY()-v.getY()*w.getX();
		double i7 = u.getY()*w.getX()-u.getX()*w.getY();
		double i8 = u.getX()*v.getY()-u.getY()*v.getX();
		worldMatrix[0][0] = (1/a)*i0;
		worldMatrix[0][1] = (1/a)*i1;
		worldMatrix[0][2] = (1/a)*i2;
		worldMatrix[1][0] = (1/a)*i3;
		worldMatrix[1][1] = (1/a)*i4;
		worldMatrix[1][2] = (1/a)*i5;
		worldMatrix[2][0] = (1/a)*i6;
		worldMatrix[2][1] = (1/a)*i7;
		worldMatrix[2][2] = (1/a)*i8;		
	}
	
	public Vector3 getEye(){
		return eyePosition;
	}
	
	/*
	 * Given coordinates in the world reference basis, calculate the 
	 * cooresponding coordinates in the eye reference basis.
	 * 
	 * new coordinates are [u dot (p-eyePos),v dot (p-eyePos),w dot (p-eyePos)] 
	 */
	public Vector3 convertToEye(Vector3 p){
		double x1 = Vector3.dot(u,(Vector3.subtract(p,eyePosition)));
		double y1 = Vector3.dot(v,(Vector3.subtract(p,eyePosition)));
		double z1 = Vector3.dot(w,(Vector3.subtract(p,eyePosition)));
		return new Vector3 (x1, y1, z1);
	}
	
	/*
	 * Given coordinates in the eye reference basis, calculate the
	 * corresponding coordinates in the world reference basis.
	 * 
	 * new coordinates are worldMatrix*p+eyePos
	 */
	public Vector3 convertToWorld(Vector3 p){
		double x2 = worldMatrix[0][0]*p.getX()+worldMatrix[0][1]*p.getY()+worldMatrix[0][2]*p.getZ();
		double y2 = worldMatrix[1][0]*p.getX()+worldMatrix[1][1]*p.getY()+worldMatrix[1][2]*p.getZ();
		double z2 = worldMatrix[2][0]*p.getX()+worldMatrix[2][1]*p.getY()+worldMatrix[2][2]*p.getZ();
		return Vector3.add(new Vector3 (x2, y2, z2), eyePosition);		
	}
	
	public static void main(String args[]){
		// setting up the view
		Vector3 eye=new Vector3(0,4,4);
		Vector3 lookat=new Vector3(0,-1,-1.75);
		Vector3 up=new Vector3(0,1,0);
	
		// create an eye reference basis
		CoordinateSystem eyeCoordinateSystem=new CoordinateSystem(eye,lookat,up);
		
		// Testing
		Vector3 leftBottomCorner=new Vector3(-2,-2,-4);
		Vector3 worldCoordinate=eyeCoordinateSystem.convertToWorld(leftBottomCorner);
		Vector3 temp=eyeCoordinateSystem.convertToEye(worldCoordinate);
		
		System.out.println("The leftBottomCorner is "+leftBottomCorner);
		System.out.println("Its world coordinates are "+worldCoordinate);
		System.out.println("The leftBottomCorner is "+temp+
				" (for testing purpose)");

		Vector3 rightTopCorner=new Vector3(2,2,-4);
		worldCoordinate=eyeCoordinateSystem.convertToWorld(rightTopCorner);
		temp=eyeCoordinateSystem.convertToEye(worldCoordinate);
		
		System.out.println("\nThe rightTopCorner is "+rightTopCorner);
		System.out.println("Its world coordinates are "+worldCoordinate);
		System.out.println("The rightTopCorner is "+temp+
				" (for testing purpose)");
	}
}
