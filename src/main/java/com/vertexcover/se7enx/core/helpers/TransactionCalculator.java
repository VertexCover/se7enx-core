package com.vertexcover.se7enx.core.helpers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import com.vertexcover.se7enx.pojo.units.EventUnit;
import com.vertexcover.se7enx.pojo.units.TransactionLogUnit;
import com.vertexcover.se7enx.pojo.wrappers.Event;
import com.vertexcover.se7enx.pojo.wrappers.TransactionLogs;

public class TransactionCalculator {
	
	public static TransactionLogs getTransactionLogs(Event event, final String eventMode) {
	    PriorityQueue<EventUnit> minQueue = new PriorityQueue<EventUnit>(event.getEventUnits().size(), getEventUnitComparator(true));
	    PriorityQueue<EventUnit> maxQueue = new PriorityQueue<EventUnit>(event.getEventUnits().size(), getEventUnitComparator(false));
	    for(EventUnit eventUnit : event.getEventUnits()) {
	    	minQueue.offer(eventUnit);
	    	maxQueue.offer(eventUnit);
	    }
	    
	    List<TransactionLogUnit> transactionLogUnitList = new ArrayList<TransactionLogUnit>();
	    try{
		    while(minQueue.size() > 0 && maxQueue.size() > 0) {
		    	if(minQueue.size() != maxQueue.size())
		    		return null;
		    	
		    	double minAmountPending = minQueue.peek().getAmountToSettle();
		    	double maxAmountPending = maxQueue.peek().getAmountToSettle();
		    	
		    	if(minAmountPending == 0) {
		    		maxQueue.remove(minQueue.remove());
		    		continue;
		    	}
	
		    	if(maxAmountPending == 0) {
		    		minQueue.remove(maxQueue.remove());
		    		continue;
		    	}
		    	
		    	
		    	if(Math.abs(minAmountPending) == maxAmountPending) {
		    		TransactionLogUnit transactionLogUnit = new TransactionLogUnit(minQueue.peek().getUserId(),
		    				maxQueue.peek().getUserId(), Math.abs(minAmountPending));
	
		    		minQueue.peek().setAmountToSettle(0);
		    		maxQueue.peek().setAmountToSettle(0);
		    		transactionLogUnitList.add(transactionLogUnit);
		    		continue;
		    	}
		    	
		    	if(Math.abs(minAmountPending) < maxAmountPending) {
		    		TransactionLogUnit transactionLogUnit = new TransactionLogUnit(minQueue.peek().getUserId(),
		    				maxQueue.peek().getUserId(), Math.abs(minAmountPending));
		    		
		    		minQueue.peek().setAmountToSettle(0);
		    		maxQueue.peek().setAmountToSettle(maxAmountPending - Math.abs(minAmountPending)); 
		    		transactionLogUnitList.add(transactionLogUnit);
		    		continue;
		    	}
		    	
	    		TransactionLogUnit transactionLogUnit = new TransactionLogUnit(minQueue.peek().getUserId(),
	    				maxQueue.peek().getUserId(), Math.abs(maxAmountPending));
	    		
	    		maxQueue.peek().setAmountToSettle(0);
	    		minQueue.peek().setAmountToSettle(minAmountPending + maxAmountPending); 
	    		transactionLogUnitList.add(transactionLogUnit);
		    }
	    } catch(Exception e) {
	    	e.printStackTrace();
	    	return null;
	    }
	    
	    TransactionLogs transactionLogs = new TransactionLogs();
	    transactionLogs.setEventMode(eventMode);
	    transactionLogs.setTransactionLogs(transactionLogUnitList);
	    return transactionLogs;
	}
	
	
	private static Comparator<EventUnit> getEventUnitComparator(final boolean ASC) {
		return new Comparator<EventUnit>() {
			public int compare(EventUnit o1, EventUnit o2) {
				if(o1.getAmountToSettle() < o2.getAmountToSettle()) 
					return (ASC)?-1:1;
				if(o1.getAmountToSettle() > o2.getAmountToSettle()) 
					return (ASC)?1:-1;
				return 0;
			}
		};
	}
	

	
	
}
