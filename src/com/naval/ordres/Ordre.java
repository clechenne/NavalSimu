package com.naval.ordres;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Ordre {
	public int modificationCap;
	public int acceleration;
	public int codeManoeuvre;
	public int idNavire;
	
	public static List<Ordre> lire(Reader reader) throws IOException {
		BufferedReader br = new BufferedReader(reader);
		
		List<Ordre> ordres = new ArrayList<Ordre>();
		
		String line = br.readLine();
		
		while (line != null) {
			Ordre o = new Ordre();
			
			StringTokenizer st = new StringTokenizer(line, "#");
			
			o.idNavire = Integer.parseInt(st.nextToken());
			o.modificationCap = Integer.parseInt(st.nextToken());
			o.acceleration = Integer.parseInt(st.nextToken());
			
			ordres.add(o);
			line = br.readLine();
		}
		return ordres;
	}
}
