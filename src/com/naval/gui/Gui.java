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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.naval.modele.Coordonnees;
import com.naval.modele.Donnees;
import com.naval.modele.Navire;
import com.naval.modele.Partie;
import com.naval.ordres.Ordre;
import com.naval.outils.Config;

public class Gui extends JPanel implements ActionListener, ItemListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5679929137144915187L;

	JFrame frame;

	private MenuBarFactory menuFac ;
	
	Partie partie;
	
	/** For displaynig msg. */
	JLabel hintBar = new JLabel(" ");
	
	private ChartPanel cPanel;
	
	public Gui() {
		super(new BorderLayout());
		menuFac = new MenuBarFactory(this);
	}

	/**
	 * Lays out the frame by setting this Client object to take up the full
	 * frame display area.
	 */
	private void layoutFrame() {
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(this, BorderLayout.CENTER);
		hintBar.setBorder(BorderFactory.createLoweredBevelBorder());
		hintBar.setForeground(Color.RED);
		frame.getContentPane().add(hintBar, BorderLayout.SOUTH);
		frame.validate();
	}

	public void initialize(boolean gameMode) throws IOException {
		frame = new JFrame("Naval Simulation");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setSize(800, 700);
		
		/*
		 * We have a menu bar only in game mode
		 */
		menuFac = new MenuBarFactory(this);
		frame.setJMenuBar(menuFac.createMenuBar());
		frame.setBackground(SystemColor.menu);
		frame.setForeground(SystemColor.menuText);

		layoutFrame();

		frame.setVisible(true);
		System.out.println(Config.getRepTravail());
	}

	public void actionPerformed(ActionEvent event) {
		// Menu action
		if (event.getActionCommand().equals("Charger")) {
			chargerPartie();
		} 
		else if (event.getActionCommand().equals("Creer")) {
			creerPartie();
		}
		else if (event.getActionCommand().equals("Executer")) {
			executerTour();
		}
		else if (event.getActionCommand().equals("Sauver")) {
			sauverPartie();
		}
		// Menu ordres
		else if (event.getActionCommand().equals("Ajouter")) {
			//OrdresDialog2.show(this);
                        OrdresTableDialog dia = new OrdresTableDialog(frame, true) ;
                        dia.setDonnees(partie.navires, partie.ordres, partie.minute);
                        dia.setVisible(true);
		}
                else if (event.getActionCommand().equals("Detecter")) {
			//OrdresDialog2.show(this);
                        VisibleDialog dia = new VisibleDialog(frame, true) ;
                        dia.setDonnees(partie.navires, partie.visibilite);
                        dia.setVisible(true);
		}
	}

	private void sauverPartie() {
		try {
			// TODO: proposer un fileChooser.
			partie.save();
			hintBar.setText("Partie " + partie.nom + " sauv�e avec succes");
		} catch (IOException e) {
			hintBar.setText(e.getMessage());
			e.printStackTrace();
		}
	}

	private void executerTour() {
		try {
			partie.executerTour();
			update();
		} catch (IOException e) {
			hintBar.setText(e.getMessage());
			e.printStackTrace();
		}
		
	}

	private void creerPartie() {
		final JFileChooser fc = new JFileChooser(Config.getRepTravail());
		
		fc.addChoosableFileFilter(new GameFileFilter("gm", "Description de partie"));
		fc.setAcceptAllFileFilterUsed(false);
		
		int returnVal = fc.showOpenDialog(frame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            
            // TODO : load only if game is !=

            try {
                FileReader fr = new FileReader(file);
            	partie = Partie.creer(fr);
    			partie.save();
    			hintBar.setText("Partie " + partie.nom + " cr��e avec succes");
			} catch (FileNotFoundException e) {
				hintBar.setText(e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				hintBar.setText(e.getMessage());
				e.printStackTrace();
			}
        }	
	}

	private void chargerPartie() {
		final JFileChooser fc = new JFileChooser(Config.getRepTravail());
		
		fc.addChoosableFileFilter(new GameFileFilter("serial", "Sauv de partie"));
		fc.setAcceptAllFileFilterUsed(false);
		
		int returnVal = fc.showOpenDialog(frame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            
            // TODO : load only if game is !=
            try {
            	partie = Partie.load(file.getAbsolutePath());
            	
            	menuFac.updateForLoad();
    			update();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				hintBar.setText(e.getMessage());
			} catch (IOException e) {
				hintBar.setText(e.getMessage());
				e.printStackTrace();

			} catch (ClassNotFoundException e) {
				hintBar.setText(e.getMessage());
				e.printStackTrace();
			}
		}
             if (partie.ordres == null || partie.ordres.size() == 0) {
                 for (Navire n : partie.navires) {
                     // creation des 3 ordres pour le tour courant.
                     partie.ordres.add(new Ordre(n.id, partie.minute));
                     partie.ordres.add(new Ordre(n.id, partie.minute));
                     partie.ordres.add(new Ordre(n.id, partie.minute));
                     
                 }
             }
	}

	private void update() {
		Coordonnees coords = partie.coords;
		
		XYSeriesCollection dataset = new XYSeriesCollection();
		
		for (Navire n : partie.navires) {		
			XYSeries series = new XYSeries(n.nom);
			
			for (int m=0; m < partie.minute; m++) {
				Donnees d = coords.donneesPour(m);
				series.add(d.xs[n.id], d.ys[n.id]);
			}
			
			if (series.isEmpty() && partie.minute ==0) {
				series.add(n.x, n.y);
			}
			
			dataset.addSeries(series);
		}

		JFreeChart chart = ChartFactory.createXYLineChart(
				"Map turn " + partie.getHeureMinute() + " visibilite:" + partie.visibilite + "%", // Title
				"x-axis", // x-axis Label
				"y-axis", // y-axis Label
				dataset, // Dataset
				PlotOrientation.VERTICAL, // Plot Orientation
				true, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
				);
				
		XYPlot plot = chart.getXYPlot();
		plot.setBackgroundPaint (Color.white);
		plot.setRangeGridlinePaint(Color.black);
		plot.setDomainGridlinePaint(Color.black);
		
		final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesShapesVisible(0, true);
        
        renderer.setSeriesLinesVisible(1, true);
        renderer.setSeriesShapesVisible(1, true);
        
        plot.setRenderer(renderer);
        
        if (cPanel != null) {
        	remove(cPanel);
        	cPanel = new ChartPanel(chart);
        	add(cPanel, BorderLayout.CENTER);
        } else {
        	cPanel = new ChartPanel(chart);
        	add(cPanel, BorderLayout.CENTER);
        }
		
		frame.validate();
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub
	}
}
