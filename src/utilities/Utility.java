package utilities;

import geometries.*;
import java.util.*;
import java.awt.*;

public class Utility {
	public static final double SMALL_NUMBER=1e-03;
	
	/*
	 * This method is used to compare two float-point numbers.
	 * If the difference is belong SMALL_NUMBER, we treat them equal.
	 * This is to avoid float-point errors.
	 */
	public static boolean equals(double n1, double n2){
		
		if(Math.abs(n1-n2)<SMALL_NUMBER){
			return true;
		}else{
			return false;
		}
	}
	/*
	 * True: n1<=n<=n2
	 * False: otherwise
	 */
	public static boolean within(double n, double n1, double n2){
		if(equals(n1,n2)){
			return false;
		}
		if(n>=n1 && n<=n2){
			return true;
		}
		if(n>=n2 && n<=n1){
			return true;
		}
		return false;
	}
	public static Color weaken(Color rgb, double d){
		return new Color((int)(rgb.getRed()*d), 
				(int)(rgb.getGreen()*d),(int)(rgb.getBlue()*d));
	}


}
