package com.gwt.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * A single Thread to split SQL insertions into multiple Threads
 * 
 * @author Fabian Weber
 */
public class ImportHandlerThread implements Runnable {
	private String[][] insertion;
	public static final Logger log = Logger.getLogger(ImportHandlerThread.class
			.getName());

	/**
	 * Constructor. Set up the string-Array that contains data to insert into
	 * the database.
	 * 
	 * @param insertion
	 *            the String two-dimensional array that contains the data to
	 *            insert.
	 */
	public ImportHandlerThread(String[][] insertion) {
		this.insertion = insertion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		PreparedStatement stmt = null;
		try {
			// Initialization of MySQL Connection
			MySQLConnection database = new MySQLConnection();
			database.connect();
			Connection conn = database.returnConnection();

			// Set up statement for MySQL insertion
			String statement = "INSERT INTO records (domainCode, domain, areaCode, areaName, elementCode, elementName, itemCode, itemName, year, unit, value, flag, flagD) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? )";
			stmt = conn.prepareStatement(statement);

			// Add each array-row to MySQL batch
			for (int y = 0; y < insertion.length; y++) {
				// Check if it is the head row of the csv File
				if (!insertion[y][2].equals("AreaCode")) {
					try {
						// Convert the value to a float 
						Float value = new Float(0);
						if (!insertion[y][10].equals(""))
							value = Float.parseFloat(insertion[y][10]);
						
						// Add the values to the statement
						stmt.setString(1, insertion[y][0]);
						stmt.setString(2, insertion[y][1]);
						stmt.setInt(3, Integer.parseInt(insertion[y][2]));
						stmt.setString(4, insertion[y][3]);
						stmt.setInt(5, Integer.parseInt(insertion[y][4]));
						stmt.setString(6, insertion[y][5]);
						stmt.setInt(7, Integer.parseInt(insertion[y][6]));
						stmt.setString(8, insertion[y][7]);
						stmt.setInt(9, Integer.parseInt(insertion[y][8]));
						stmt.setString(10, insertion[y][9]);
						stmt.setFloat(11, value);
						stmt.setString(12, insertion[y][11]);
						stmt.setString(13, insertion[y][12]);
						// Add statement to batch
						stmt.addBatch();
					} catch (SQLException e) {
						// Log the error 
						log.warning(e.getMessage());
					}
				}
			}
			try {
				// Execute MySQL insertion
				stmt.executeBatch();

				// delete header row with Row-Descriptions
//				String query = "DELETE FROM records WHERE domainCode = 'Domain Code' AND domain = 'Domain' AND areaCode = 'AreaCode' AND areaName = 'AreaName' AND elementCode = 'ElementCode' AND elementName = 'ElementName' AND itemCode = 'ItemCode' AND itemName = 'ItemName' AND year = 'Year' AND unit = 'Unit' AND value = 'Value' AND flag = 'Flag' AND flagD = 'FlagD'";
//				PreparedStatement preparedStmt = conn.prepareStatement(query);
//				preparedStmt.execute();

			} catch (SQLException e) {
				log.warning(e.toString());
			}

			// close mysql connection
			conn.close();

		} catch (SQLException e1) {
			log.warning(e1.toString());
		}
	}
}
