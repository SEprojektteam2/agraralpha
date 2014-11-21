package com.gwt.server;

import java.sql.*;
import java.util.ArrayList;

import com.gwt.server.MySQLConnection;
//import com.google.appengine.api.utils.SystemProperty;
import com.google.gwt.visualization.client.*;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.gwt.client.DataManagerService;

@SuppressWarnings("serial")
public class DataManagerServiceImpl extends RemoteServiceServlet implements
		DataManagerService {
		
	Connection conn;
	
	//Create connection to mysqldatabase
	private void connectToDatabase(){ 
		MySQLConnection database = new MySQLConnection("173.194.253.240:3306","root","","agrar","agraralphav1:agrar");
		if(database.connect()){
			System.out.println("<html><head></head><body>Connection Started</body></html>");
		}
	    conn = database.returnConnection();
	}
	
	public ArrayList<String> getCountries(){
		//Array mit Strings als Rueckgabe
		ArrayList<String> countries = new ArrayList<String>();
		countries.add(0, "World");
		
		connectToDatabase();
		//limit 1 bei der Abfrage (entfernt die Dupletten)
		// if you only need a few columns, specify them by name instead of using "*"
		String query = "SELECT distinct AreaName FROM records WHERE ElementName='Export Quantity'";
		
		// create the java statement
		Statement st = null;
		try {
			st = conn.createStatement();
			
			// execute the query, and get a java resultset
			ResultSet rs = null;
			rs = st.executeQuery(query);
							
			// iterate through the java resultset
			int i=1;
						
			while (rs.next())
			{
				String resultTemp = rs.getString("AreaName");
				countries.add(i, resultTemp);
				i++;
			}
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return (countries);
	}
	
	public ArrayList<String> getProducts(){
		//Array mit Strings als Rueckgabe
		ArrayList<String> products = new ArrayList<String>();
		products.add(0, " ");
		
		connectToDatabase();
		//limit 1 bei der Abfrage (entfernt die Dupletten)
		// if you only need a few columns, specify them by name instead of using "*"
		String query = "SELECT distinct ItemName FROM records WHERE ElementName='Export Quantity'";
		
		// create the java statement
		Statement st = null;
		try {
			st = conn.createStatement();
			
			// execute the query, and get a java resultset
			ResultSet rs = null;
			rs = st.executeQuery(query);
							
			// iterate through the java resultset
			int i=1;
						
			while (rs.next())
			{
				String resultTemp = rs.getString("ItemName");
				products.add(i, resultTemp);
				i++;
			}
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return (products);
	}
		
}	
	

