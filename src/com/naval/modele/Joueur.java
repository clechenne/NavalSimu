package com.naval.modele;

import java.io.Serializable;
import java.util.StringTokenizer;

public class Joueur implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4538509914480060593L;
	
	public String nom;
	public int id;

	public Joueur(String description) {
		analyser(description);
	}

	private void analyser(String description) {
		//0#chris
		StringTokenizer st = new StringTokenizer(description, "#");
		id = Integer.parseInt(st.nextToken());
		nom = st.nextToken();
	}	
	
}
