package com.vertexcover.se7enx.pojo.wrappers;

import java.util.List;

import com.vertexcover.se7enx.pojo.units.TransactionLogUnit;


public class TransactionLogs {
	private String eventMode;
	private List<TransactionLogUnit> transactionLogs;
	public String getEventMode() {
		return eventMode;
	}
	public void setEventMode(String eventMode) {
		this.eventMode = eventMode;
	}
	public List<TransactionLogUnit> getTransactionLogs() {
		return transactionLogs;
	}
	public void setTransactionLogs(List<TransactionLogUnit> transactionLogs) {
		this.transactionLogs = transactionLogs;
	}
}
