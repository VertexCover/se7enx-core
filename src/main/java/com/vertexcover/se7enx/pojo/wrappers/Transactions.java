package com.vertexcover.se7enx.pojo.wrappers;

import java.util.List;

import com.vertexcover.se7enx.pojo.units.Transaction;


public class Transactions {
	private String eventMode;
	private List<Transaction> transactions;
	public String getEventMode() {
		return eventMode;
	}
	public void setEventMode(String eventMode) {
		this.eventMode = eventMode;
	}
	public List<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
}
