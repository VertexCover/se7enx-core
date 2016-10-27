package com.vertexcover.se7enx.pojo.wrappers;

import java.util.List;

import com.vertexcover.se7enx.pojo.units.EqualSharingEventEntry;

public class EqualSharingEvent {
	
	private List<EqualSharingEventEntry> eventEntries;

	public List<EqualSharingEventEntry> getEventEntries() {
		return eventEntries;
	}

	public void setEventDetails(List<EqualSharingEventEntry> eventEntries) {
		this.eventEntries = eventEntries;
	}

}
