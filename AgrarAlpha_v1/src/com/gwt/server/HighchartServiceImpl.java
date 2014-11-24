package com.gwt.server;



import java.util.ArrayList;

import org.moxieapps.gwt.highcharts.client.Chart;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.gwt.client.HighchartService;

@SuppressWarnings("serial")
public class HighchartServiceImpl extends RemoteServiceServlet implements
		HighchartService {
		
		
	public ArrayList<Chart> getCharts(String country, String product, String type, String year, Boolean perCapita, Boolean interpolation){
			
		ArrayList<Chart> result = new ArrayList<Chart>();
		DataManager data = new DataManager();
		ArrayList<String[]> resultData = new ArrayList<String[]>();
		resultData=data.getData(country, product, type);
		/*if(interpolation==true){
			VisualizationLineChart LineChart = new VisualizationLineChart();
			result.add(0,LineChart.createChart(resultData));
			//vielleicht noch Tabelle und Histogramm einfügen
		}*/
		VisualizationMap map = new VisualizationMap();
		RootPanel.get().add(map.createChart());
		return result;
	}
		

}	
	
	
	

