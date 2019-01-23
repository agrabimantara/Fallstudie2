package model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Cluster
{
	private int x_average;
	private int y_average;
	private long[] x_points;
	private long[] y_points;
}
