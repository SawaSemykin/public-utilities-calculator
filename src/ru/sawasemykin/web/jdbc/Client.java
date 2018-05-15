package ru.sawasemykin.web.jdbc;

import java.util.Map;

public class Client {
	private int account;
	private String firstName;
	private String lastName;
	private Payment payment;	
		
	public Client(int account, String firstName, String lastName, Payment payment) {
		super();
		this.account = account;
		this.firstName = firstName;
		this.lastName = lastName;
		this.payment = payment;
	}

	public int getAccount() {
		return account;
	}

	public void setAccount(int account) {
		this.account = account;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	
	

}
