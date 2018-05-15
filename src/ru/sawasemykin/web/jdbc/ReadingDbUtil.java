package ru.sawasemykin.web.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class ReadingDbUtil {
	
	private DataSource dataSource;
	private int account;

	public ReadingDbUtil(DataSource dataSource) {		
		this.dataSource = dataSource;
	}

	public Client getClient(int theAccount) throws Exception {
		
		Client theClient = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;		
		
		try {
		// get the connection to the database
		myConn = dataSource.getConnection();
		
		// create sql
		String sql = "select * from public_utilities_calculator.client where account=?";
		
		// prepare statement
		myStmt = myConn.prepareStatement(sql);
		
		// set param
		myStmt.setInt(1, theAccount);
		
		// execute statement
		myRs = myStmt.executeQuery();
		
		// retrieve client's first name and last name based on the account cell
		if (myRs.next()) {			
			String firstName = myRs.getString("first_name");
			String lastName = myRs.getString("last_name");
			
			theClient = new Client(theAccount, firstName, lastName, null);
		}
		
		return theClient;
		
		// clean up jdbc code
		}
		finally {
			close(myConn, myStmt, myRs);
		}
	}

	public void addReading(Reading reading) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;		
		
		try {			
			myConn = dataSource.getConnection();			
			
			String sql = "insert into public_utilities_calculator.reading "
					+ "(account, reading_date, electricity, cold_water, hot_water) "
					+ "values (?, ?, ?, ?, ?)";
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setInt(1, reading.getAccount());
			Date sqlDate = new Date(reading.getReadingDate().getTime());
			myStmt.setDate(2, sqlDate);
			myStmt.setInt(3, reading.getElectricity());
			myStmt.setInt(4, reading.getColdWater());
			myStmt.setInt(5, reading.getHotWater());			
			
			// execute sql query
			myStmt.execute();
		}
		finally {
			// close up jdbc code
			close(myConn, myStmt, null);
		}		
	}

	public Reading[] getReadingsPair() throws Exception {		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			// get a connection to a database
			myConn = dataSource.getConnection();
			
		
			// create sql		
			String sql = "Select * from public_utilities_calculator.reading "
					+ "where account=? and "
					+ "(date_format(reading_date, \"%c %Y\")=? or "
					+ "date_format(reading_date, \"%c %Y\")=?)";			
			
			// prepare a statement object
			myStmt = myConn.prepareStatement(sql);
			
			// set params			
			myStmt.setInt(1, account);
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M yyyy");
			YearMonth now = YearMonth.now();
			YearMonth initReading = now.minusMonths(2);			
			myStmt.setString(2, initReading.format(formatter));
			YearMonth finalReading = now.minusMonths(1);			
			myStmt.setString(3, finalReading.format(formatter));
			
			// execute statement 
			myRs = myStmt.executeQuery();
			
			// process the result set
			Reading[] readingsPair = new Reading[2];
			byte i = 0;
			while (myRs.next()) {
				int account = myRs.getInt(2);				
				int electricity = myRs.getInt(4);
				int coldWater = myRs.getInt(5);
				int hotWater = myRs.getInt(6);
				Reading tempReading = new Reading(account, electricity, coldWater, hotWater);
				readingsPair[i++] = tempReading;				
			}
			return readingsPair;			
		} finally {
			// clean up jdbc code
			close(myConn, myStmt, myRs);
		}		
	}
	
	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {		
		try {				
			if (myRs != null)
				myRs.close();			
			
			if (myStmt != null)
				myStmt.close();
			
			if (myConn != null)
				myConn.close();
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}		
	}	

	public int getAccount() {
		return account;
	}

	public void setAccount(int theAccount) {
		account = theAccount;
	}
}
