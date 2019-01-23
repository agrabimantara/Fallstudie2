package model;

import de.unknownreality.dataframe.DataRow;

import java.util.ArrayList;
import java.util.function.BiFunction;

public class DistanceMeasureService {
    public static double getDistance(DataRow t1, double[] t2, BiFunction<DataRow, double[], Double> distance){
        if(distance== null){
            double[] x1 = t1.toArray();
            return euclideanDistance(x1, t2);
        }else{
            return distance.apply(t1, t2);
        }
    }

    public static double getDistance(DataRow t1, DataRow t2, BiFunction<DataRow, DataRow, Double> distance){
        if(distance== null){
            double[] x1 = t1.toArray();
            return euclideanDistance(x1, t2.toArray());
        }else{
            return distance.apply(t1, t2);
        }
    }

    public static double euclideanDistance(double[] x1, double[] x2){
        int dimension = Math.min(x1.length, x2.length);
        double cross_prod = 0;
        for(int i=0; i < dimension; ++i){
            cross_prod += (x1[i]-x2[i]) * (x1[i]-x2[i]);
        }
        return Math.sqrt(cross_prod);
    }

}
