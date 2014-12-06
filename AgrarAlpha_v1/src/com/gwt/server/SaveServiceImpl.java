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
import com.gwt.client.HighchartService;
import com.gwt.client.SaveService;

@SuppressWarnings("serial")
public class SaveServiceImpl extends RemoteServiceServlet implements
		SaveService {
	public static final Logger log = Logger.getLogger(SaveServiceImpl.class.getName());
	private Connection conn;
	
	public SaveServiceImpl(){
		connectToDatabase();
	}
	
	//Create connection to mysqldatabase
	private void connectToDatabase(){ 
		MySQLConnection database = new MySQLConnection();
		if(database.connect()){
			System.out.println("<html><head></head><body>Connection Started</body></html>");
		}
	    conn = database.returnConnection();
	}

	@Override
	public void save(int year, String country, String product, String type,
			boolean perCapita, String name) throws IllegalArgumentException {
		
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss");
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+1"));;
		Date date = new Date();
		int capita = 0;
		if(perCapita == true)
			capita = 1;
		Statement insertion;
		try {
			insertion = conn.createStatement();
			insertion.executeUpdate("INSERT INTO store (year,country,product,type,name,perCapita,date) VALUES ('" + String.valueOf(year) + "','" + country + "','" + product + "','" + type + "','" + name + "','" + capita + "','" + dateFormat.format(date) + "')");
			log.warning("INSERTION SUCCESS!");
		} catch (SQLException e1) {
			log.warning(e1.getErrorCode() + e1.getMessage());
		}
		
	}
	
	public ArrayList<String[]> getSavedData(){
		ArrayList<String[]> savedData = new ArrayList<String[]>();
		
		String query = "SELECT * FROM store ORDER BY date";
		Statement st = null;
		try {
			st = conn.createStatement();
			
			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery(query);
							
			// iterate through the java resultset						
			while (rs.next())
			{
				String[] resultTemp = new String[8];
				resultTemp[0] = String.valueOf(rs.getInt("id"));
				resultTemp[1] = rs.getString("year");
				resultTemp[2] = rs.getString("country");
				resultTemp[3] = rs.getString("product");
				resultTemp[4] = rs.getString("type");
				resultTemp[5] = rs.getString("perCapita");
				resultTemp[6] = rs.getString("name");
				resultTemp[7] = rs.getString("date");
				
				savedData.add(resultTemp);
			}
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}/******/
				
		return (savedData);
	}

}
