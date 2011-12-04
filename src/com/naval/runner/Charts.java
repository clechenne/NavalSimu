package com.naval.runner;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Charts {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Create a simple XY chart
		XYSeries series = new XYSeries("XYGraph");
		series.add(1, 1);
		series.add(1, 2);
		series.add(2, 1);
		series.add(3, 9);
		series.add(4, 10);
		// Add the series to your data set
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series);
		// Generate the graph
		JFreeChart chart = ChartFactory.createXYLineChart(
		"XY Chart", // Title
		"x-axis", // x-axis Label
		"y-axis", // y-axis Label
		dataset, // Dataset
		PlotOrientation.VERTICAL, // Plot Orientation
		true, // Show Legend
		true, // Use tooltips
		false // Configure chart to generate URLs?
		);		
		ChartFrame frame=new ChartFrame("First",chart);
	    frame.pack();
	    frame.setVisible(true);


	}

}
