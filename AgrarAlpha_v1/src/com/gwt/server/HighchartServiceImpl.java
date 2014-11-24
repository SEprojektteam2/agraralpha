package com.gwt.server;



import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.moxieapps.gwt.highcharts.client.Chart;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.gwt.client.HighchartService;
import com.gwt.client.VisualizationMap;

@SuppressWarnings("serial")
public class HighchartServiceImpl extends RemoteServiceServlet implements
		HighchartService {
		
		
	Connection conn;
	
	//Create connection to mysqldatabase
	private void connectToDatabase(){ 
		MySQLConnection database = new MySQLConnection();
		if(database.connect()){
			System.out.println("<html><head></head><body>Connection Started</body></html>");
		}
	    conn = database.returnConnection();
	}
		
	private int getCounter(String query){
		Statement st = null;
		int i=0;
		try {
			st = conn.createStatement();
				
			// execute the query, and get a java resultset
			ResultSet rs = null;
			rs = st.executeQuery(query);
			
			// iterate through the java resultset
				
					
			while (rs.next())
			{
				i++;
			}
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
	
	private ArrayList<String[]> readDatabase(String query, String searchingVar, String outputVar){
		ArrayList<String[]> result = new ArrayList<String[]>();
		// create the java statement
		Statement st = null;
		try {
			st = conn.createStatement();
			
			// execute the query, and get a java resultset
			ResultSet rs = null;
			rs = st.executeQuery(query);
			
			// iterate through the java resultset
			int i=0;
			
			while (rs.next())
			{
					String[] resultTemp = new String[3];
					resultTemp[0] = rs.getString("Year");
					resultTemp[1] = rs.getString(searchingVar);
					resultTemp[2] = rs.getString(outputVar);
					result.add(i, resultTemp);
					i++;
			}
			
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
		
	public ArrayList<String[]> getData(String country, String product, String type, Boolean perCapita){
		// "null" nicht angegeben => nicht Beachtung der varaible 
		//private DataTable TableDATA;
		//-> siehe prepareData() VisualizationManager
		//evt. absteigend nach jahr sortieren und ueber geben
		// Achtung Import: Import Quantity gleich fuer Export!!!
			
		ArrayList<String[]> result = new ArrayList<String[]>();
			
			
		connectToDatabase();
			
		String query="null";
		String query2="null";
		String searchingVar="null";
		String outputVar="null";
			
		int counter=0;
		// country=null when world is selected and product is given and type is given => Output: Country + Year + Value
		if(country=="null"){ 
			query = "SELECT AreaName, Year, Value FROM records WHERE ElementName = '"+type+"' AND ItemName = '"+product+"' ORDER BY Year ASC";
			query2 = "SELECT distinct AreaName FROM records WHERE ElementName = '"+type+"' AND ItemName = '"+product+"'";
			counter=getCounter(query2);
			searchingVar="AreaName";
		}
		//
		if(product=="null"){
			query = "SELECT ItemName, Year, Value FROM records WHERE ElementName = '"+type+"' AND AreaName = '"+country+"' ORDER BY Year ASC";
			query2 = "SELECT distinct ItemName FROM records WHERE ElementName = '"+type+"' AND AreaName = '"+country+"'";
			counter=getCounter(query2);
			searchingVar="ItemName";
		}
		//
		if(type=="null"){
			query = "SELECT ElementName, Year, Value FROM records WHERE ItemName = '"+product+"' AND AreaName = '"+country+"' ORDER BY Year ASC";
			query2 = "SELECT distinct ElementName FROM records WHERE ItemName = '"+product+"' AND AreaName = '"+country+"'";
			counter=getCounter(query2);
			searchingVar="ElementName";
		}
			
		result = readDatabase(query,searchingVar,outputVar);
		
		String[] resultTemp = new String[3];
		resultTemp[0] = Integer.toString(counter);
		resultTemp[1] = searchingVar;
		resultTemp[2] = "null";
		result.add(result.size(),resultTemp);
		
		return result;
	}
	
	private ArrayList<String[]> calcPerCaptia(ArrayList<String[]> resultData){
		return resultData;
	}
		

}	
	
	
	

