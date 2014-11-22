package com.gwt.server;



import java.util.ArrayList;

import org.moxieapps.gwt.highcharts.client.Chart;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.gwt.client.HighchartService;

@SuppressWarnings("serial")
public class HighchartServiceImpl extends RemoteServiceServlet implements
		HighchartService {
		
		
	public ArrayList<Chart[]> getCharts(String country, String product, String type, Boolean perCapita, Boolean interpolation){
			
		ArrayList<Chart[]> result = new ArrayList<Chart[]>();
		DataManager data = new DataManager();
		ArrayList<String[]> resultData = new ArrayList<String[]>();
		resultData=data.getData(country, product, type);
		
		return result;
	}
		

}	
	

