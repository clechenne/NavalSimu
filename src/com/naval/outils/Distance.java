package com.naval.outils;

public class Distance {
	
	public static double calculer(double xNav1, double yNav1, double xNav2, double yNav2) {
		//2000*RACINE((I$2-$B26)^2+(I$3-$C26)^2)))
		return 2000*Math.sqrt((xNav2-xNav1)*(xNav2-xNav1) + (yNav2 - yNav1)*(yNav2 - yNav1));
	}
}
