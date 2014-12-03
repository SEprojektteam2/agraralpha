package com.gwt.client;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.appengine.tools.util.Logging;
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
import com.google.gwt.logging.client.*;
import com.gwt.server.MySQLConnection;

import org.moxieapps.gwt.highcharts.client.*;  
import org.moxieapps.gwt.highcharts.client.labels.*;  
import org.moxieapps.gwt.highcharts.client.plotOptions.*;

public class VisualizationMap{
	ArrayList<String[]> data = null;
	private int year = 0;
	private GeoMap.Options world_chart_options=GeoMap.Options.create();
	public static final Logger log = Logger.getLogger(VisualizationMap.class.getName());
	Logger logger = Logger.getLogger("NameOfYourLogger");

	
	public VisualizationMap(int year){
		this.year = year;
		setOptions();
		log.warning("map created!");
		logger.warning("map created!");
	}
	
	public void setOptions(){
		world_chart_options.setDataMode(GeoMap.DataMode.REGIONS);
		world_chart_options.setHeight(600);
		world_chart_options.setWidth(1050);
		world_chart_options.setShowLegend(true);
		world_chart_options.setColors(0xDEEBF7, 0x000080);
		//world_chart_options.setRegion("155");
	}
	
	public GeoMap createMap(DataTable table){

		//getData();
		//final DataTable datatable = createDataTable(year);
		final DataTable datatable = table;
		final GeoMap grafico_mundo = new GeoMap();
		
		
		    	  grafico_mundo.draw(datatable, world_chart_options);
		    	
		return grafico_mundo;
	}
  
	
}
