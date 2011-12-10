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

import java.io.File;

import javax.swing.filechooser.FileFilter;

class GameFileFilter extends FileFilter {
	private String ext;
	private String desc;
	
	GameFileFilter(String ext, String desc) {
		this.ext = ext;
		this.desc = desc;
	}
	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}

		String extension = getExtension(f);
		if (extension != null) {
			if (extension.equals(ext)) {
				return true;
			} else {
				return false;
			}
		}

		return false;
	}

	/*
	 * Get the extension of a file.
	 */
	private String getExtension(File f) {
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1).toLowerCase();
		}
		return ext;
	}

	
	@Override
	public String getDescription() {
		return desc;
	}

}
