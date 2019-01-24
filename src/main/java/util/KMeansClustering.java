package util;

import model.Cluster;

import java.awt.*;
import java.util.*;
import java.util.List;

class KMeansClustering
{
	private List<Cluster> clusterList;			//contains all the cluster of points
	private List<Point> pointList;				//contains all the points

	KMeansClustering(List<Cluster> clusterList) {
		this.clusterList = clusterList;
		pointList = new ArrayList<>();
		//combine / joining all the Points in each cluster into a single list
		clusterList.stream()
					.map(Cluster::getPointList)
					.forEach(pointList::addAll);
	}


	List<Cluster> generateCluster() {
		Random random = new Random();
		int k = clusterList.size();
		List<Cluster> endCluster = new ArrayList<>();

		for(int counter = 0; counter < 10000; counter++) {					//repeat as many as would like
			List<Point> centers = new ArrayList<>();						//will contains all the centroid / Mittelwert
			if (counter == 0) {                                            	//first run, selecting random initial centroid from all points
				List<Integer> randomCenters = new ArrayList<>();
				for (int i = 0; i < k; i++) {
					int randomPoint = random.nextInt(pointList.size()) + 1;	//which position?
					if (!randomCenters.contains(randomPoint)) {
						randomCenters.add(randomPoint);
						centers.add(pointList.get(randomPoint));			//adding the centroid to the lists
					}

				}
			} else {
				//each consecutive run, selecting centroid based on average from produced cluster before
				for (Cluster c : clusterList) {
					int x_average = (int)Math.round(c.getPointList().stream().mapToDouble(Point::getX).average().orElse(0.0));
					int y_average = (int)Math.round(c.getPointList().stream().mapToDouble(Point::getX).average().orElse(0.0));
					Point newCenter = new Point(x_average, y_average);
					centers.add(newCenter);									//adding the centroid to the lists
				}
			}

			//for holding the centroid index (in List<Point> centers) as key, and corresponding closest points as value
			//this will be converted to List<Cluster later>
			Map<Integer, List<Point>> maps = new HashMap<>();
			for (int i = 0; i < centers.size(); i++)
				maps.put(i, new ArrayList<>());

			//comparing every point with the temporary centroid
			for (Point point : pointList) {
				Point nearestCenter;
				double minDistance = Double.MAX_VALUE;
				int indexOfNearestCenter = Integer.MAX_VALUE;

				//compute which centroid is the closest based on the euclidean distance
				for (Point center : centers) {
					double distance = point.distance(center);
					if (distance <= minDistance) {
						minDistance = distance;
						indexOfNearestCenter = centers.indexOf(center);
					}

				}

				maps.get(indexOfNearestCenter).add(point);
			}


			endCluster = new ArrayList<>();
			for (Integer i : maps.keySet()) {
				Cluster a = new Cluster();							//finally convert the map to List<Cluster>
				a.setAverage(centers.get(i));						//so it can be returned
				a.setPointList(maps.get(i));
				endCluster.add(a);
			}
			this.clusterList = endCluster;							//update the clusterList to the new one,
			//System.out.println(counter);							//so the process can continue
		}


		return endCluster;


	}


}
