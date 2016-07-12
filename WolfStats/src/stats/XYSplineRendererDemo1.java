// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package stats;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;

public class XYSplineRendererDemo1 extends ApplicationFrame {
	static PlayerStats w;
	static List<TimeSeries> series = new ArrayList<TimeSeries>();

	static class DemoPanel extends JPanel {
		private XYDataset createSampleData() {
			for (Player player : w.players) {
				getSerie(player.name).add(new Minute(player.data), player.kll);
			}

			TimeSeriesCollection dataset = new TimeSeriesCollection();
			for (TimeSeries serie : series) {
				dataset.addSeries(serie);
			}
			return dataset;
		}

		private JTabbedPane createContent() {
			JTabbedPane jtabbedpane = new JTabbedPane();
			jtabbedpane.add("Splines:", createChartPanel1());
			jtabbedpane.add("Lines:", createChartPanel2());
			return jtabbedpane;
		}

		private ChartPanel createChartPanel1() {
			NumberAxis numberaxis = new NumberAxis("X");
			numberaxis.setAutoRangeIncludesZero(false);
			NumberAxis numberaxis1 = new NumberAxis("Y");
			numberaxis1.setAutoRangeIncludesZero(false);
			XYSplineRenderer xysplinerenderer = new XYSplineRenderer();
			XYPlot xyplot = new XYPlot(data1, numberaxis, numberaxis1, xysplinerenderer);
			xyplot.setBackgroundPaint(Color.lightGray);
			xyplot.setDomainGridlinePaint(Color.white);
			xyplot.setRangeGridlinePaint(Color.white);
			xyplot.setAxisOffset(new RectangleInsets(4D, 4D, 4D, 4D));
			JFreeChart jfreechart = new JFreeChart("XYSplineRenderer", JFreeChart.DEFAULT_TITLE_FONT, xyplot, true);
			jfreechart.setBackgroundPaint(Color.white);
			ChartPanel chartpanel = new ChartPanel(jfreechart, false);
			return chartpanel;
		}

		private ChartPanel createChartPanel2() {
			NumberAxis numberaxis = new NumberAxis("X");
			numberaxis.setAutoRangeIncludesZero(false);
			NumberAxis numberaxis1 = new NumberAxis("Y");
			numberaxis1.setAutoRangeIncludesZero(false);
			XYLineAndShapeRenderer xylineandshaperenderer = new XYLineAndShapeRenderer();
			XYPlot xyplot = new XYPlot(data1, numberaxis, numberaxis1, xylineandshaperenderer);
			xyplot.setBackgroundPaint(Color.lightGray);
			xyplot.setDomainGridlinePaint(Color.white);
			xyplot.setRangeGridlinePaint(Color.white);
			xyplot.setAxisOffset(new RectangleInsets(4D, 4D, 4D, 4D));
			JFreeChart jfreechart = new JFreeChart("XYLineAndShapeRenderer", JFreeChart.DEFAULT_TITLE_FONT, xyplot, true);
			jfreechart.setBackgroundPaint(Color.white);
			ChartPanel chartpanel = new ChartPanel(jfreechart, false);
			return chartpanel;
		}

		private XYDataset data1;

		public DemoPanel() {
			super(new BorderLayout());
			data1 = createSampleData();
			add(createContent());
		}
	}

	public XYSplineRendererDemo1(String s) {
		super(s);
		JPanel jpanel = createDemoPanel();
		getContentPane().add(jpanel);
	}

	public static JPanel createDemoPanel() {
		return new DemoPanel();
	}

	private static TimeSeries getSerie(Comparable name) {
		for (TimeSeries serie : series) {
			if (serie.getKey().equals(name)) {
				return serie;
			}
		}
		TimeSeries serie = new TimeSeries(name, Minute.class);
		series.add(serie);
		return serie;
	}

	public static void main(String[] args) {
		w = new PlayerStats();
		XYSplineRendererDemo1 demo = new XYSplineRendererDemo1("Graficos Wolf");
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);
	}
}
