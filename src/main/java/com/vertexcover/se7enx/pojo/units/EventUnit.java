package com.vertexcover.se7enx.pojo.units;

public class EventUnit {
	
	private long userId;
	private double amountPaid;
	private double amountToBePaid;
	private double amountToSettle;
	
	
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
	
	
	public double getAmountToSettle() {
		return amountToSettle;
	}
	
	public void setAmountToSettle(double amountToSettle) {
		this.amountToSettle = amountToSettle;
	}
}
