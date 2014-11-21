package com.gwt.server;

import java.sql.*;
import java.util.ArrayList;

import com.gwt.server.MySQLConnection;
//import com.google.appengine.api.utils.SystemProperty;
import com.google.gwt.visualization.client.*;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.gwt.client.DataManager2;
import com.gwt.client.HighchartService;

@SuppressWarnings("serial")
public class HighchartServiceImpl extends RemoteServiceServlet implements
		HighchartService {
		
		
	public ArrayList<String[]> getCharts(String country, String product, String type, Boolean perCapita){
			
		ArrayList<String[]> result = new ArrayList<String[]>();
		
		DataManager data = new DataManager();
		result=data.getData(country, product, type);
		
		return result;
	}
		

}	
	

