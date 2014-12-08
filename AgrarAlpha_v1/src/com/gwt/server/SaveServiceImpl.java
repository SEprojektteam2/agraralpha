package com.gwt.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.gwt.client.SaveService;

/**
 * Class to get saved Data from SQL Server
 * 
 * @author Fabian Weber
 *
 */
@SuppressWarnings("serial")
public class SaveServiceImpl extends RemoteServiceServlet implements
		SaveService {
	// add Logger to log exceptions
	public static final Logger log = Logger.getLogger(SaveServiceImpl.class
			.getName());
	// add Connection to use in functions
	private Connection conn;

	/**
	 * Constructor. Creates connection to mysql server
	 * 
	 * @author Fabian Weber
	 * 
	 */
	public SaveServiceImpl() {
		connectToDatabase();
	}

	/**
	 * Connects to database
	 * 
	 * @author Fabian Weber
	 */
	private boolean connectToDatabase() {
		MySQLConnection database = new MySQLConnection();
		if (database.connect()) {
			// set database connection
			conn = database.returnConnection();
			return true;
		} else {
			log.warning("Error connecting to database");
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwt.client.SaveService#save(int, java.lang.String,
	 * java.lang.String, java.lang.String, boolean, java.lang.String)
	 */
	public void save(int year, String country, String product, String type,
			boolean perCapita, String name) throws IllegalArgumentException{

		// Set date format
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss");

		// Set TimeZone
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+1"));
		;
		Date date = new Date();

		// Parse boolean perCapita to Integer 1 or 0
		int capita = 0;
		if (perCapita == true)
			capita = 1;

		Statement insertion;
		try {
			insertion = conn.createStatement();
			// Insert values into database
			insertion
					.executeUpdate("INSERT INTO store (year,country,product,type,name,perCapita,date) VALUES ('"
							+ String.valueOf(year)
							+ "','"
							+ country
							+ "','"
							+ product
							+ "','"
							+ type
							+ "','"
							+ name
							+ "','"
							+ capita + "','" + dateFormat.format(date) + "')");
		} catch (SQLException e1) {
			// Log Exception
			log.warning(e1.getMessage());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwt.client.SaveService#getSavedData()
	 */
	public ArrayList<String[]> getSavedData() {
		// Initialize ArrayList that will be returned
		ArrayList<String[]> savedData = new ArrayList<String[]>();

		// Set up query to get all the data in the SQL table
		String query = "SELECT * FROM store ORDER BY date";
		Statement st = null;
		try {
			st = conn.createStatement();

			// Execute the query, and get a java ResulSet
			ResultSet rs = st.executeQuery(query);

			// Iterate through the java ResultSet
			while (rs.next()) {
				String[] resultTemp = new String[8];

				// Add all values of the result set to a string array
				resultTemp[0] = String.valueOf(rs.getInt("id"));
				resultTemp[1] = rs.getString("year");
				resultTemp[2] = rs.getString("country");
				resultTemp[3] = rs.getString("product");
				resultTemp[4] = rs.getString("type");
				resultTemp[5] = rs.getString("perCapita");
				resultTemp[6] = rs.getString("name");
				resultTemp[7] = rs.getString("date");

				// Add the string-array to the ArrayList
				savedData.add(resultTemp);
			}
		} catch (SQLException e) {
			// Log exception
			log.warning(e.getMessage());
		}
		// Return the ArrayList
		return (savedData);
	}

}
