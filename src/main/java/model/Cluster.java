package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class Cluster
{
	private Point average;
	private List<Point> pointList;
}
