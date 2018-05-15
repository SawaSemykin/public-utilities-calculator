package ru.sawasemykin.web.jdbc;

import java.util.Date;

public class Reading {
	private int account;
	private Date readingDate;
	private int electricity;
	private int coldWater;
	private int hotWater;
	
	public Reading(int account, Date readingDate, int electricity, int coldWater, int hotWater) {		
		this.account = account;
		this.readingDate = readingDate;
		this.electricity = electricity;
		this.coldWater = coldWater;
		this.hotWater = hotWater;
	}	

	public Reading(int account, int electricity, int coldWater, int hotWater) {
		super();
		this.account = account;
		this.electricity = electricity;
		this.coldWater = coldWater;
		this.hotWater = hotWater;
	}	

	public int getAccount() {
		return account;
	}

	public void setAccount(int account) {
		this.account = account;
	}

	public Date getReadingDate() {
		return readingDate;
	}

	public void setReadingDate(Date readingDate) {
		this.readingDate = readingDate;
	}

	public int getElectricity() {
		return electricity;
	}

	public void setElectricity(int electricity) {
		this.electricity = electricity;
	}

	public int getColdWater() {
		return coldWater;
	}

	public void setColdWater(int coldWater) {
		this.coldWater = coldWater;
	}

	public int getHotWater() {
		return hotWater;
	}

	public void setHotWater(int hotWater) {
		this.hotWater = hotWater;
	}

	@Override
	public String toString() {
		return "Reading [account=" + account + ", electricity=" + electricity + ", coldWater=" + coldWater
				+ ", hotWater=" + hotWater + "]";
	}	

}
