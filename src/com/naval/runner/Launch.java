package com.naval.runner;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.DefaultFontMapper;
import com.lowagie.text.pdf.FontMapper;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
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
	
	public static void writeChartAsPDF(OutputStream out,
			JFreeChart chart,
			int width,
			int height,
			FontMapper mapper) throws IOException {
			Rectangle pagesize = new Rectangle(width, height);
			Document document = new Document(pagesize, 50, 50, 50, 50);
			try {
			PdfWriter writer = PdfWriter.getInstance(document, out);
			document.addAuthor("JFreeChart");
			document.addSubject("Demonstration");
			document.open();
			PdfContentByte cb = writer.getDirectContent();
			PdfTemplate tp = cb.createTemplate(width, height);
			Graphics2D g2 = tp.createGraphics(width, height, mapper);
			Rectangle2D r2D = new Rectangle2D.Double(0, 0, width, height);
			chart.draw(g2, r2D);
			g2.dispose();
			cb.addTemplate(tp, 0, 0);
			}
			catch (DocumentException de) {
			System.err.println(de.getMessage());
			}
			document.close();
			}
	
	/**
	 * @param partie
	 * @throws IOException 
	 */
	private static void drawCharts(Partie partie) throws IOException {
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

		// TODO: mettre le fond en blanc
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
        
        File file = new File(".\\PDFMap.pdf");
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        writeChartAsPDF(out, chart, 800, 600, new DefaultFontMapper());
        out.close();
        
//		ChartFrame frame=new ChartFrame("Naval simulation",chart);
//	    frame.pack();
//	    frame.setVisible(true);
	    
//	    try {
//	    	ChartUtilities.saveChartAsJPEG(new File(".\\chart.jpg"), chart, 500, 300);
//	    	} catch (IOException e) {
//	    	System.err.println("Problem occurred creating chart.");
//	    	}
	}
}
