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

import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Allows to handle all menu bar and item.
 * 
 * @author christophe
 *
 */
class MenuBarFactory {
	private Gui gui;
	private JMenu optionsActions;
	private JMenu optionsOrdres;
	
	public MenuBarFactory(Gui aGui) {
		gui = aGui;
	}

	public JMenuBar createMenuBar() {
		JMenuBar menuBar;
		menuBar = new JMenuBar();		
		menuBar.add(getMenuActions());
		menuBar.add(getMenuOrdres());
		return menuBar;
	}
	
	private JMenu getMenuOrdres() {
		optionsOrdres = new JMenu("Ordres");
		JMenuItem menuItem = createMenuItem("Ajouter", KeyEvent.VK_O, "Ajouter/Modifier", false);
		optionsOrdres.add(menuItem);
		
		return optionsOrdres;
	}

	private JMenu getMenuActions() {
		optionsActions = new JMenu("Actions");
		optionsActions.setMnemonic(KeyEvent.VK_O);
		
		JMenuItem menuItem = createMenuItem("Creer", KeyEvent.VK_C, "Creer une partie", true);
		optionsActions.add(menuItem);	
		
		menuItem = createMenuItem("Charger", KeyEvent.VK_D, "Charger une partie", true);
		optionsActions.add(menuItem);	

		menuItem = createMenuItem("Executer", KeyEvent.VK_E, "Exec tour", false);
		optionsActions.add(menuItem);

		menuItem = createMenuItem("Sauver", KeyEvent.VK_S, "Sauver la partie", false);
		optionsActions.add(menuItem);
		
		return optionsActions;
	}

	private JMenuItem createMenuItem(String action, int keyEvent, String description, boolean enabled ) {
		JMenuItem menuItem = new JMenuItem(description, keyEvent);
	
		menuItem.getAccessibleContext().setAccessibleDescription(
				description);
		menuItem.addActionListener(gui);
	
		menuItem.setActionCommand(action);
		
		menuItem.setEnabled(enabled);
		
		return menuItem;
	}
	
	public void updateForLoad() {
		
		// Menu actions
		JMenuItem item = (JMenuItem) optionsActions.getMenuComponent(0);
		item.setEnabled(true);

		item = (JMenuItem) optionsActions.getMenuComponent(1);
		item.setEnabled(false);
		
		item = (JMenuItem) optionsActions.getMenuComponent(2);
		item.setEnabled(true);
		
		item = (JMenuItem) optionsActions.getMenuComponent(3);
		item.setEnabled(true);
		
		// Menu ordres
		item = (JMenuItem) optionsOrdres.getMenuComponent(0);
		item.setEnabled(true);
	}
}
