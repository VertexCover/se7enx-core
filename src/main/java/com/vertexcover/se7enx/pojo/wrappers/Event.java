package com.vertexcover.se7enx.pojo.wrappers;

import java.util.List;

import com.vertexcover.se7enx.pojo.units.EventUnit;

public class Event {
	
	private String eventMode;
	private List<EventUnit> eventUnits;
	
	
	public String getEventMode() {
		return eventMode;
	}
	
	public void setEventMode(String eventMode) {
		this.eventMode = eventMode;
	}
	
	
	public List<EventUnit> getEventUnits() {
		return eventUnits;
	}
	
	public void setEventUnits(List<EventUnit> eventUnits) {
		this.eventUnits = eventUnits;
	}

}
