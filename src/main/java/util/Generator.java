package util;

import model.Cluster;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * this class generate cluster based on the requirement stated on the paper
 */
class Generator {

	private static final int MAX_K = 10;
	private static final int MIN_K = 1;
	private static final int MAX_POINT = 100;
	private static final int MIN_POINT = -100;
	private static final int STDEV = 15;

	/**
	 * this function will first create random Point with x & y coordinate between -100 & 100
	 * then based on this point, 100 additional points will be created
	 * based on normal distribution around the random point
	 * @return the Cluster-Object generated
	 */
	private Cluster generateCluster() {
		Random rand = new Random();

		Cluster cluster = new Cluster();
		int x_average = rand.nextInt(MAX_POINT - MIN_POINT + 1) + MIN_POINT;
		int y_average = rand.nextInt(MAX_POINT - MIN_POINT + 1) + MIN_POINT;

		Point average = new Point(x_average, y_average);
		cluster.setAverage(average);

		List<Point> pointList = new ArrayList<>();
		for(int i = 0; i < 100; i++) {
			Point point = new Point((int)Math.round(rand.nextGaussian() * STDEV + x_average),
									(int)Math.round(rand.nextGaussian() * STDEV + y_average));

			pointList.add(point);
		}

		cluster.setPointList(pointList);
		return cluster;
	}

	/**
	 * this function will roll random number between 1-10
	 * and then will create clusters of that amount
	 * @return the list of clusters
	 */
	List<Cluster> randomCluster() {
		Random rand = new Random();

		List<Cluster> clusterList = new ArrayList<>();
		int k = rand.nextInt(MAX_K - MIN_K + 1) + MIN_K;
		for(int i = 0; i < k ; i++)
			clusterList.add(generateCluster());


		return clusterList;

	}
}
