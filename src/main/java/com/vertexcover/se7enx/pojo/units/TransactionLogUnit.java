package com.vertexcover.se7enx.pojo.units;

public class TransactionLogUnit {

	private long fromUserId;
	private long toUserId;
	private double amountToBePaid;
	
	public TransactionLogUnit() {}
	
	public TransactionLogUnit(long fromUserId, long toUserId, double amountToBePaid) {
		this.fromUserId = fromUserId;
		this.toUserId = toUserId;
		this.amountToBePaid = amountToBePaid;
	}
	
	public long getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(long fromUserId) {
		this.fromUserId = fromUserId;
	}
	
	
	public long getToUserId() {
		return toUserId;
	}
	public void setToUserId(long toUserId) {
		this.toUserId = toUserId;
	}
	
	
	public double getAmountToBePaid() {
		return amountToBePaid;
	}
	public void setAmountToBePaid(double amountToBePaid) {
		this.amountToBePaid = amountToBePaid;
	}
}
