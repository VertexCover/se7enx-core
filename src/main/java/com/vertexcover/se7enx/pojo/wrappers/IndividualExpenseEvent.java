package com.vertexcover.se7enx.pojo.wrappers;

import java.util.List;
import com.vertexcover.se7enx.pojo.units.IndividualExpenseEventEntry;

public class IndividualExpenseEvent {
	
	private List<IndividualExpenseEventEntry> eventEntries;

	public List<IndividualExpenseEventEntry> getEventEntries() {
		return eventEntries;
	}

	public void setEventUnits(List<IndividualExpenseEventEntry> eventEntries) {
		this.eventEntries = eventEntries;
	}
	
}
