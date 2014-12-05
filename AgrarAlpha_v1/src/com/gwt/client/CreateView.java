package com.gwt.client;

//package guiA.client;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.GeoMap;
import com.google.gwt.visualization.client.visualizations.Table;
import com.google.gwt.widgetideas.client.SliderBar;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

public class CreateView extends Composite{
	
	private TabPanel basePanel = new TabPanel();
	private VerticalPanel tablePanel;
	private VerticalPanel interpolationPanel;
	private VerticalPanel histogramPanel;
    
	private Label label;
	private ListBox list;
	private VerticalPanel mapPanel = new VerticalPanel();
	private ArrayList <String[]>dataArray;
	public GeoMap map;
	private String year;
	private SliderBar slider = new SliderBar(1990, 2011);
	public static final Logger log = Logger.getLogger(CreateView.class.getName());
//private VisualizationLineChart vLineChart;

	/* This class present the view the user has after he clicked the create button on mainView. it contains the graphics the user wants to see
	 */
	public CreateView(boolean interpolation, ArrayList<String[]> Data, final String year){
		initWidget(this.basePanel);
		this.dataArray = Data;
		this.year = year;

		VisualizationLineChart vLineChart = new VisualizationLineChart();
		VisualizationTable vTable = new VisualizationTable(Data);
		list=new ListBox();
		label= new Label("Placeholder");
		/*
		 for(int i=min; i<=max; i++){
		 list.addItem(String.valueOf(i));
		 }
		 */
		
		tablePanel = new VerticalPanel();
		interpolationPanel = new VerticalPanel();
		histogramPanel = new VerticalPanel();
		histogramPanel.add(new SourceView());

		mapPanel = new VerticalPanel();


	
		/*Runnable onLoadCallbackTable = new Runnable(){
			public void run(){
				VisMan = visMan;
				tablePanel.add(VisMan.graphs.get(0));
			}
		};

		VisualizationUtils.loadVisualizationApi(onLoadCallbackTable, Table.PACKAGE);
		  
		
		*/
		
		  slider.setStepSize(1);
		  slider.setCurrentValue(Integer.parseInt(year));
		  slider.setNumTicks(21);
		  slider.setNumLabels(21);
		  slider.setWidth("100%");
		  
		 /* slider.sinkEvents( Event.MOUSEEVENTS ); 
		  slider.sinkEvents( Event.KEYEVENTS ); 
		  slider.sinkEvents( Event.ONMOUSEWHEEL ); 
		  slider.sinkEvents( Event.FOCUSEVENTS );
		  */
		  slider.addMouseUpHandler(new MouseUpHandler(){
				@Override
				public void onMouseUp(MouseUpEvent event) {
					// TODO Auto-generated method stub
					createMapFromSlider();
					slider.redraw();
				}
	          });
//		  slider.addChangeListener(new ChangeListener() {
//				@Override
//				public void onChange(Widget sender) {
//					// TODO Auto-generated method stub
//					createMapFromSlider();
//					slider.redraw();
//				}
//	          });
		 
		  
		  
		  //slider.addValueChangeHandler();
		  
		
		/*if(interpolation==true){*/
		    tablePanel.add(new SourceView());
			tablePanel.add(vTable.create());
			interpolationPanel.add(vLineChart.createChart(Data));
		/*}
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
		
		
		/**mapPanel.add(new SourceView()); // adding a verticalPanel with all source to the mapPanel
		mapPanel.add(slider.asWidget());
		createMap(Integer.parseInt(year));	**/	
		//mapPanel.add(getMap());
		basePanel.add(tablePanel,"Table");
		basePanel.add(interpolationPanel,"Interpolation");
		basePanel.add(histogramPanel,"Histogram");
		basePanel.add(mapPanel,"Map");

		basePanel.selectTab(0); // first tab of the tabPanel will be open
		
	
	}
	public void setMap(GeoMap map){
		this.map = map;
	}
	public GeoMap getMap(){
		return this.map;
	}
	
	public void createMapFromSlider(){
		mapPanel.remove(2);
		createMap((int)slider.getCurrentValue());
	}
	public void createMap(final int year){
		final DataTable table = DataTable.create();
		table.addColumn(ColumnType.STRING, "Country");
		table.addColumn(ColumnType.NUMBER, dataArray.get(dataArray.size()-1)[2] + " in " + year);
		int y = 0;
		for(int i=0; i<dataArray.size()-1; i++)
		{
			if(dataArray.get(i)[0].equals(String.valueOf(year)))
			{
					table.addRows(1);
					table.setValue(y, 0,dataArray.get(i)[1]);
					table.setValue(y, 1, dataArray.get(i)[2]);
					y++;
			}
		}
		Runnable onLoadCallbackMap = new Runnable(){
			public void run(){
				VisualizationMap map = new VisualizationMap(year);
				GeoMap newMap = map.createMap(table);
				    	  mapPanel.add(newMap.asWidget());
			}
		};

		VisualizationUtils.loadVisualizationApi(onLoadCallbackMap, GeoMap.PACKAGE); 
	}
}
