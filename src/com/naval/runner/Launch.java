package com.naval.runner;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;

import com.naval.modele.Partie;

public class Launch {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		FileReader fr = new FileReader("test.partie");
		
		Partie partie = Partie.creer(fr);
		
		partie.executerTour();
		
		StringWriter sw = new StringWriter();
		partie.report(sw);
		
		System.out.println(sw);
		
//		Coordonnees coords = new Coordonnees(1);
//		
//		Navire n = new Navire();
//		n.id = 0;
//		n.x = 19.93;
//		n.y = 21.82;
//		n.vitesse = 22;
//		n.cap = 390;
		
//		for (int minute=0; minute < 10; minute++) {
//			n.bouge();
//			coords.ajouter(minute, n);
//			
//			Donnees donnees = coords.donneesPour(minute);
//			
//			System.out.println(donnees.minute + ": X=[" + donnees.xs[n.id] + "] Y=[" + donnees.ys[n.id] + "]");
//		}
		
		
	}

}
