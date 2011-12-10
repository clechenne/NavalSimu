/*
 * NavalSimul - Copyright (C) 2011 Christophe Lechenne (christophe.lechenne@gmail.com)
 * 
 *  This program is free software; you can redistribute it and/or modify it 
 *  under the terms of the GNU General Public License as published by the Free 
 *  Software Foundation; either version 2 of the License, or (at your option) 
 *  any later version.
 * 
 *  This program is distributed in the hope that it will be useful, but 
 *  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 *  or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License 
 *  for more details.
 */
package com.naval.gui;

import java.io.IOException;

/**
 * Main launcher.
 * 
 * @author Lechenne_c
 *
 */
public class GuiLauncher {
    
	/**
	 * No constructor.
	 */
	private GuiLauncher() {
		
	}
	/**
	 * @param args args
	 */
	public static void main(String[] args) {
		//PreferenceManager.setRevision(Version.value);

	    //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	Gui gui = new Gui();
        		try {
        			gui.initialize(true);
        		} catch (IOException e) {
        			e.printStackTrace();
        		}

            }
        });

	}
}
