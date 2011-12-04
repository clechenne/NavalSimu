package com.naval.modele;

import java.io.Serializable;
import java.util.StringTokenizer;

public class Navire implements Serializable{
	public int id; // 0 to n
	public double x;
	public double y;
	public int cap; // 0 to 359
	public float vitesse;
	public int taille;
	
	public String nationalite;
	public String nom;
	
	public boolean detecte;
	private String division;
	
	public Navire(String description) {
		
		analyser(description);
	}
	
	private void analyser(String description) {
		//0#Nagato#JP#JAP1#12.62#10.79#25#45
		StringTokenizer st = new StringTokenizer(description, "#");
		id = Integer.parseInt(st.nextToken());
		nom = st.nextToken();
		nationalite = st.nextToken();
		division = st.nextToken();
		x = Double.parseDouble(st.nextToken());
		y = Double.parseDouble(st.nextToken());
		vitesse = Float.parseFloat(st.nextToken());
		cap = Integer.parseInt(st.nextToken());
	}

	public void bouge() {
		x += (vitesse / 60.0)*Math.sin(cap * Math.PI / 180);
		y += (vitesse / 60.0)*Math.cos(cap * Math.PI / 180);
	}
}
