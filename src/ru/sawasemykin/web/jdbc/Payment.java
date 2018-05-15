package ru.sawasemykin.web.jdbc;

public class Payment {
	public final static double WATER_RATE = 20.43;
	public final static double ELECTRICITY_RATE = 3.53;
	public final static double REMOVE_WATER_RATE = 25.04;	
	private int electrVolume;
	private double electrValue;
	private int cWVolume;
	private double cWValue;
	private int hWVolume;
	private double hWValue;
	private int remWVolume;
	private double remWValue;
	private double totalSum;
	
	public Payment(Reading[] readingsPair) {	
		int[] elReadingsPair = new int[2];
		elReadingsPair[0] = readingsPair[0].getElectricity();
		elReadingsPair[1] = readingsPair[1].getElectricity();
		electrVolume = calcVolume(elReadingsPair);
		electrValue = calcValue(ELECTRICITY_RATE, elReadingsPair);
		
		int[] cWReadingsPair = new int[2];
		cWReadingsPair[0] = readingsPair[0].getColdWater();
		cWReadingsPair[1] = readingsPair[1].getColdWater();
		cWVolume = calcVolume(cWReadingsPair);
		cWValue = calcValue(WATER_RATE, cWReadingsPair);
		
		int[] hWReadingsPair = new int[2];
		hWReadingsPair[0] = readingsPair[0].getHotWater();
		hWReadingsPair[1] = readingsPair[1].getHotWater();
		hWVolume = calcVolume(hWReadingsPair);
		hWValue = calcValue(WATER_RATE, hWReadingsPair);
		
		int[] wRemReadingsPair = new int[2];
		wRemReadingsPair[0] = cWReadingsPair[0] + hWReadingsPair[0];
		wRemReadingsPair[1] = cWReadingsPair[1] + hWReadingsPair[1];
		remWVolume = calcVolume(wRemReadingsPair);
		remWValue = calcValue(REMOVE_WATER_RATE, wRemReadingsPair);
		
		totalSum = electrValue + cWValue + hWValue + remWValue;
	};
	
	private int calcVolume(int[] readingsPair) {
		return readingsPair[1] - readingsPair[0];
	}
	
	private double calcValue(double rate, int[] readingsPair) {
		return rate*(readingsPair[1] - readingsPair[0]);
	}

	public int getElectrVolume() {
		return electrVolume;
	}

	public void setElectrVolume(int electrVolume) {
		this.electrVolume = electrVolume;
	}

	public double getElectrValue() {
		return electrValue;
	}

	public void setElectrValue(double electrValue) {
		this.electrValue = electrValue;
	}

	public int getcWVolume() {
		return cWVolume;
	}

	public void setcWVolume(int cWVolume) {
		this.cWVolume = cWVolume;
	}

	public double getcWValue() {
		return cWValue;
	}

	public void setcWValue(double cWValue) {
		this.cWValue = cWValue;
	}

	public int gethWVolume() {
		return hWVolume;
	}

	public void sethWVolume(int hWVolume) {
		this.hWVolume = hWVolume;
	}

	public double gethWValue() {
		return hWValue;
	}

	public void sethWValue(double hWValue) {
		this.hWValue = hWValue;
	}

	public int getRemWVolume() {
		return remWVolume;
	}

	public void setRemWVolume(int remWVolume) {
		this.remWVolume = remWVolume;
	}

	public double getRemWValue() {
		return remWValue;
	}

	public void setRemWValue(double remWValue) {
		this.remWValue = remWValue;
	}

	public double getTotalSum() {		 
		return totalSum;
	}

	public void setTotalSum(double totalSum) {
		this.totalSum = totalSum;
	}

	

}
