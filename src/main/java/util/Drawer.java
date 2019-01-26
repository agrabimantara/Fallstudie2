package util;

import model.Cluster;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * visualizer class for the List of Cluster
 */
public class Drawer extends JFrame
{
	private List<Cluster> clusterList;

	/**
	 * Constructor, setting the title of the graph
	 * @param title the passed title
	 */
	private Drawer(String title) {
		super(title);
	}

	/**
	 * this function will display the graph based on the cluster list passed in argument
	 * @param clusterList the list of clusters that want to be displayed
	 */
	private void draw(List<Cluster> clusterList) {
		this.clusterList = clusterList;

		XYDataset dataset = createDataset();
		// Create chart
		JFreeChart chart = ChartFactory.createScatterPlot(this.getTitle(),
													 "X-Axis",
													 "Y-Axis",
																dataset,
																PlotOrientation.HORIZONTAL,
														true,
														true,
															false);


		//Changes background color
		XYPlot plot = (XYPlot)chart.getPlot();
		plot.setBackgroundPaint(new Color(255,228,196));


		// Create Panel
		ChartPanel panel = new ChartPanel(chart);
		setContentPane(panel);
	}

	/**
	 * this function will create the data required by JFrame based on the clusterList object
	 * that has been set by function draw()
	 * @return the Dataset required
	 */
	private XYDataset createDataset() {
		XYSeriesCollection dataset = new XYSeriesCollection();

		for(Cluster cluster : clusterList) {
			XYSeries series = new XYSeries("Average: ("+ cluster.getAverage().getX() + " , " + cluster.getAverage().getY() + ")");
			cluster.getPointList()
					.forEach(p -> series.add(p.getX(), p.getY()));
			dataset.addSeries(series);
		}

		return dataset;
	}

	public static void main(String[] args) {
		Generator generator = new Generator();
		List<Cluster> initialClusters = generator.randomCluster();
		SwingUtilities.invokeLater(() -> {
			Drawer drawer = new Drawer("Initial Cluster");

			drawer.draw(initialClusters);
			drawer.setSize(800, 400);
			drawer.setLocationRelativeTo(null);
			drawer.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			drawer.setVisible(true);
		});

		KMeansClustering KMeansClustering = new KMeansClustering(initialClusters);
		List<Cluster> endClusters1 = KMeansClustering.groupCluster();
		SwingUtilities.invokeLater(() -> {
			Drawer drawer = new Drawer("With K-Means Algorithm");

			drawer.draw(endClusters1);
			drawer.setSize(800, 400);
			drawer.setLocationRelativeTo(null);
			drawer.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			drawer.setVisible(true);
		});

		AverageLinkageClustering averageLinkageClustering = new AverageLinkageClustering(initialClusters);
		List<Cluster> endClusters3 = averageLinkageClustering.groupCluster();
		SwingUtilities.invokeLater(() -> {
			Drawer drawer = new Drawer("With Average Linkage Algorithm");

			drawer.draw(endClusters3);
			drawer.setSize(800, 400);
			drawer.setLocationRelativeTo(null);
			drawer.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			drawer.setVisible(true);
		});
	}

}
