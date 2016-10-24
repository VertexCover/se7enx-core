package com.vertexcover.se7enx.core.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vertexcover.se7enx.core.constants.EventConstants;
import com.vertexcover.se7enx.pojo.units.EqualSharingEventUnit;
import com.vertexcover.se7enx.pojo.units.EventUnit;
import com.vertexcover.se7enx.pojo.units.IndividualExpenseEventUnit;
import com.vertexcover.se7enx.pojo.units.TransactionLogUnit;
import com.vertexcover.se7enx.pojo.wrappers.EqualSharingEvent;
import com.vertexcover.se7enx.pojo.wrappers.Event;
import com.vertexcover.se7enx.pojo.wrappers.IndividualExpenseEvent;
import com.vertexcover.se7enx.pojo.wrappers.TransactionLogs;

public class EventConvertor {
	
	public static Event toEvents(EqualSharingEvent eqsEvent) {
		double totalAmountPaid = 0;
		List<EqualSharingEventUnit> eqsUnitList = eqsEvent.getEventDetails();
		List<EventUnit> eventUnitList = new ArrayList<EventUnit>();
		for(EqualSharingEventUnit eqsUnit : eqsUnitList) {
			EventUnit eventUnit = new EventUnit();
			eventUnit.setUserId(eqsUnit.getUserId());
			eventUnit.setAmountPaid(eqsUnit.getAmountPaid());
			eventUnitList.add(eventUnit);
			totalAmountPaid += eventUnit.getAmountPaid();
		}
		
		double avgAmountPaid = totalAmountPaid / eventUnitList.size();
		for(EventUnit eventUnit : eventUnitList) {
			eventUnit.setAmountToBePaid(avgAmountPaid);
			eventUnit.setAmountToSettle(eventUnit.getAmountPaid() - eventUnit.getAmountToBePaid());
		}
		
		Event event = new Event();
		event.setEventMode(EventConstants.EQUAL_SHARING_MODE);
		event.setEventUnits(eventUnitList);
		return event;
	}
	


	public static Event toEvents(IndividualExpenseEvent ineEvent) {
		List<IndividualExpenseEventUnit> ineUnitList = ineEvent.getEventDetails();
		List<EventUnit> eventUnitList = new ArrayList<EventUnit>();
		for(IndividualExpenseEventUnit ineUnit : ineUnitList) {
			EventUnit eventUnit = new EventUnit();
			eventUnit.setUserId(ineUnit.getUserId());
			eventUnit.setAmountPaid(ineUnit.getAmountPaid());
			eventUnit.setAmountToBePaid(ineUnit.getAmountToBePaid());
			eventUnit.setAmountToSettle(eventUnit.getAmountPaid() - eventUnit.getAmountToBePaid());
			eventUnitList.add(eventUnit);
		}
		
		Event event = new Event();
		event.setEventMode(EventConstants.INDIVIDUAL_EXPENSES_MODE);
		event.setEventUnits(eventUnitList);
		return event;
	}

	
	
	public static Event toEvents(final String eventInfoJsonString, final String eventMode) {
		try {
			if(eventMode.equals(EventConstants.EQUAL_SHARING_MODE)){
				EqualSharingEvent eqsEvents = (EqualSharingEvent) JsonObjectMapper.toObject(eventInfoJsonString, EqualSharingEvent.class);
				return toEvents(eqsEvents);
			}
				
			if(eventMode.equals(EventConstants.INDIVIDUAL_EXPENSES_MODE)){
				IndividualExpenseEvent ineEvents = (IndividualExpenseEvent) JsonObjectMapper.toObject(eventInfoJsonString, IndividualExpenseEvent.class);
				return toEvents(ineEvents);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	

	public static Event toEvents(final TransactionLogs transactionLogs) {
		HashMap<Long, Double> pendingAmountOfUsers = new HashMap<Long, Double>();
		for(TransactionLogUnit transactionLogUnit : transactionLogs.getTransactionLogs()) {
			double pendingAmount = transactionLogUnit.getAmountToBePaid();
			if(pendingAmount == 0)
				continue;
			
			long fromUserId = transactionLogUnit.getFromUserId();
			if(!pendingAmountOfUsers.containsKey(fromUserId))
				pendingAmountOfUsers.put(fromUserId, (double) 0);
			pendingAmountOfUsers.put(fromUserId, pendingAmountOfUsers.get(fromUserId) - pendingAmount);
			
			long toUserId = transactionLogUnit.getToUserId();
			if(!pendingAmountOfUsers.containsKey(toUserId))
				pendingAmountOfUsers.put(toUserId, (double) 0);
			pendingAmountOfUsers.put(toUserId, pendingAmountOfUsers.get(toUserId) + pendingAmount);
		}
		
		List<EventUnit> eventUnitList = new ArrayList<EventUnit>();
		for (Map.Entry<Long, Double> entry : pendingAmountOfUsers.entrySet()) {
		    long userId = entry.getKey();
		    double pendingAmount = entry.getValue();
		    EventUnit eventUnit = new EventUnit();
		    eventUnit.setUserId(userId);
		    eventUnit.setAmountToSettle(pendingAmount);
		    eventUnitList.add(eventUnit);
		}
		
		Event event = new Event();
		event.setEventMode(EventConstants.DEFAULT_MODE);
		event.setEventUnits(eventUnitList);
		return event;
	}
	
	
	public static Event toEvents(final HashMap<Long, HashMap<Long, Double>> eventInfoMap) {
		HashMap<Long, Double> pendingAmountOfUsers = new HashMap<Long, Double>();
		for (Map.Entry<Long, HashMap<Long, Double>> entryOuter : eventInfoMap.entrySet()) {
		    long fromUserId = entryOuter.getKey();
		    HashMap<Long, Double> recievers = entryOuter.getValue();
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
	
		
		List<EventUnit> eventUnitList = new ArrayList<EventUnit>();
		for (Map.Entry<Long, Double> entry : pendingAmountOfUsers.entrySet()) {
		    long userId = entry.getKey();
		    double pendingAmount = entry.getValue();
		    EventUnit eventUnit = new EventUnit();
		    eventUnit.setUserId(userId);
		    eventUnit.setAmountToSettle(pendingAmount);
		    eventUnitList.add(eventUnit);
		}
		
		Event event = new Event();
		event.setEventMode(EventConstants.DEFAULT_MODE);
		event.setEventUnits(eventUnitList);
		return event;
	}
	
}
