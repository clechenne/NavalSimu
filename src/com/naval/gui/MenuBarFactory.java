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
	private JMenu options;
		
	public MenuBarFactory(Gui aGui) {
		gui = aGui;
	}

	public JMenuBar createMenuBar() {
		JMenuBar menuBar;
		menuBar = new JMenuBar();		
		menuBar.add(getMenuOptions());
		
		return menuBar;
	}
	
	private JMenu getMenuOptions() {
		options = new JMenu("Actions");
		options.setMnemonic(KeyEvent.VK_O);
		
		JMenuItem menuItem = createMenuItem("Creer", KeyEvent.VK_L, "Creer une partie", true);
		options.add(menuItem);	
		
		menuItem = createMenuItem("Charger", KeyEvent.VK_L, "Charger une partie", true);
		options.add(menuItem);	

		menuItem = createMenuItem("Executer", KeyEvent.VK_M, "Exec tour", false);
		options.add(menuItem);

		menuItem = createMenuItem("Sauver", KeyEvent.VK_M, "Sauver la partie", false);
		options.add(menuItem);
		
		return options;
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
 
		JMenuItem item = (JMenuItem) options.getMenuComponent(0);
		item.setEnabled(true);

		item = (JMenuItem) options.getMenuComponent(1);
		item.setEnabled(false);
		
		item = (JMenuItem) options.getMenuComponent(2);
		item.setEnabled(true);
	}
}
