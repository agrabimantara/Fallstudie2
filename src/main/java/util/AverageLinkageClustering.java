package util;

import model.Cluster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * class that contains the function to group clusters, based on Average-Linkage Strategy
 * note that this will perform the algorithm until the amount of clusters is >= the initial cluster size
 */
class AverageLinkageClustering
{

	private List<Cluster> initialCLuster;
	private List<Cluster> clusterList;			//contains all the cluster of points


	/**
	 * Constructor, copying the passed clusterList to its initialCluster
	 * for every single points in the cluster in the cluster lists,
	 * make new Cluster, each consists of only single point with the point itsself as average
	 * (start of Average-Linkage Strategy)
	 * @param clusterList the passed list of clusters
	 */
	AverageLinkageClustering(List<Cluster> clusterList) {
		this.initialCLuster = clusterList;
		this.clusterList = new ArrayList<>();
		clusterList.stream()
					.map(Cluster::getPointList)
					.forEach(pointList -> pointList.stream()
							       					.map(Cluster::new)
													.forEach(this.clusterList::add));

	}

	/**
	 * this method will try to group the cluster,
	 * which already initialized by the constructor with Average-Linked Strategy
	 * @return the finished grouped cluster
	 */
	List<Cluster> groupCluster() {
		//no need to group if the initial clusters only has 1 cluster
		if(initialCLuster.size() == 1)
			return initialCLuster;

		List<Cluster> endCluster = new ArrayList<>();

		//will perform the algorithm until the amount of clusters is >= the initial cluster size
		int currentClusterSize = clusterList.size();
		while(currentClusterSize >= initialCLuster.size()) {
			endCluster = new ArrayList<>();

			//this map holds each pair of integer as index in clusterList,
			//indicating which cluster-pair that will be joined
			Map<Integer, Integer> indexesTobeJoined= new HashMap<>();

			//two for-loops, comparing each cluster with the other, calculating its average distance
			for (int i = 0; i < currentClusterSize; i++) {
				if(indexesTobeJoined.containsKey(i) || indexesTobeJoined.containsValue(i))
					continue;											//if its already paired(in map), then continue

				double minDistance = Double.MAX_VALUE;
				Cluster firstCluster = clusterList.get(i);
				int firstIndexTobeJoined = i;
				int secondIndexTobeJoined = i;
				for (int j = i+1; j < currentClusterSize; j++) {
					if(indexesTobeJoined.containsKey(j) || indexesTobeJoined.containsValue(j))
						continue;										//if its already paired(in map), then continue
					Cluster secondCluster = clusterList.get(j);

					double averageDistance = firstCluster.calculateAverageDistance(secondCluster);
					if (averageDistance < minDistance) {				//find out which cluster is the closest by average
						minDistance = averageDistance;
						secondIndexTobeJoined = j;
					}
				}

				//add the pair of closest cluster
				indexesTobeJoined.put(firstIndexTobeJoined, secondIndexTobeJoined);
			}

			indexesTobeJoined.keySet()
							 .stream()
							 .map(a -> clusterList.get(a).joinCluster(					//join the first and second cluster in pair
							 		   clusterList.get(indexesTobeJoined.get(a))))
							 .forEach(endCluster::add);

			//if current cluster size is odd, then there will be one cluster left in the list that hasnt been joined
			if(currentClusterSize % 2 != 0) {
				for(int u = 0; u < currentClusterSize; u++)
					if(!indexesTobeJoined.containsKey(u) && ! indexesTobeJoined.containsValue(u)) {
						endCluster.add(clusterList.get(u));
						break;
					}
			}

			//update the current clusterList and its size
			this.clusterList = endCluster;
			currentClusterSize = clusterList.size();
		}
		return endCluster;


	}
}
