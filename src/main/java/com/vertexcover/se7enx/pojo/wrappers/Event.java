package com.vertexcover.se7enx.pojo.wrappers;

import java.util.List;

import com.vertexcover.se7enx.pojo.units.EventEntry;

public class Event {
	
	private String eventMode;
	private List<EventEntry> eventEntries;
	
	
	public String getEventMode() {
		return eventMode;
	}
	
	public void setEventMode(String eventMode) {
		this.eventMode = eventMode;
	}
	
	
	public List<EventEntry> getEventEntries() {
		return eventEntries;
	}
	
	public void setEventEntries(List<EventEntry> eventEntries) {
		this.eventEntries = eventEntries;
	}

}
