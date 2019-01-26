package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * this is the Cluster class, which contains various functions and attributes
 * average is centroid / mittelwert between the points in its list
 */
@Getter @Setter
@NoArgsConstructor
public class Cluster
{
	private Point average;
	private List<Point> pointList;

	/**
	 * Constructor
	 * @param average the centroid
	 * @param pointList	the list of points
	 */
	public Cluster(Point average, List<Point> pointList) {
		setAverage(average);
		setPointList(pointList);
	}

	/**
	 * Constructor, take only 1 Point, setting it as centroid and creating new List consist of only that point
	 * @param average the centroid
	 */
	public Cluster(Point average) {
		setAverage(average);
		this.pointList = new ArrayList<>();
		pointList.add(average);
	}

	/**
	 * this function will calculate the centroid based on its list of points
	 * @return the centroid point
	 */
	public Point calculateCentroid() {
		int x_average = (int)Math.round(pointList.stream().mapToDouble(Point::getX).average().orElse(0.0));
		int y_average = (int)Math.round(pointList.stream().mapToDouble(Point::getX).average().orElse(0.0));
		return new Point(x_average, y_average);
	}

	/**
	 * this function will calculate the average distance between this cluster,
	 * and the cluster passed in parameter
	 * @param c the cluster which want to be calculated its distance to
	 * @return the average distance
	 */
	public double calculateAverageDistance(Cluster c) {
		double sumDistance = 0;
		for(Point p : pointList) {
			for(Point q : c.getPointList()) {
				sumDistance += p.distance(q);
			}
		}
		return (sumDistance) / (pointList.size() * c.getPointList().size());
	}

	/**
	 * this function will join two cluster together by
	 * adding the list of points of the second cluster to the first
	 * @param cluster the cluster that want to be joined
	 * @return this cluster (already joined)
	 */
	public Cluster joinCluster(Cluster cluster) {
		this.getPointList().addAll(cluster.getPointList());
		return this;
	}
}
