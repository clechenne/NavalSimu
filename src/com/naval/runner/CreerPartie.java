package com.naval.runner;

import java.io.FileReader;
import java.io.IOException;

import com.naval.modele.Partie;

public class CreerPartie {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		FileReader fr = new FileReader("test.partie");
		
		Partie partie = Partie.creer(fr);
		
		partie.save();
	}

}
