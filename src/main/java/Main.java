import model.Cluster;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    Random rand;
    private static final int MAX_K = 10;
    private static final int MIN_K = 1;
    private static final int MAX_POINT = 100;
    private static final int MIN_POINT = -100;
    private static final int STDEV = 3;

    public Main() {this.rand = new Random();}



    public Cluster generateCluster() {

        Cluster cluster = new Cluster();
        int x_average = rand.nextInt(MAX_POINT - MIN_POINT + 1) + MIN_POINT;
        int y_average = rand.nextInt(MAX_POINT - MIN_POINT + 1) + MIN_POINT;

        cluster.setX_average(x_average);
        cluster.setY_average(y_average);

        long[] x_points = new long[100];
        long[] y_points = new long[100];

        for (int j = 0; j < 100; j++)
            x_points[j] = Math.round(rand.nextGaussian() * STDEV + x_average);

        cluster.setX_points(x_points);

        for (int l = 0; l < 100; l++)
            y_points[l] = Math.round(rand.nextGaussian() * STDEV + y_average);

        cluster.setY_points(y_points);

        return cluster;
    }


    private List<Cluster> randomCluster() {

        List<Cluster> clusterList = new ArrayList<>();
        int k = rand.nextInt(MAX_K - MIN_K + 1) + MIN_K;
        for(int i = 0; i < k ; i++)
            clusterList.add(generateCluster());


        return clusterList;

    }

    public static void main(String[] args) {
        Main main = new Main();
        main.randomCluster();
        System.out.println();
    }
}
