package com.vertexcover.se7enx.core.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vertexcover.se7enx.core.constants.EventConstants;
import com.vertexcover.se7enx.pojo.units.EqualSharingEventEntry;
import com.vertexcover.se7enx.pojo.units.EventEntry;
import com.vertexcover.se7enx.pojo.units.IndividualExpenseEventEntry;
import com.vertexcover.se7enx.pojo.units.Transaction;
import com.vertexcover.se7enx.pojo.wrappers.EqualSharingEvent;
import com.vertexcover.se7enx.pojo.wrappers.Event;
import com.vertexcover.se7enx.pojo.wrappers.IndividualExpenseEvent;
import com.vertexcover.se7enx.pojo.wrappers.Transactions;

public class EventConvertor {
	
	public static Event convert(EqualSharingEvent eseEvent) {
		double totalAmountPaid = 0;
		List<EqualSharingEventEntry> eseEntries = eseEvent.getEventEntries();
		List<EventEntry> eventEntries = new ArrayList<EventEntry>();
		for(EqualSharingEventEntry eseEntry : eseEntries) {
			EventEntry entry = new EventEntry();
			entry.setUserId(eseEntry.getUserId());
			entry.setAmountPaid(eseEntry.getAmountPaid());
			eventEntries.add(entry);
			totalAmountPaid += entry.getAmountPaid();
		}
		
		double avgAmountPaid = totalAmountPaid / eventEntries.size();
		for(EventEntry entry : eventEntries) {
			entry.setAmountToBePaid(avgAmountPaid);
			entry.setAmountToSettle(entry.getAmountPaid() - entry.getAmountToBePaid());
		}
		
		Event event = new Event();
		event.setEventMode(EventConstants.EQUAL_SHARING_MODE);
		event.setEventEntries(eventEntries);
		return event;
	}
	


	public static Event convert(IndividualExpenseEvent ineEvent) {
		List<IndividualExpenseEventEntry> ineEntries = ineEvent.getEventEntries();
		List<EventEntry> eventEntries = new ArrayList<EventEntry>();
		for(IndividualExpenseEventEntry ineEntry : ineEntries) {
			EventEntry entry = new EventEntry();
			entry.setUserId(ineEntry.getUserId());
			entry.setAmountPaid(ineEntry.getAmountPaid());
			entry.setAmountToBePaid(ineEntry.getAmountToBePaid());
			entry.setAmountToSettle(entry.getAmountPaid() - entry.getAmountToBePaid());
			eventEntries.add(entry);
		}
		
		Event event = new Event();
		event.setEventMode(EventConstants.INDIVIDUAL_EXPENSES_MODE);
		event.setEventEntries(eventEntries);
		return event;
	}

	
	
	public static Event convert(final String eventInfoJson, final String eventMode) {
		try {
			if(eventMode.equals(EventConstants.EQUAL_SHARING_MODE)){
				EqualSharingEvent eqsEvents = (EqualSharingEvent) JsonObjectMapper.toObject(eventInfoJson, EqualSharingEvent.class);
				return convert(eqsEvents);
			}
				
			if(eventMode.equals(EventConstants.INDIVIDUAL_EXPENSES_MODE)){
				IndividualExpenseEvent ineEvents = (IndividualExpenseEvent) JsonObjectMapper.toObject(eventInfoJson, IndividualExpenseEvent.class);
				return convert(ineEvents);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	

	public static Event convert(final Transactions transactions) {
		HashMap<Long, Double> pendingAmountOfUsers = new HashMap<Long, Double>();
		for(Transaction transaction : transactions.getTransactions()) {
			double pendingAmount = transaction.getAmountToBePaid();
			if(pendingAmount == 0)
				continue;
			
			long fromUserId = transaction.getFromUserId();
			if(!pendingAmountOfUsers.containsKey(fromUserId))
				pendingAmountOfUsers.put(fromUserId, (double) 0);
			pendingAmountOfUsers.put(fromUserId, pendingAmountOfUsers.get(fromUserId) - pendingAmount);
			
			long toUserId = transaction.getToUserId();
			if(!pendingAmountOfUsers.containsKey(toUserId))
				pendingAmountOfUsers.put(toUserId, (double) 0);
			pendingAmountOfUsers.put(toUserId, pendingAmountOfUsers.get(toUserId) + pendingAmount);
		}
		
		List<EventEntry> eventEntries = new ArrayList<EventEntry>();
		for (Map.Entry<Long, Double> entry : pendingAmountOfUsers.entrySet()) {
		    long userId = entry.getKey();
		    double pendingAmount = entry.getValue();
		    EventEntry eventEntry = new EventEntry();
		    eventEntry.setUserId(userId);
		    eventEntry.setAmountToSettle(pendingAmount);
		    eventEntries.add(eventEntry);
		}
		
		Event event = new Event();
		event.setEventMode(EventConstants.DEFAULT_MODE);
		event.setEventEntries(eventEntries);
		return event;
	}
	
	
	public static Event convert(final HashMap<Long, HashMap<Long, Double>> transactions) {
		HashMap<Long, Double> pendingAmountOfUsers = new HashMap<Long, Double>();
		for (Map.Entry<Long, HashMap<Long, Double>> entryOuter : transactions.entrySet()) {
		    long fromUserId = entryOuter.getKey();
		    HashMap<Long, Double> recievers = entryOuter.getValue();
		    if(recievers == null)
		    	continue; 
		    
		    for (Map.Entry<Long, Double> entryInner : recievers.entrySet()) {
		    	long toUserId = entryInner.getKey();
		    	double pendingAmount = entryInner.getValue();
				if(pendingAmount == 0)
					continue;
				
				if(!pendingAmountOfUsers.containsKey(fromUserId))
					pendingAmountOfUsers.put(fromUserId, (double) 0);
				pendingAmountOfUsers.put(fromUserId, pendingAmountOfUsers.get(fromUserId) - pendingAmount);
				
				if(!pendingAmountOfUsers.containsKey(toUserId))
					pendingAmountOfUsers.put(toUserId, (double) 0);
				pendingAmountOfUsers.put(toUserId, pendingAmountOfUsers.get(toUserId) + pendingAmount);
		    }
		}
	
		
		List<EventEntry> eventEntries = new ArrayList<EventEntry>();
		for (Map.Entry<Long, Double> entry : pendingAmountOfUsers.entrySet()) {
		    long userId = entry.getKey();
		    double pendingAmount = entry.getValue();
		    EventEntry eventEntry = new EventEntry();
		    eventEntry.setUserId(userId);
		    eventEntry.setAmountToSettle(pendingAmount);
		    eventEntries.add(eventEntry);
		}
		
		Event event = new Event();
		event.setEventMode(EventConstants.DEFAULT_MODE);
		event.setEventEntries(eventEntries);
		return event;
	}
	
}
