package com.naval.modele;

import java.util.ArrayList;
import java.util.List;

public class Coordonnees {
	int nombreNavires = 0;
	List<Donnees> lesDonnees;
	
	public Coordonnees(int nombreNavires) {
		this.nombreNavires = nombreNavires;
		lesDonnees = new ArrayList<Donnees>(); 
	}

	public void ajouter(int minute, Navire navire) {
		Donnees donnee = null;
		
		if (lesDonnees.size() > 0) {
			int idx = minute==0?0:(minute-1);
			donnee = lesDonnees.get(idx);
		} else {
			donnee = new Donnees();
			donnee.minute = minute;
			donnee.xs = new double[nombreNavires];
			donnee.ys = new double[nombreNavires];
			donnee.noms = new String[nombreNavires];
			lesDonnees.add(donnee);
		}
		
		if (navire.id >= nombreNavires) {
			throw new IllegalArgumentException(navire.nom + ": identifiant invalide" );
		}
		donnee.xs[navire.id] = navire.x;
		donnee.ys[navire.id] = navire.y;
		donnee.noms[navire.id] = navire.nom;
	}
	
	public Donnees donneesPour(int minute) {
		if (minute > lesDonnees.size()) {
			throw new IllegalArgumentException(minute + " est invalide" );
		}
		
		return lesDonnees.get(minute);
	}
}

