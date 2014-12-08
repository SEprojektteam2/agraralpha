package com.gwt.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Logger;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.gwt.client.HighchartService;

/**
 * @author William Martini & Fabian Weber
 *
 */
@SuppressWarnings("serial")
public class HighchartServiceImpl extends RemoteServiceServlet implements
		HighchartService {

	public static final Logger log = Logger
			.getLogger(HighchartServiceImpl.class.getName());
	Connection conn;

	// Create connection to mysqldatabase
	private void connectToDatabase() {
		MySQLConnection database = new MySQLConnection();
		if (database.connect()) {
			System.out
					.println("<html><head></head><body>Connection Started</body></html>");
			log.warning("MySQL Connection Started!");
		}
		conn = database.returnConnection();
	}

	/**
	 * Return the numbers of different countries, types or products
	 * 
	 * @param query
	 * @return int
	 */
	private int getCounter(String query) {
		Statement st = null;
		int i = 0;
		try {
			st = conn.createStatement();

			// execute the query, and get a java resultset
			ResultSet rs = null;
			rs = st.executeQuery(query);

			// iterate through the java resultset
			while (rs.next()) {
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.warning("Error getCounter in HighchartServiceImpl");
		}
		return i;
	}

	/**
	 * Read the database and create an arraylist with the results
	 * 
	 * @param query
	 * @param searchingVar
	 * @param outputVar
	 * @param perCapita
	 * @return ArrayList<String[]>
	 */
	private ArrayList<String[]> readDatabase(String query, String searchingVar, String outputVar, Boolean perCapita) {
		
		ArrayList<String[]> result = new ArrayList<String[]>();
		
		// create the java statement
		Statement st = null;
		try {
			st = conn.createStatement();

			// execute the query, and get a java resultset
			ResultSet rs = null;
			rs = st.executeQuery(query);
			
			int i = 1990;
			String controll = "null";
			ArrayList<String[]> population = getPopulation();
			
			// iterate through the java resultset
			while (rs.next()) {
				//the controll string
				if (controll.equals("null")) {
					controll = rs.getString(searchingVar);
				}

				String[] resultTemp = new String[3];
				resultTemp[0] = rs.getString("Year");
				resultTemp[1] = rs.getString(searchingVar);
				resultTemp[2] = rs.getString(outputVar);
				if (rs.getString("Unit").equals("tonnes"))
					resultTemp[2] = String.valueOf(Double.parseDouble(resultTemp[2]) * 1000);
				else
					resultTemp[2] = String.valueOf(Double.parseDouble(resultTemp[2])* Double.parseDouble(rs.getString("Unit")));

				// If we want to have the values per capita
				if (perCapita) {
					log.warning("perCapita");
					// We can only do this if the values don't equal an empty
					// String
					if (!resultTemp[2].equals("")) {
						log.warning("now calculating");
						// int valueTotal = Integer.parseInt(resultTemp[2]);
						for (int r = 0; r < population.size(); r++) {
							// areacode year value unit
							if (population.get(r)[0].equals(rs
									.getString("AreaCode"))
									&& population.get(r)[1]
											.equals(resultTemp[0]))
								resultTemp[2] = String.valueOf(Double.parseDouble(resultTemp[2]) / (Double.parseDouble(population.get(r)[2]) * Double.parseDouble(population.get(r)[3])));
						}
					}
				}
				
				//Fill the arraylist with "-" where no value is given
				if (resultTemp[0].equals(String.valueOf(i))) {
					result.add(resultTemp);
					if (i == 2011) {
						i = 1990;
						controll = "null";
					} else {
						i++;
					}
				} else {
					if (!resultTemp[1].equals(controll)) {
						for (int j = i; j <= 2011; j++) {
							String[] resultCorrect = new String[3];
							resultCorrect[0] = String.valueOf(j);
							resultCorrect[1] = controll;
							resultCorrect[2] = "-";
							result.add(resultCorrect);
						}
						i = 1990;
						if (resultTemp[0].equals("1990")) {
							String[] resultCorrect = new String[3];
							resultCorrect[0] = String.valueOf(i);
							resultCorrect[1] = resultTemp[1];
							resultCorrect[2] = "-";
						} else {
							for (int j = i; j < Integer.parseInt(resultTemp[0]); j++) {
								String[] resultCorrect = new String[3];
								resultCorrect[0] = String.valueOf(j);
								resultCorrect[1] = resultTemp[1];
								resultCorrect[2] = "-";
								result.add(resultCorrect);
								i++;
							}
						}
						result.add(resultTemp);
						controll = resultTemp[1];
						i++;

					} else {
						for (int j = i; j < Integer.parseInt(resultTemp[0]); j++) {
							String[] resultCorrect = new String[3];
							resultCorrect[0] = String.valueOf(j);
							resultCorrect[1] = resultTemp[1];
							resultCorrect[2] = "-";
							result.add(resultCorrect);
							i++;
						}
						result.add(resultTemp);
						if (i == 2011) {
							i = 1990;
							controll = "null";
						} else {
							i++;
						}
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			log.warning("Error readDatabase in HighchartServiceImpl");
		}
		return result;
	}

	/*
	 * Prepare the information to read the database and return an arraylist
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.gwt.client.HighchartService#getData(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.Boolean)
	 */
	public ArrayList<String[]> getData(String country, String product, String type, Boolean perCapita) {
		
		ArrayList<String[]> result = new ArrayList<String[]>();

		connectToDatabase();

		String query = "null";
		String query2 = "null";
		String searchingVar = "null";
		String outputVar = "Value";
		String mapInfo = "null";
		int counter = 0;
		
		//create the different mysql-query
		//if country=Global then all countries are selected and one product is given and one type is given
		if (country.equals("Global")) {
			query = "SELECT AreaName, Year, Value, AreaCode, Unit FROM records WHERE ElementName = '"+ type+ "' AND ItemName = '"+ product+ "' ORDER BY AreaName ASC, Year ASC";
			query2 = "SELECT distinct AreaCode, AreaName FROM records WHERE ElementName = '" + type + "' AND ItemName = '" + product + "'";
			//counter is the number of different countries, types or products 
			counter = getCounter(query2);
			searchingVar = "AreaName";
			//mapInfo is for the titles of the charts
			mapInfo = type + " of " + product;
			if (perCapita)
				mapInfo = mapInfo.concat(" (per Capita)");
		}
		
		//if product=null then all products are selected and one country is given and one type is given
		else if (product.equals("null")) {
			query = "SELECT ItemName, Year, Value, AreaCode, Unit FROM records WHERE ElementName = '"+ type+ "' AND AreaName = '"+ country+ "' ORDER BY ItemName ASC, Year ASC";
			query2 = "SELECT distinct ItemCode, ItemName FROM records WHERE ElementName = '"+ type + "' AND AreaName = '" + country + "'";
			counter = getCounter(query2);
			searchingVar = "ItemName";
			mapInfo = type + " in " + country;
			if (perCapita)
				mapInfo = mapInfo.concat(" (per Capita)");
		}
		
		//if type=null then all products are selected and one country is given and one product is given
		else if (type.equals("null")) {
			query = "SELECT ElementName, Year, Value, AreaCode, Unit FROM records WHERE ItemName = '"+ product+ "' AND AreaName = '"+ country+ "' ORDER BY ElementName ASC, Year ASC";
			query2 = "SELECT distinct ElementCode, ElementName FROM records WHERE ItemName = '"+ product + "' AND AreaName = '" + country + "'";
			counter = getCounter(query2);
			searchingVar = "ElementName";
			mapInfo = product + " in " + country;
			if (perCapita)
				mapInfo = mapInfo.concat(" (per Capita)");
		}
		
		//getting the informations out of the database
		result = readDatabase(query, searchingVar, outputVar, perCapita);
		//Adding a row with information at the end of the arraylist 
		String[] informationRow = { Integer.toString(counter), searchingVar,mapInfo };
		result.add(informationRow);

		return result;
	}

	/**
	 * @return ArrayList<String[]>
	 */
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
