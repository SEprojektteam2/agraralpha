package com.gwt.client;

//package guiA.client;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.GeoMap;
import com.google.gwt.visualization.client.visualizations.Table;

public class CreateView extends Composite{
	
	private TabPanel basePanel = new TabPanel();
	private VerticalPanel tablePanel;
	private VerticalPanel interpolationPanel;
	private VerticalPanel stockChartPanel;
    
	private Label label;
	private ListBox list;
	private VerticalPanel mapPanel = new VerticalPanel();
	private SourceView source;
	private ArrayList <String[]>dataArray;

//private VisualizationLineChart vLineChart;

	/* This class present the view the user has after he clicked the create button on mainView. it contains the graphics the user wants to see
	 */
	public CreateView(boolean interpolation, final ArrayList<String[]> Data){
		initWidget(this.basePanel);

		

		VisualizationLineChart vLineChart = new VisualizationLineChart();
		VisualizationTable vTable = new VisualizationTable(Data);
		list=new ListBox();
		label= new Label("Placeholder");
		/*
		 for(int i=min; i<=max; i++){
		 list.addItem(String.valueOf(i));
		 }
		 */
		source= new SourceView();
		source.addSource("Source: FAO. 2014. FAOSTAT. data.fao.org. (Accessed 1.9.2014)"); //add a source
		
		tablePanel = new VerticalPanel();
		interpolationPanel = new VerticalPanel();
		stockChartPanel = new VerticalPanel();

		mapPanel = new VerticalPanel();
        
	/*
		Runnable onLoadCallbackTable = new Runnable(){
			public void run(){
				VisMan = visMan;
				tablePanel.add(VisMan.graphs.get(0));
			}
		};

		VisualizationUtils.loadVisualizationApi(onLoadCallbackTable, Table.PACKAGE);
		  
		
		*/
		tablePanel.add(source);
		
		
		
	

		
		if(interpolation==true){
			tablePanel.add(vTable.create());
			interpolationPanel.add(vLineChart.createChart(Data));
		}
		if(interpolation==false){
			tablePanel.add(vTable.create());
			//interpolationPanel.add(vMap.createChart());
		}
		//graphPanel.add(source); // adding a verticalPanel with all source to the mapPanel
		/*
		Runnable onLoadCallbackMap = new Runnable(){
			public void run(){
				mapPanel.add(VisMan.graphs.get(1));
			}
		};

		VisualizationUtils.loadVisualizationApi(onLoadCallbackMap, GeoMap.PACKAGE);
		  */
		//mapPanel.add(message.asWidget());
		
		//mapPanel.add(vMap.createChart());
		//vMap.createChart(mapPanel);
		
		ArrayList<String[]> arrayformap;
		arrayformap = Data;
		for(int i=0; i<arrayformap.size()-1; i++)
		{
			if(arrayformap.get(i)[0] != "2010")
			{
				arrayformap.remove(i);
			}
		}
		
		final ArrayList<String[]> newArray = arrayformap;
		
		Runnable onLoadCallbackMap = new Runnable(){
			public void run(){
				VisualizationMap map = new VisualizationMap(2010);
				GeoMap newMap = map.createMap(newArray);
				
				mapPanel.add(newMap);
			}
		};

		VisualizationUtils.loadVisualizationApi(onLoadCallbackMap, GeoMap.PACKAGE); 
		mapPanel.add(source); // adding a verticalPanel with all source to the mapPanel
		
		basePanel.add(tablePanel,"Table");
		basePanel.add(interpolationPanel,"Interpolation");
		basePanel.add(stockChartPanel,"Stock Chart");
		basePanel.add(mapPanel,"Map");

		basePanel.selectTab(0); // first tab of the tabPanel will be open
		
	
	}
	
}
