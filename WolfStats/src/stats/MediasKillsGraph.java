// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package stats;

import java.awt.Dimension;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.time.Minute;
import org.jfree.data.time.MovingAverage;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class MediasKillsGraph extends ApplicationFrame {
	static PlayerStats w = new PlayerStats();
	List<TimeSeries> series = new ArrayList<TimeSeries>();

	public MediasKillsGraph(String s, String field) {
		super(s);
		XYDataset xydataset = createDataset(field);
		JFreeChart jfreechart = createChart(xydataset);
		ChartPanel chartpanel = new ChartPanel(jfreechart);
		chartpanel.setPreferredSize(new Dimension(800, 600));
		chartpanel.setMouseZoomable(true, false);
		setContentPane(chartpanel);
	}

	private XYDataset createDataset(String field) {
		for (Player player : w.players) {
			try {
				Number n = (Number) player.getClass().getField(field).get(player);
				getSerie(player.name).add(new Minute(player.data), n);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		for (TimeSeries serie : series) {
			dataset.addSeries(serie);
		}
		XYDataset data1 = MovingAverage.createMovingAverage(dataset, "", Double.MAX_VALUE, 0);
		return data1;
	}

	private JFreeChart createChart(XYDataset xydataset) {
		JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(this.getTitle(), "Date", "Valor", xydataset, true, true, false);
		XYPlot xyplot = (XYPlot) jfreechart.getPlot();
		XYItemRenderer xyitemrenderer = xyplot.getRenderer();
		StandardXYToolTipGenerator standardxytooltipgenerator = new StandardXYToolTipGenerator("{0}: ({1}, {2})",
				new SimpleDateFormat("dd-MM-yyyy"), new DecimalFormat("0.00"));
		xyitemrenderer.setBaseToolTipGenerator(standardxytooltipgenerator);
		return jfreechart;
	}

	private TimeSeries getSerie(Comparable name) {
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
		MediasKillsGraph demo = new MediasKillsGraph("Médias de Kills", "kll");
		demo.pack();
		RefineryUtilities.positionFrameRandomly(demo);
		demo.setVisible(true);

		MediasKillsGraph demo1 = new MediasKillsGraph("Médias de XP", "score");
		demo1.pack();
		RefineryUtilities.positionFrameRandomly(demo1);
		demo1.setVisible(true);
	}
}
