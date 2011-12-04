package com.naval.runner;

import java.io.IOException;
import java.io.StringWriter;

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
import com.naval.report.Report;

public class Launch {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException {	
		
		Partie partie = Partie.load("GameOne");
				
		partie.executerTour();

		drawCharts(partie);
		
		StringWriter sw = new StringWriter();
		Report.dump(sw);
		
		System.out.println(sw.toString());
		
		partie.save();
		
	}

	/**
	 * @param partie
	 */
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
				"Map turn " + partie.getHeureMinute() , // Title
				"x-axis", // x-axis Label
				"y-axis", // y-axis Label
				dataset, // Dataset
				PlotOrientation.VERTICAL, // Plot Orientation
				true, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
				);
				
		XYPlot plot = chart.getXYPlot();
		
		final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesShapesVisible(0, true);
        
        renderer.setSeriesLinesVisible(1, true);
        renderer.setSeriesShapesVisible(1, true);
        
        plot.setRenderer(renderer);
        
//		XYDotRenderer r = new XYDotRenderer();
//		r.setDotHeight(4);
//		r.setDotWidth(4);
//        plot.setRenderer(r);
	
		ChartFrame frame=new ChartFrame("Naval simulation",chart);
	    frame.pack();
	    frame.setVisible(true);
	    
//	    try {
//	    	ChartUtilities.saveChartAsJPEG(new File(".\\chart.jpg"), chart, 500, 300);
//	    	} catch (IOException e) {
//	    	System.err.println("Problem occurred creating chart.");
//	    	}
	}
}
