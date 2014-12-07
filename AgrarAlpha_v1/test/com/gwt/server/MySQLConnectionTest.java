package com.gwt.server;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

/**
 * JUnit Test to test the Class MySQLConnection
 * 
 * @author Fabian Weber
 *
 */
public class MySQLConnectionTest {

	/**
	 * Tests the method connect() of Class MySQLConnection
	 * 
	 * @author Fabian Weber
	 */
	@Test
	public void testConnect() {
		// Create new MySQLConnection
		MySQLConnection connection = new MySQLConnection();
		// Connect to Database
		if (connection.connect() == false)
			fail("Could not connect");
	}

	/**
	 * Tests the method returnConnection() of Class MySQLConnection
	 * 
	 * @author Fabian Weber
	 * @throws SQLException
	 */
	@Test
	public void testReturnConnection() throws SQLException {
		// Initialize Connection
		Connection testConnection = null;
		// Create new MySQLConnection
		MySQLConnection connection = new MySQLConnection();
		// Connect to Database
		if (connection.connect() == false)
			fail("Could not connect");
		// Get MySQL Connection
		testConnection = connection.returnConnection();
		// Check if Connection does not equal null
		if (testConnection.equals(null))
			fail("Could not get Connection");
		// Check if Connection succeeded
		if (!testConnection.isValid(500))
			fail("Got invalid Connection");
	}

	/**
	 * Tests the method close() of Class MySQLConnection
	 * 
	 * @author Fabian Weber
	 * @throws SQLException
	 */
	@Test
	public void testCloseConnection() throws SQLException {
		// Initialize Connection
		Connection testConnection = null;
		// Create new MySQLConnection
		MySQLConnection connection = new MySQLConnection();
		// Connect to Database
		if (connection.connect() == false)
			fail("Could not connect");
		// Get MySQL Connection
		testConnection = connection.returnConnection();
		// Check if Connection does not equal null
		if (testConnection.equals(null))
			fail("Could not get Connection");
		// Check if Connection succeeded
		if (!testConnection.isValid(500))
			fail("Got invalid Connection");
		// Close MySQLConnection
		if (!connection.close())
			fail("Connection could not be closed");
		// Set Connection
		testConnection = connection.returnConnection();
		if (testConnection.isValid(500))
			fail("Connection is still valid");
	}

}