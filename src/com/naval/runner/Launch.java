package com.naval.runner;

import java.io.FileReader;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.naval.modele.Coordonnees;
import com.naval.modele.Donnees;
import com.naval.modele.Navire;
import com.naval.modele.Partie;

public class Launch {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		FileReader fr = new FileReader("test.partie");
		
		Partie partie = Partie.creer(fr);
		
		partie.executerTour();
		partie.executerTour();
		partie.executerTour();
		partie.executerTour();
		partie.executerTour();
				
		drawCharts(partie);
	}

	private static void drawCharts(Partie partie) {
		Coordonnees coords = partie.coords;
		
		XYSeriesCollection dataset = new XYSeriesCollection();
		
		for (Navire n : partie.navires) {		
			XYSeries series = new XYSeries(n.nom);
		
			for (int m=0; m < partie.minute; m++) {
				Donnees d = coords.donneesPour(m);	
				series.add(d.xs[n.id], d.ys[n.id]);
			}

			dataset.addSeries(series);
		}

		
		JFreeChart chart = ChartFactory.createXYLineChart(
				"Map", // Title
				"x-axis", // x-axis Label
				"y-axis", // y-axis Label
				dataset, // Dataset
				PlotOrientation.VERTICAL, // Plot Orientation
				true, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
				);
		
		XYPlot plot = chart.getXYPlot();
		XYDotRenderer r = new XYDotRenderer();
		r.setDotHeight(4);
		r.setDotWidth(4);
        plot.setRenderer(r);

		//XYItemRenderer rend = chart.getXYPlot().getRenderer();
//		java.awt.geom.Ellipse2D.Double double1 = new java.awt.geom.Ellipse2D.Double(-4D, -4D, 8D, 8D);
//        XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer)rend;
//        xylineandshaperenderer.setBaseShapesVisible(true);
		
		ChartFrame frame=new ChartFrame("Naval simulation",chart);
	    frame.pack();
	    frame.setVisible(true);		    
	}
}
