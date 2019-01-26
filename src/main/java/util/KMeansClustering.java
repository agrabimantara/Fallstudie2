package util;

import model.Cluster;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * class that contains the function to group clusters, based on K-Means Strategy
 * note that this will perform the algorithm exactly as many as stated in the static variable LOOP
 */
class KMeansClustering
{
	private List<Cluster> clusterList;			//contains all the cluster of points
	private List<Point> pointList;				//contains all the points
	private static final int LOOP = 500;		//how many times the algorithm should be repeated

	/**
	 * Constructor, copying the passed clusterList, and then
	 * combining / joining all the Points in each cluster into a single list
	 * @param clusterList the passed list of clusters
	 */
	KMeansClustering(List<Cluster> clusterList) {
		this.clusterList = clusterList;
		pointList = new ArrayList<>();
		clusterList.stream()
					.map(Cluster::getPointList)
					.forEach(pointList::addAll);
	}

	/**
	 * this method will try to group the cluster,
	 * which already initialized by the constructor based on K-Means Strategy
	 * @return the finished grouped cluster
	 */
	List<Cluster> groupCluster() {
		Random random = new Random();
		int k = clusterList.size();
		List<Cluster> endCluster = new ArrayList<>();

		for(int counter = 0; counter < LOOP; counter++) {					//repeat as many as would like
			List<Point> centers = new ArrayList<>();						//will contains all the centroid / Mittelwert

			//first run, selecting random initial centroid from all points
			if (counter == 0) {
				List<Integer> randomCenters = new ArrayList<>();
				for (int i = 0; i < k; i++) {
					int randomPoint = random.nextInt(pointList.size()) + 1;	//which position?
					if (!randomCenters.contains(randomPoint)) {
						randomCenters.add(randomPoint);
						centers.add(pointList.get(randomPoint));			//adding the centroid to the lists
					}

				}
			} else {
				//for each cluster in the list, selecting centroid based on average from produced cluster before
				clusterList.stream()
						   .map(Cluster::calculateCentroid)
						   .forEach(centers::add);				//adding the centroid to the lists
			}

			//indicating the centroid index (in List<Point> centers) as key, and corresponding closest points as value
			//this will be converted to List<Cluster> later
			Map<Integer, List<Point>> maps = new HashMap<>();
			for (int i = 0; i < centers.size(); i++)
				maps.put(i, new ArrayList<>());

			//comparing every point with the temporary centroid (in centers)
			for (Point point : pointList) {
				double minDistance = Double.MAX_VALUE;
				int indexOfNearestCenter = Integer.MAX_VALUE;

				//calculate which centroid is the closest based on the euclidean distance
				for (Point center : centers) {
					double distance = point.distance(center);
					if (distance <= minDistance) {
						minDistance = distance;
						indexOfNearestCenter = centers.indexOf(center);
					}
				}
				//group the point to its nearest centroid by adding it to list in the map
				maps.get(indexOfNearestCenter).add(point);
			}

			endCluster = new ArrayList<>();
			//finally convert the map to List<Cluster>
			//each entry in the map will be converted to Cluster-Object,
			//the key (int) will be created as Point-Object and set as average
			//the value will be its lists of points
			maps.keySet()
				 .stream()
				 .map(key -> new Cluster(centers.get(key), maps.get(key)))
				 .forEach(endCluster::add);

			this.clusterList = endCluster;							//update the clusterList to the new one,
																	//so the process can continue
		}
		return endCluster;
	}


}
