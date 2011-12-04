package com.naval.modele;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class Partie {
	public Coordonnees coords;
	public List<Navire> navires;
	public int minute = 0;
	int heure;
	int minuteDebut;
	String nom;
	
	Partie() {
		navires = new ArrayList<Navire>();
		
	}
	
	public static Partie creer(Reader reader) throws IOException {
		
		Partie partie = new Partie();
		
		BufferedReader br = new BufferedReader(reader);
		
		// Nom de la partie
		partie.nom = br.readLine();
		// Heure et minute
		partie.heure = Integer.parseInt(br.readLine());
		partie.minuteDebut = Integer.parseInt(br.readLine());
		
		int nbNavires = Integer.parseInt(br.readLine());
		
		partie.coords = new Coordonnees(nbNavires);
		
		for (int i=0; i < nbNavires; i++) {
			String navireDescription = br.readLine();
			Navire navire = new Navire(navireDescription);
			partie.navires.add(navire);
		}
		
		return partie;
	}

	public void executerTour() {
		for (Navire n : navires) {
			n.bouge();
			coords.ajouter(minute, n);
		}
		minute++;
	}
	
	public void report(Writer writer) throws IOException {
		for (int m=0; m < minute; m++) {
			Donnees donnees = coords.donneesPour(m);
			for (Navire n : navires) {
				StringBuilder sb = new StringBuilder();
				sb.append(donnees.minute);
				sb.append(": id=[").append(n.id);
				sb.append("] nom=[").append(donnees.noms[n.id]);
				sb.append("] X=[").append(donnees.xs[n.id]);
				sb.append("] Y=[").append(donnees.ys[n.id]).append("]");
				
				writer.write(sb.toString()+ "\r\n");
			}
		}
	}

	public Navire getNavire(int n) {
		// TODO Auto-generated method stub
		return navires.get(n);
	}
	
}
