package com.vertexcover.se7enx.core.graph;

import java.util.HashMap;

public class Graph {
	
	public static boolean doesCycleExist(HashMap<Long, HashMap<Long, Double>> adjacencyMap) {
		return new CycleDetector(adjacencyMap).doesCycleExists();
	}

}
