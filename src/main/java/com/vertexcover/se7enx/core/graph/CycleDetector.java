package com.vertexcover.se7enx.core.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class CycleDetector {
	
	private HashMap<Long, HashMap<Long, Double>> adjacencyMap;
	private HashSet<Long> visitedSet;
	private boolean cycleDetected;
	
	public CycleDetector(HashMap<Long, HashMap<Long, Double>> adjacencyMap) {
		this.adjacencyMap = adjacencyMap;
		this.visitedSet = new HashSet<Long>();
		this.cycleDetected = false;
	}
	
	public boolean doesCycleExists() {
		if(adjacencyMap == null)
			return false;
		
		for (Map.Entry<Long, HashMap<Long, Double>> entry : adjacencyMap.entrySet()) {
			if(cycleDetected)
				return true;
			
		    long userId = entry.getKey();
		    traverseGraph(userId);
		}
		
		return cycleDetected;
	}
	
	
	private void traverseGraph(long userId) {
	    if(visitedSet.contains(userId)) {
	    	cycleDetected = true;
	    	return;
	    }
	    
	    visitedSet.add(userId);
	    HashMap<Long, Double> friends = adjacencyMap.get(userId);
	    if(friends != null) {
		    for (Map.Entry<Long, Double> entry : friends.entrySet()) {
		    	long friendId = entry.getKey();
		    	traverseGraph(friendId);
		    }
	    }
	    
	    visitedSet.remove(userId);
	}

}
