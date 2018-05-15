package ru.sawasemykin.web.jdbc;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 * Servlet implementation class ReadingControllerServlet
 */
@WebServlet("/ReadingControllerServlet")
public class ReadingControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ReadingDbUtil readingDbUtil;	
	
	@Resource(name="jdbc/public_utilities_calculator")
	DataSource dataSource;	

	@Override
	public void init() throws ServletException {		
		super.init();
		
		try {
			readingDbUtil = new ReadingDbUtil(dataSource);
		} 
		catch (Exception exc) {
			throw new ServletException(exc);
		}				
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {			
			String theCommand = request.getParameter("command");
			if (theCommand == null)
				theCommand = "SERVE_THE_CLIENT";
			
			switch (theCommand) {
			case "SERVE_THE_CLIENT": 
				serveClient(request, response);
				break;
				
			case "SEND_READING":				
				sendReading(request, response);
				break;
				
			default:
				serveClient(request, response);					
			}	
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}		
		
	}

	private void serveClient(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		// recognize the client from greeting page
		int theAccount = Integer.parseInt(request.getParameter("account"));
		readingDbUtil.setAccount(theAccount);
		HttpSession theSession = request.getSession(true);
		theSession.setAttribute("theAccount", theAccount);	
		Client theClient = readingDbUtil.getClient(theAccount);
		
		// calc client's payments
		Reading[] readingsPair = readingDbUtil.getReadingsPair();		
		Payment thePayment = new Payment(readingsPair);				
		theClient.setPayment(thePayment);
		
		// add theClient to the request
		request.setAttribute("THE_CLIENT", theClient);
		
		// send to the report page
		RequestDispatcher dispatcher = request.getRequestDispatcher("/report.jsp");
		dispatcher.forward(request, response);		
	}

	private void sendReading(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession theSession = request.getSession();
		
		// create Reading object
		int theAccount = (int) theSession.getAttribute("theAccount");
		int electricity = Integer.parseInt(request.getParameter("electricity"));
		int coldWater = Integer.parseInt(request.getParameter("coldWater"));
		int hotWater = Integer.parseInt(request.getParameter("hotWater"));		
		Reading reading = new Reading(theAccount, new Date(), electricity, coldWater, hotWater);
		
		// add the readings to database
		readingDbUtil.addReading(reading);
		
		// send to readingConfirmation page
		RequestDispatcher dispatcher = request.getRequestDispatcher("/report.jsp");
		dispatcher.forward(request, response);		
		
	}	
}
