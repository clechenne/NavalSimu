package com.naval.tables;

import com.naval.modele.Navire;
import com.naval.outils.Distance;
import com.naval.outils.LogUtil;

public class TableHorizonVisuel {
	static float[]table = { 
                      //T1  T2  T3  T4  T5  T6
			40, 35, 35, 30, 30, 25, // T1
			35, 30, 30, 25, 25, 20, // T2
			35, 30, 30, 25, 25, 20, // T3
			30, 25, 25, 20, 20, 15, // T4
			30, 25, 25, 20, 20, 15, // T5
			25, 20, 20, 15, 15, 10, // T6
			//20, 15, 10, 7.5f, 5, // Periscope
			//35, 30, 25, 20, 15,  // TBA
			//70, 65, 60, 55, 50,  // BA
			//150,140,130,120,100,  // MA
	};
	
	public static float getHV(int tailleX, int tailleY) {
            return table[(tailleX-1)*6 + tailleY-1];
        }
        
        public static boolean visible(int visibilite, Navire nA, Navire nB) {
            double distance = Distance.calculer(nA.x, nA.y, nB.x, nB.y) / 1000 ;
            
            // calcul du seuil
            double distanceS = Math.min(90*50/100.0, TableHorizonVisuel.getHV(1, 1));
            
            LogUtil.doDebug(nA.nom + "<->" + nB.nom + " dist:" + distance);
            LogUtil.doDebug("distanceS:" + distanceS);
            
            if (distance <=  distanceS) {
                return true;
            } else {
                return false;
            }
        }
}
