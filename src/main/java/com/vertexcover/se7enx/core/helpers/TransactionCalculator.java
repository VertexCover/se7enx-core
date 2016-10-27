package com.vertexcover.se7enx.core.helpers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import com.vertexcover.se7enx.pojo.units.EventEntry;
import com.vertexcover.se7enx.pojo.units.Transaction;
import com.vertexcover.se7enx.pojo.wrappers.Event;
import com.vertexcover.se7enx.pojo.wrappers.Transactions;

public class TransactionCalculator {
	
	public static Transactions getTransactions(Event event, final String eventMode) {
	    PriorityQueue<EventEntry> minQueue = new PriorityQueue<EventEntry>(event.getEventEntries().size(), getEventUnitComparator(true));
	    PriorityQueue<EventEntry> maxQueue = new PriorityQueue<EventEntry>(event.getEventEntries().size(), getEventUnitComparator(false));
	    for(EventEntry eventEntry : event.getEventEntries()) {
	    	minQueue.offer(eventEntry);
	    	maxQueue.offer(eventEntry);
	    }
	    
	    List<Transaction> transactionList = new ArrayList<Transaction>();
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
		    		Transaction transaction = new Transaction(minQueue.peek().getUserId(),
		    				maxQueue.peek().getUserId(), Math.abs(minAmountPending));
	
		    		minQueue.peek().setAmountToSettle(0);
		    		maxQueue.peek().setAmountToSettle(0);
		    		transactionList.add(transaction);
		    		continue;
		    	}
		    	
		    	if(Math.abs(minAmountPending) < maxAmountPending) {
		    		Transaction transaction = new Transaction(minQueue.peek().getUserId(),
		    				maxQueue.peek().getUserId(), Math.abs(minAmountPending));
		    		
		    		minQueue.peek().setAmountToSettle(0);
		    		maxQueue.peek().setAmountToSettle(maxAmountPending - Math.abs(minAmountPending)); 
		    		transactionList.add(transaction);
		    		continue;
		    	}
		    	
	    		Transaction transaction = new Transaction(minQueue.peek().getUserId(),
	    				maxQueue.peek().getUserId(), Math.abs(maxAmountPending));
	    		
	    		maxQueue.peek().setAmountToSettle(0);
	    		minQueue.peek().setAmountToSettle(minAmountPending + maxAmountPending); 
	    		transactionList.add(transaction);
		    }
	    } catch(Exception e) {
	    	e.printStackTrace();
	    	return null;
	    }
	    
	    Transactions transactions = new Transactions();
	    transactions.setEventMode(eventMode);
	    transactions.setTransactions(transactionList);
	    return transactions;
	}
	
	
	private static Comparator<EventEntry> getEventUnitComparator(final boolean ASC) {
		return new Comparator<EventEntry>() {
			public int compare(EventEntry o1, EventEntry o2) {
				if(o1.getAmountToSettle() < o2.getAmountToSettle()) 
					return (ASC)?-1:1;
				if(o1.getAmountToSettle() > o2.getAmountToSettle()) 
					return (ASC)?1:-1;
				return 0;
			}
		};
	}
	

	
	
}
