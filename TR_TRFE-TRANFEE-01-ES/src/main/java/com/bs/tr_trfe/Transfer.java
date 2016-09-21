package com.bs.tr_trfe;

public class Transfer {

	private double amount=0.0;
	private double fee=0.0;
	private String isVip="0";

	
	public Transfer(double amount, String isVip, double fee) {
		super();
		this.amount = amount;
		this.fee = fee;
		this.isVip = isVip;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getFee() {
		return fee;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}
	public String getIsVip() {
		return isVip;
	}
	public void setIsVip(String isVip) {
		this.isVip = isVip;
	}
	@Override
	public String toString() {
		return "Transfer [amount=" + amount + ", fee=" + fee + ", isVip=" + isVip + "]";
	}

	
	
}
