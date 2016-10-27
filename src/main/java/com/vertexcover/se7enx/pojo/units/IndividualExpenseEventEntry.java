package com.vertexcover.se7enx.pojo.units;

public class IndividualExpenseEventEntry {
	
	private long userId;
	private double amountPaid;
	private double amountToBePaid;
	
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	
	public double getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}	

	
	public double getAmountToBePaid() {
		return amountToBePaid;
	}
	
	public void setAmountToBePaid(double amountToBePaid) {
		this.amountToBePaid = amountToBePaid;
	}
	
}
