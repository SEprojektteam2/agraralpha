/*package com.gwt.client;

import java.sql.*;

import com.gwt.client.MySQLConnection;
//import com.google.appengine.api.utils.SystemProperty;
//import com.google.cloud.sql.jdbc.ResultSet;
//import com.mysql.jdbc.*;
//import com.google.gwt.visualization.client.visualizations.*;
import com.google.gwt.visualization.client.*;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;



import java.util.ArrayList;

public class DataManager_alt {
	Connection conn;
	
	//Create connection to mysqldatabase
	private void connectToDatabase(){ 
		MySQLConnection database = new MySQLConnection("173.194.253.240:3306","root","","agrar","agraralphav1:agrar");
	    if(database.connect()){
	    	System.out.println("<html><head></head><body>Connection Started</body></html>");
	    }
	    conn = database.returnConnection();
	}
	
	private void calculateInterpolation(){
		//evt. Klassse die die Berechnung uebernimmt
		//Achtung Zahl sind String
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
	
	public DataTable getDataTable(String country, String product, String type){
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
			query = "SELECT AreaName, Year, Value FROM agrar WHERE ElementName = '"+type+"' AND ItemName = '"+product+"' ORDER BY Year ASC";
			query2 = "SELECT distinct AreaName FROM agrar WHERE ElementName = '"+type+"' AND ItemName = '"+product+"'";
			counter=getCounter(query2);
		}
		//
		if(product=="null"){
			query = "SELECT ItemName, Year, Value FROM agrar WHERE ElementName = '"+type+"' AND AreaName = '"+country+"' ORDER BY Year ASC";
			query2 = "SELECT distinct ItemName FROM agrar WHERE ElementName = '"+type+"' AND AreaName = '"+country+"'";
			counter=getCounter(query2);
		}
		//
		if(type=="null"){
			query = "SELECT ElementName, Year, Value FROM agrar WHERE ItemName = '"+product+"' AND AreaName = '"+country+"' ORDER BY Year ASC";
			query2 = "SELECT distinct ElementName FROM agrar WHERE ItemName = '"+product+"' AND AreaName = '"+country+"'";
			counter=getCounter(query2);
		}
		
		result = readDatabase(query,searchingVar,outputVar);
		
		DataTable DATA = DataTable.create();

		DATA.addColumn(ColumnType.STRING, searchingVar);
		for(int j=2011; j>=1990;j--){      //TODO Achtung Funktion zum auffuellen
			DATA.addColumn(ColumnType.NUMBER, Integer.toString(j));
		}
			
		DATA.addRows(counter);

		for(int c = 0; c < DATA.getNumberOfRows(); c++ )
		{
			int k;
			String[] temp=null;
			for(k = 1; k <= DATA.getNumberOfColumns(); k++ )
			{
				temp=result.get(k-1);
				DATA.setCell(c, k, Double.parseDouble(temp[2]), temp[2], null);
			}
			DATA.setCell(c, 0, temp[1],temp[1] , null);
		}
		
		return DATA;
	}
	
	public ArrayList<String> getCountries(){
		//Array mit Strings als Rueckgabe
		//limit 1 bei der Abfrage (entfernt die Dupletten)
		ArrayList<String> countries = new ArrayList<String>();
		//countries.add(0, "World");
		
		connectToDatabase();
		// if you only need a few columns, specify them by name instead of using "*"
		String query = "SELECT distinct AreaName FROM agrar WHERE Domain = 'Annual population'";
		
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
	
	public void saveUser(String username, String param1, String param2){
		//coming soon
	}
	
	public void getUser(String username){
		//coming soon
	}
	
	
}
*/