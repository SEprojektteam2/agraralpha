package com.gwt.client;

import java.util.ArrayList;

import com.google.gwt.i18n.client.*;  
import com.google.gwt.core.client.EntryPoint;  
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;  
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.GeoMap;

import org.moxieapps.gwt.highcharts.client.*;  
import org.moxieapps.gwt.highcharts.client.labels.*;  
import org.moxieapps.gwt.highcharts.client.plotOptions.*;

public class VisualizationMap{
	ArrayList<String[]> data = null;
	private int year = 0;
	private GeoMap.Options world_chart_options=GeoMap.Options.create();

	
	public VisualizationMap(int year){
		this.year = year;
		setOptions();
	}
	
	public void setOptions(){
		world_chart_options.setDataMode(GeoMap.DataMode.REGIONS);
		world_chart_options.setHeight(600);
		world_chart_options.setWidth(1050);
		world_chart_options.setShowLegend(true);
		world_chart_options.setColors(0xDEEBF7, 0x3182BD);
		//world_chart_options.setRegion("155");
	}
	
	public GeoMap createMap(ArrayList<String[]> data){
		this.data = data;
		//getData();
		final DataTable datatable = createDataTable(year);
		final GeoMap grafico_mundo = new GeoMap();
		
		Timer timer = new Timer() {
		      public void run() {
		    	  grafico_mundo.draw(datatable, world_chart_options);
		      }
		    };
		timer.schedule(5000);
		return grafico_mundo;
	}
  
	public DataTable createDataTable(int year) {
		DataTable dataWorldChart = DataTable.create();

		dataWorldChart.addColumn(ColumnType.STRING, "Country");
		dataWorldChart.addColumn(ColumnType.NUMBER, data.get(data.size()-1)[2]);
	
		
		/*dataWorldChart.addRows(5);
		dataWorldChart.setValue(0, 0, "Germany");
		dataWorldChart.setValue(0, 1, 1000);
	
		dataWorldChart.setValue(1, 0, "Spain");
		dataWorldChart.setValue(1, 1, 1500);
		
		dataWorldChart.setValue(2, 0, "Italy");
		dataWorldChart.setValue(2, 1, 1000);
	
		dataWorldChart.setValue(3, 0, "France");
		dataWorldChart.setValue(3, 1, 600);
	
		dataWorldChart.setValue(4, 0, "Portugal");
		dataWorldChart.setValue(4, 1, 200);
		*/
		for(int i=0; i<data.size()-1; i++){
			dataWorldChart.addRows(1);
			String[] row = data.get(i);
			int dataYear = Integer.parseInt(row[0]);
			if(dataYear == year)
			{
				dataWorldChart.setValue(i, 0, row[1]);
				dataWorldChart.setValue(i, 1, Double.parseDouble(row[2]));
			}
		}
		
		return dataWorldChart;
	}
	
}
