package util;

import model.Cluster;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Generator {

	private Random rand;
	private static final int MAX_K = 10;
	private static final int MIN_K = 1;
	private static final int MAX_POINT = 100;
	private static final int MIN_POINT = -100;
	private static final int STDEV = 15;

	Generator() {this.rand = new Random();}



	private Cluster generateCluster() {

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


	List<Cluster> randomCluster() {

		List<Cluster> clusterList = new ArrayList<>();
		int k = rand.nextInt(MAX_K - MIN_K + 1) + MIN_K;
		for(int i = 0; i < k ; i++)
			clusterList.add(generateCluster());


		return clusterList;

	}
}
