package stats;

import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.experimental.chart.annotations.XYTitleAnnotation;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;

/**
 * An example of a time series chart. For the most part, default settings are used, except that the renderer is modified to show
 * filled shapes (as well as lines) at each data point.
 */
public class PlayersGraph extends ApplicationFrame {
	static PlayerStats w;
	static List<TimeSeries> series = new ArrayList<TimeSeries>();

	/**
	 * A demonstration application showing how to create a simple time series chart. This example uses monthly data.
	 * 
	 * @param title
	 *            the frame title.
	 */
	public PlayersGraph(String title) {
		super(title);
		ChartPanel chartPanel = (ChartPanel) createDemoPanel();
		chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
		chartPanel.setMouseZoomable(true, false);
		setContentPane(chartPanel);
	}

	private static JFreeChart createChart(XYDataset dataset) {

		JFreeChart chart = ChartFactory.createTimeSeriesChart("Wolf Stats", // title
				"Tempo", // x-axis label
				"Marca", // y-axis label
				dataset, // data
				false, // create legend?
				true, // generate tooltips?
				false // generate URLs?
				);

		chart.setBackgroundPaint(Color.white);

		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		LegendTitle lt = new LegendTitle(plot);
		lt.setItemFont(new Font("Dialog", Font.PLAIN, 9));
		lt.setBackgroundPaint(new Color(200, 200, 255, 100));
		lt.setFrame(new BlockBorder(Color.white));
		lt.setPosition(RectangleEdge.BOTTOM);
		XYTitleAnnotation ta = new XYTitleAnnotation(0.98, 0.02, lt, RectangleAnchor.BOTTOM_RIGHT);

		ta.setMaxWidth(0.48);
		plot.addAnnotation(ta);

//		XYItemRenderer r = plot.getRenderer();
//		if (r instanceof XYLineAndShapeRenderer) {
//			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
//			renderer.setBaseShapesVisible(true);
//			renderer.setBaseShapesFilled(true);
//		}

		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("dd-MM-yyyy"));

		ValueAxis yAxis = plot.getRangeAxis();
		yAxis.setLowerMargin(0.35);
		return chart;

	}

	/**
	 * Creates a dataset, consisting of two series of monthly data.
	 * 
	 * @return The dataset.
	 */
	private static XYDataset createDataset() {
		for (Player player : w.players) {
			getSerie(player.name).add(new Minute(player.data), player.kll);
		}

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		for (TimeSeries serie : series) {
			dataset.addSeries(serie);
		}
		return dataset;
	}

	/**
	 * Creates a panel for the demo (used by SuperDemo.java).
	 * 
	 * @return A panel.
	 */
	public static JPanel createDemoPanel() {
		JFreeChart chart = createChart(createDataset());
		return new ChartPanel(chart);
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
		PlayersGraph demo = new PlayersGraph("Graficos Wolf");
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);
	}
}