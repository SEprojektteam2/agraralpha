package com.gwt.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ImportHandlerThread implements Runnable {
	  private String[][] output;
	  public static final Logger log = Logger.getLogger(ImportHandlerThread.class.getName());
	 
	  //constructor takes String[][] that contains table with contents
	  public ImportHandlerThread(String[][] output) {
	    this.output = output;
	  }
	  
	  public void run() {   
		    PreparedStatement stmt = null;
			try {
				
				//Initialization of MySQL Connection
				String statement = "INSERT INTO records (domainCode, domain, areaCode, areaName, elementCode, elementName, itemCode, itemName, year, unit, value, flag, flagD) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? )";
				MySQLConnection database = new MySQLConnection("173.194.253.240:3306","root","","agrar","agraralphav1:agrar");
			    database.connect();
				
				Connection conn = database.returnConnection();
				stmt = conn.prepareStatement(statement);
				
				//Add each array-row to mysql batch
			    for(int y = 0; y<output.length; y++){
			    	try {
			    		stmt.setString(1,output[y][0]);
						stmt.setString(2,output[y][1]);
						stmt.setString(3,output[y][2]);
						stmt.setString(4,output[y][3]);
						stmt.setString(5,output[y][4]);
						stmt.setString(6,output[y][5]);
						stmt.setString(7,output[y][6]);
						stmt.setString(8,output[y][7]);
						stmt.setString(9,output[y][8]);
						stmt.setString(10,output[y][9]);
						stmt.setString(11,output[y][10]);
						stmt.setString(12,output[y][11]);
						stmt.setString(13,output[y][12]);
						stmt.addBatch();
					 } catch (SQLException e) {
						log.warning(e.toString());
					}
			         
				}
			    try {
			    	//execute mysql insertion
			    	stmt.executeBatch();
			    	
			    	//delete header row with Row-Descriptions
			    	String query = "DELETE FROM records WHERE domainCode = 'Domain Code' AND domain = 'Domain' AND areaCode = 'AreaCode' AND areaName = 'AreaName' AND elementCode = 'ElementCode' AND elementName = 'ElementName' AND itemCode = 'ItemCode' AND itemName = 'ItemName' AND year = 'Year' AND unit = 'Unit' AND value = 'Value' AND flag = 'Flag' AND flagD = 'FlagD'";
			    	PreparedStatement preparedStmt = conn.prepareStatement(query);
			    	preparedStmt.execute();
			    	
				} catch (SQLException e) {
					log.warning(e.toString());
				}
				
			//close mysql connection    
			conn.close();	
				
			} catch (SQLException e1) {
				log.warning(e1.toString());
			}
		}
	}
