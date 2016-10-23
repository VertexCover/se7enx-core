package com.vertexcover.se7enx.pojo.wrappers;

import java.util.List;

import com.vertexcover.se7enx.pojo.units.IndividualExpenseEventUnit;

public class IndividualExpenseEvent {
	
	private List<IndividualExpenseEventUnit> eventDetails;

	public List<IndividualExpenseEventUnit> getEventDetails() {
		return eventDetails;
	}

	public void setEventUnits(List<IndividualExpenseEventUnit> eventDetails) {
		this.eventDetails = eventDetails;
	}
	
}
