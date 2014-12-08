package com.gwt.server;



import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Logger;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.gwt.client.HighchartService;

@SuppressWarnings("serial")
public class HighchartServiceImpl extends RemoteServiceServlet implements
		HighchartService {
		
	public static final Logger log = Logger.getLogger(HighchartServiceImpl.class.getName());	
	Connection conn;
	
	//Create connection to mysqldatabase
	private void connectToDatabase(){ 
		MySQLConnection database = new MySQLConnection();
		if(database.connect()){
			System.out.println("<html><head></head><body>Connection Started</body></html>");
			log.warning("MySQL Connection Started!");
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
	
	private ArrayList<String[]> readDatabase(String query, String searchingVar, String outputVar, Boolean perCapita){
		ArrayList<String[]> result = new ArrayList<String[]>();
		// create the java statement
		Statement st = null;
		log.warning("now reading database!");
		try {
			st = conn.createStatement();
			
			// execute the query, and get a java resultset
			ResultSet rs = null;
			rs = st.executeQuery(query);
			int noOfEntries = rs.getRow();
			rs.beforeFirst();
			log.warning("Entries: "+String.valueOf(noOfEntries) +" Query: "+ query);
			// iterate through the java resultset
			int i=1990;
			//String controll = rs.getString(searchingVar);
			String controll = "null";
			ArrayList<String[]> population = getPopulation(); 
			
			while (rs.next())
			{		
					if(controll.equals("null")){
						controll = rs.getString(searchingVar);
					}
					String[] resultTemp = new String[3];
					resultTemp[0] = rs.getString("Year");
					resultTemp[1] = rs.getString(searchingVar);
					resultTemp[2] = rs.getString(outputVar);
					if(rs.getString("Unit").equals("tonnes"))
						resultTemp[2] = String.valueOf(Double.parseDouble(resultTemp[2]) * 1000);
					else
						resultTemp[2] = String.valueOf(Double.parseDouble(resultTemp[2]) * Double.parseDouble(rs.getString("Unit")));
					
					// If we want to have the values per capita
					if(perCapita){
						log.warning("perCapita");
						// We can only do this if the values don't equal an empty String
						if(!resultTemp[2].equals("")){
							log.warning("now calculating");
							//int valueTotal = Integer.parseInt(resultTemp[2]);
							for(int r=0; r<population.size();r++){
								//areacode year value unit
								if(population.get(r)[0].equals(rs.getString("AreaCode")) && population.get(r)[1].equals(resultTemp[0]))
									resultTemp[2] = String.valueOf(Double.parseDouble(resultTemp[2]) / Double.parseDouble(population.get(r)[2]) * Double.parseDouble(population.get(r)[3]));
							}
							
						}
					}
					
					
					
					
					/**result.add(resultTemp);**/
					//i++;
					if(resultTemp[0].equals(String.valueOf(i))){
						result.add(resultTemp);
						if(i==2011){
							i=1990;
							controll = "null";
						}
						else{
							i++;
						}
					}
					else{
						if(!resultTemp[1].equals(controll)){
							for(int j=i;j<=2011;j++){
								String[] resultCorrect = new String[3];
								resultCorrect[0]=String.valueOf(j);
								resultCorrect[1]=controll;
								resultCorrect[2]="-";
								result.add(resultCorrect);	
							}
							i=1990;
							if(resultTemp[0].equals("1990")){
								String[] resultCorrect = new String[3];
								resultCorrect[0]=String.valueOf(i);
								resultCorrect[1]=resultTemp[1];
								resultCorrect[2]="-";	
							}
							else{
								for(int j=i;j<Integer.parseInt(resultTemp[0]);j++){
									String[] resultCorrect = new String[3];
									resultCorrect[0]=String.valueOf(j);
									resultCorrect[1]=resultTemp[1];
									resultCorrect[2]="-";
									result.add(resultCorrect);
									i++;
								}
							}
							result.add(resultTemp);
							controll=resultTemp[1];
							i++;
								
						}
						else{
							for(int j=i;j<Integer.parseInt(resultTemp[0]);j++){
								String[] resultCorrect = new String[3];
								resultCorrect[0]=String.valueOf(j);
								resultCorrect[1]=resultTemp[1];
								resultCorrect[2]="-";
								result.add(resultCorrect);
								i++;
							}
							result.add(resultTemp);
							if(i==2011){
								i=1990;
								controll = "null";
							}
							else{
								i++;
							}
						}
					}
					
					
					log.warning("added[" + resultTemp[0] + "," + resultTemp[1] + "," + resultTemp[2] + "]" );
			}
			
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	//									Namibia			Tea			Export Quantity			false	
	public ArrayList<String[]> getData(String country, String product, String type, Boolean perCapita){
		// "null" nicht angegeben => nicht Beachtung der varaible 
		//private DataTable TableDATA;
		//-> siehe prepareData() VisualizationManager
		//evt. absteigend nach jahr sortieren und ueber geben
		// Achtung Import: Import Quantity gleich fuer Export!!!
			
		ArrayList<String[]> result = new ArrayList<String[]>();
			
		log.warning("start");
		connectToDatabase();
			
		String query="null";
		String query2="null";
		String searchingVar="null";
		String outputVar="Value";
		String mapInfo="null";
			
		int counter=0;
		// country=null when world is selected and product is given and type is given => Output: Country + Year + Value
		if(country.equals("Global")){ 
			query = "SELECT AreaName, Year, Value, AreaCode, Unit FROM records WHERE ElementName = '"+type+"' AND ItemName = '"+product+"' ORDER BY AreaName ASC, Year ASC";
			query2 = "SELECT distinct AreaCode, AreaName FROM records WHERE ElementName = '"+type+"' AND ItemName = '"+product+"'";
			counter=getCounter(query2);
			searchingVar="AreaName";
			mapInfo=type+" of "+product;
			if(perCapita)
				mapInfo.concat(" (per Capita)");
		}
		//
		else if(product.equals("null")){
			query = "SELECT ItemName, Year, Value, AreaCode, Unit FROM records WHERE ElementName = '"+type+"' AND AreaName = '"+country+"' ORDER BY ItemName ASC, Year ASC";
			query2 = "SELECT distinct ItemCode, ItemName FROM records WHERE ElementName = '"+type+"' AND AreaName = '"+country+"'";
			counter=getCounter(query2);
			searchingVar="ItemName";
			mapInfo=type+" in "+country;
			if(perCapita)
				mapInfo.concat(" (per Capita)");
		}
		//
		else if(type.equals("null")){
			query = "SELECT ElementName, Year, Value, AreaCode, Unit FROM records WHERE ItemName = '"+product+"' AND AreaName = '"+country+"' ORDER BY ElementName ASC, Year ASC";
			query2 = "SELECT distinct ElementCode, ElementName FROM records WHERE ItemName = '"+product+"' AND AreaName = '"+country+"'";
			counter=getCounter(query2);
			searchingVar="ElementName";
			mapInfo=product+" in "+country;
			if(perCapita)
				mapInfo.concat(" (per Capita)");
		}
		else{
			query = "SELECT AreaName, Year, Value, AreaCode, Unit FROM records WHERE ItemName = '"+product+"' AND AreaName = '"+country+"' AND ElementName = '"+type+"' ORDER BY Year ASC";
			query2 = "SELECT distinct AreaCode, AreaName FROM records WHERE ItemName = '"+product+"' AND AreaName = '"+country+"' AND ElementName = '"+type+"'";
			counter=getCounter(query2);
			searchingVar="AreaName";
			/** HIER FEHLT NOCH MAP INFO! **/
		}
			
		result = readDatabase(query,searchingVar,outputVar,perCapita);
		
		
		/*String[] resultTemp = new String[3];
		resultTemp[0] = Integer.toString(counter);
		resultTemp[1] = searchingVar;
		resultTemp[2] = "null";
		result.add(result.size(),resultTemp);
		
		String[] sArray= {"1990","Schweiz","3.0"};
		String[] sArray1= {"1991","Schweiz","10.0"};
		String[] sArray2= {"1992","Schweiz","9.0"};
		String[] sArray3= {"1993","Schweiz","11.0"};
		String[] sArray4= {"1994","Schweiz","15.0"};
		String[] sArray5= {"1995","Schweiz","12.0"};
		String[] sArray6= {"1996","Schweiz","14.0"};
		String[] sArray7= {"1997","Schweiz","16.0"};
		String[] sArray8= {"1998","Schweiz","10.0"};
		String[] sArray9= {"1999","Schweiz","14.0"};
		String[] sArray10= {"2000","Schweiz","19.0"};
		String[] sArray11= {"2001","Schweiz","20.0"};
		String[] sArray12= {"2002","Schweiz","21.0"};
		String[] sArray13= {"2003","Schweiz","25.0"};
		String[] sArray14= {"2004","Schweiz","14.0"};
		String[] sArray15= {"2005","Schweiz","16.0"};
		String[] sArray16= {"2006","Schweiz","19.0"};
		String[] sArray17= {"2007","Schweiz","18.0"};
		String[] sArray18= {"2008","Schweiz","20.0"};
		String[] sArray19= {"2009","Schweiz","21.0"};
		String[] sArray20= {"2010","Schweiz","22.0"};
		String[] sArray21= {"2011","Schweiz","20.0"};
		
		result.add(sArray);
		result.add(sArray1);
		result.add(sArray2);
		result.add(sArray3);
		result.add(sArray4);
		result.add(sArray5);
		result.add(sArray6);
		result.add(sArray7);
		result.add(sArray8);
		result.add(sArray9);
		result.add(sArray10);
		result.add(sArray11);
		result.add(sArray12);
		result.add(sArray13);
		result.add(sArray14);
		result.add(sArray15);
		result.add(sArray16);
		result.add(sArray17);
		result.add(sArray18);
		result.add(sArray19);
		result.add(sArray20);
		result.add(sArray21);*/
	
		
		//adding informations

		String[] informationRow= {Integer.toString(counter),searchingVar,mapInfo};
		result.add(informationRow);
		
		return result;
	}
	
	private ArrayList<String[]> calcPerCapita(ArrayList<String[]> resultData){
		return resultData;
	}
	
	private ArrayList<String[]> getPopulation() {
		ArrayList<String[]> population = new ArrayList<String[]>();
		String query = "SELECT AreaCode, Value, Year, Unit FROM records WHERE ElementName = 'Total Population - Both sexes' ORDER BY AreaName ASC, Year ASC";

		Statement st;
		try {
			st = conn.createStatement();
			ResultSet populationSet = st.executeQuery(query);

			while (populationSet.next()) {
				String[] resultTemp = new String[4];
				resultTemp[0] = populationSet.getString("AreaCode");
				resultTemp[1] = populationSet.getString("Year");
				resultTemp[2] = populationSet.getString("Value");
				resultTemp[3] = populationSet.getString("Unit");
				population.add(resultTemp);
				log.warning("Got population");
			}

		} catch (SQLException e) {
			log.warning("Error getting population values");
		}

		return population;
	}

}	
