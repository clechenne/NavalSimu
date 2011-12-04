package com.naval.report;

import java.io.IOException;
import java.io.Writer;

import com.naval.modele.Partie;

public class Report {
	static Partie partie;
	
	public static void set(Partie p) {
		partie = p;
	}
	
	public static void add(String msg) {
		if (partie != null) {
			partie.messages.add(new String(partie.minute + ":" + msg));
		}
	}
	
	public static void dump(Writer writer) throws IOException {
		for (String s : partie.messages) {
			writer.write(s + "\r\n");
		}
	}
	
}
