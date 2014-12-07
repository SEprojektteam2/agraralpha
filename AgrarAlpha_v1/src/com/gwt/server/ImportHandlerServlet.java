package com.gwt.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.google.appengine.api.ThreadManager;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreInputStream;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.opencsv.CSVReader;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet to insert uploaded CSV file into MySQL Database
 * 
 * @author Fabian Weber
 *
 */
@SuppressWarnings("serial")
public class ImportHandlerServlet extends HttpServlet {

	public static final Logger log = Logger
			.getLogger(ImportHandlerServlet.class.getName());
	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();
	private int noOfThreads = 0;
	Connection conn;

	/*
	 * (non-Javadoc) Get the CSV File from BlobStore and create Threads to
	 * insert it into the MySQL Database
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		// Set up OutputStream
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");

		// Get the file from BlobStore
		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
		List<BlobKey> bkList = blobs.get("importCSV");
		BlobKey blobKey = bkList.get(0);
		BlobstoreInputStream blobStream = new BlobstoreInputStream(blobKey);

		// Open the File with a BufferedReader
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(blobStream, "utf-8"));

		// Create a CSV-Reader
		CSVReader csvReader = null;

		// Read all the lines from the CSV file and create threads to import
		// data into Database
		while (bufferedReader.read() != -1) {
			// Initialize CSV Reader with actual Buffer
			csvReader = new CSVReader(bufferedReader);
			// Add each row to a list containing String arrays
			List<String[]> rows = csvReader.readAll();

			// Create a new background Thread to insert all data in List "rows"
			Thread thread = ThreadManager
					.createBackgroundThread(new ImportHandlerThread(rows
							.toArray(new String[rows.size()][])));

			// There are only 10 Threads allowed. If there are already 10
			// Threads running, wait.
			while (noOfThreads > 9) {
				try {
					// Wait
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// Log the exception
					log.warning(e.getMessage());
				}
			}

			// Start the Thread
			thread.start();
			// Increase Thread counter
			noOfThreads++;

			try {
				// Wait until Thread has finished
				thread.join();
			} catch (InterruptedException e) {
				// Log the Exception
				log.warning(e.getMessage());
			}

			if (!thread.isAlive())
				// Decrease number of Threads
				noOfThreads--;
		}

		// Delete the file uploaded to BlobStore
		blobstoreService.delete(blobKey);
		// Connect to MySQL
		connectToDatabase();
		// Update countries in MySQL Table countries
		updateCountries();
		// Update items in MySQL Table items
		updateItems();
		// Update elements in MySQL Table elements
		updateElements();
		// Close CSVReader
		csvReader.close();
		// Flush
		out.flush();
		try {
			// Close Connection
			conn.close();
		} catch (SQLException e) {
			// Log Exception
			log.warning(e.getMessage());
		}
	}

	/**
	 * Connect to MySQL Database.
	 * 
	 * @author Fabian Weber
	 */
	private void connectToDatabase() {
		MySQLConnection database = new MySQLConnection();
		if (!database.connect()) {
			// Connection did nod succeed. Log a warning.
			log.warning("Connection did not succeed");
		}
		conn = database.returnConnection();
	}

	/**
	 * Get all countries stored in the MySQL table records and update MySQL
	 * table countries.
	 * 
	 * @author Fabian Weber
	 */
	private void updateCountries() {
		Statement deletion;
		// Delete all the values stored in table countries
		try {
			deletion = conn.createStatement();
			// Execute deletion
			deletion.executeUpdate("TRUNCATE countries");
		} catch (SQLException e1) {
			// Log Exception
			log.warning(e1.getMessage());
		}

		Statement insertion;
		// Insert new values
		try {
			insertion = conn.createStatement();
			insertion
					.executeUpdate("INSERT INTO countries (AreaCode, AreaName) SELECT distinct AreaCode, AreaName from records");
		} catch (SQLException e1) {
			// Log Exception
			log.warning(e1.getMessage());
		}
	}

	/**
	 * Get all elements stored in the MySQL table records and update MySQL table
	 * elements.
	 * 
	 * @author Fabian Weber
	 */
	private void updateElements() {
		Statement deletion;
		// Delete all the values stored in table elements
		try {
			deletion = conn.createStatement();
			// Execute the deletion
			deletion.executeUpdate("TRUNCATE elements");
		} catch (SQLException e1) {
			// Log the exception
			log.warning(e1.getMessage());
		}

		Statement insertion;
		// Insert new values
		try {
			insertion = conn.createStatement();
			// Execute insertion
			insertion
					.executeUpdate("INSERT INTO elements (ElementCode, ElementName) SELECT distinct ElementCode, ElementName from records");
		} catch (SQLException e1) {
			// Log exception
			log.warning(e1.getMessage());
		}
	}

	/**
	 * Get all items stored in the MySQL table records and update MySQL table
	 * items.
	 * 
	 * @author Fabian Weber
	 */
	private void updateItems() {
		Statement deletion;
		// Delete all the values stored in table items
		try {
			deletion = conn.createStatement();
			// Execute deletion
			deletion.executeUpdate("TRUNCATE items");
		} catch (SQLException e1) {
			// Log the Exception
			log.warning(e1.getMessage());
		}

		Statement insertion;
		// Insert new values
		try {
			insertion = conn.createStatement();
			// Execute the insertion
			insertion
					.executeUpdate("INSERT INTO items (ItemCode, ItemName) SELECT distinct ItemCode, ItemName from records");
		} catch (SQLException e1) {
			// Log the Exception
			log.warning(e1.getMessage());
		}
	}
}
