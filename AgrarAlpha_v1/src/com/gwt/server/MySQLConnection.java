package com.gwt.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.google.appengine.api.utils.SystemProperty;

/**
 * Class to easy handle MySQL Connections. Provides functions to 
 * connect to our Cloud-SQL instance and use it in other classes.
 * @author Fabian Weber
 */
public class MySQLConnection {
	// initialize instance variables
	private String host, user, password, db, appengineSource;
	Connection conn;
	public static final Logger log = Logger.getLogger(MySQLConnection.class.getName());
	
	/**
	 * Constructor. Sets up the connection parameters.
	 * @author Fabian Weber
	 */
	public MySQLConnection(){
		// set up parameters
		this.host = "173.194.240.221";
		this.user = "root";
		this.password = "";
		this.db = "agrar";
		this.appengineSource = "agraralphav2:agrar";
	}
	
	/**
	 * Connects to database.
	 * @author Fabian Weber
	 * @return 	<code>true</code> if the connection succeeded
	 * 			<code>false</code> otherwise
	 */
	public boolean connect(){
		try {
			String url;
		    
			// Checks if application is running on AppEngine or not and set up corresponding url
			if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
		        // If application is running on AppEngine.
		        Class.forName("com.mysql.jdbc.GoogleDriver");
		        url = "jdbc:google:mysql://"+appengineSource+"/"+ db +"?user=" + user + "&charset=utf8";
		        if(!password.equals(""))
		        	url = url + "?password=" + password;
		    	} 
			else {
		        // If application is not running on AppEngine
		        Class.forName("com.mysql.jdbc.Driver");
		        url = "jdbc:mysql://"+ host +":3306/"+ db +"?user=" + user +"&charset=utf8";
		        if(!password.equals(""))
		        	url = url + "?password=" + password;
		        }
			// Get Connection
			conn = DriverManager.getConnection(url);
			
			// Return true if success
			return true;
		   	
		} catch (Exception e) {
			// Log exception
		    log.warning(e.getMessage());
		    // Return false if connection did not succeed
		    return false;
		}
	}
	
	
	/**
	 * Returns the SQL-Connection.
	 * @author Fabian Weber
	 * @return <code>Connection</code> 
	 */
	public Connection returnConnection(){
		return conn;
	}
	
	/**
	 * Closes the Connection.
	 * @author Fabian Weber
	 * @return	<code>true</code> if connecton close succeeded.
	 * 			<code>false</code> otherwise
	 */
	public boolean close(){
		try {
			// Close the connection
			conn.close();
			// Return true if closing the connection succeeded.
			return true;
		} catch (SQLException e) {
			// Log the exception
			log.warning(e.getMessage());
			// Return false if the connection did not succeed.
			return false;
		}
	}
}
