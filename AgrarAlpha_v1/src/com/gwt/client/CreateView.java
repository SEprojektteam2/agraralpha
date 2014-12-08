package com.gwt.client;

//package guiA.client;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.moxieapps.gwt.highcharts.client.Chart;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.GeoMap;
import com.google.gwt.widgetideas.client.SliderBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;

public class CreateView extends Composite{
	
	private TabPanel basePanel = new TabPanel();
	private VerticalPanel tablePanel;
	private VerticalPanel interpolationPanel;
	private VerticalPanel histogramPanel;
    private VisualizationRanking vRanking;
	private VerticalPanel rankingPanel;
	private Label label;
	private ListBox interpol;
	private VerticalPanel mapPanel = new VerticalPanel();
	private ArrayList <String[]>dataArray;
	public GeoMap map;
	private String year;
	private SliderBar slider = new SliderBar(1990, 2011);
	private SliderBar sliderHisto = new SliderBar(1990, 2011);
	public static final Logger log = Logger.getLogger(CreateView.class.getName());
	final VisualizationLineChart vLineChart = new VisualizationLineChart();
	VisualizationBarChart vBarChart;

	/* This class present the view the user has after he clicked the create button on mainView. it contains the graphics the user wants to see
	 */

	public CreateView(boolean interpolation, final ArrayList<String[]> Data, final String year){
		initWidget(this.basePanel);
		this.dataArray = Data;
		this.year = year;
		
			// Set up slider
		  slider.setStepSize(1);
		  slider.setCurrentValue(Integer.parseInt(year));
		  slider.setNumTicks(21);
		  slider.setNumLabels(21);
		  slider.setWidth("100%");
		  slider.addMouseUpHandler(new MouseUpHandler(){
				@Override
				public void onMouseUp(MouseUpEvent event) {
					// TODO Auto-generated method stub
					createMapFromSlider();
					slider.redraw();
				}
	          });

		
		VisualizationTable vTable = new VisualizationTable(Data);
		
		rankingPanel = new VerticalPanel();
		rankingPanel.add(new SourceView());
		rankingPanel.add(slider.asWidget());
        vRanking= new VisualizationRanking(dataArray, year);
        HTML ranking=vRanking.create();
		rankingPanel.add(ranking);	
        rankingPanel.setCellHorizontalAlignment(ranking,HasHorizontalAlignment.ALIGN_CENTER);
		
        tablePanel = new VerticalPanel();
		interpolationPanel = new VerticalPanel();
		histogramPanel = new VerticalPanel();
		histogramPanel.add(new SourceView());

		mapPanel = new VerticalPanel();

		tablePanel.add(new SourceView());
		tablePanel.add(vTable.create());

		addLineChart();


		/**createBtn = new Button("Create");
		createBtn.addClickHandler(new createClickHandler());
		createBtn.addStyleName("beautifulbutton2");*/
			
		sliderHisto.setStepSize(1);
		sliderHisto.setCurrentValue(Integer.parseInt(year));
		sliderHisto.setNumTicks(21);
		sliderHisto.setNumLabels(21);
		sliderHisto.setWidth("100%");
		sliderHisto.addMouseUpHandler(new MouseUpHandler(){
			@Override
			public void onMouseUp(MouseUpEvent event) {
				createBarChartFromSlider();
				sliderHisto.redraw();
			}
		});	
			
		
		
		
		mapPanel.add(new SourceView()); // adding a verticalPanel with all source to the mapPanel
		mapPanel.add(slider.asWidget());
		histogramPanel.add(sliderHisto.asWidget());
		addBarChart();	
		createMap(Integer.parseInt(year));	
		//mapPanel.add(getMap());
		basePanel.add(tablePanel,"Table");
		basePanel.add(interpolationPanel,"Interpolation");
		basePanel.add(histogramPanel,"Histogram");
		basePanel.add(mapPanel,"Map");
		basePanel.add(rankingPanel,"Ranking");

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
		Double value = 0.0;
		for(int i=0; i<dataArray.size()-1; i++)
		{
			if(dataArray.get(i)[0].equals(String.valueOf(year)))
			{		if(dataArray.get(i)[2].equals("-"))
						value = 0.0;
					else
						value= Double.parseDouble(dataArray.get(i)[2]);
					table.addRows(1);
					table.setValue(y, 0,dataArray.get(i)[1]);
					table.setValue(y, 1, value);
					
					if(dataArray.get(i)[1].equals("United States of America")){
						table.setValue(y, 0, "United States");
						table.setValue(y, 1, value);
						log.warning("US");
					}
					else if(dataArray.get(i)[1].equals("Russian Federation")){
						table.setValue(y, 0, "Russia");
						table.setValue(y, 1, value);
					}
					else if(dataArray.get(i)[1].equals("Iran (Islamic Republic of)")){
						table.setValue(y, 0, "Iran");
						table.setValue(y, 1, value);
					}
					
					else if(dataArray.get(i)[1].equals("Venezuela (Bolivarian Republic of)")){
						table.setValue(y, 0, "Venezuela");
						table.setValue(y, 1, value);
					}
					else if(dataArray.get(i)[1].equalsIgnoreCase("Bolivia (Plurinational State of)")){
						table.setValue(y, 0, "Bolivia");
						table.setValue(y, 1, value);
					}
					
					else if(dataArray.get(i)[1].equalsIgnoreCase("United Republic of Tanzania")){
						table.setValue(y, 0, "Tanzania");
						table.setValue(y, 1, value);
					}
					else if(dataArray.get(i)[1].equalsIgnoreCase("Congo")){
						table.setValue(y, 0, "CG");
						table.setValue(y, 1, value);
					}
					else if(dataArray.get(i)[1].equalsIgnoreCase("Syrian Arab Republic")){
						table.setValue(y, 0, "Syria");
						table.setValue(y, 1, value);
					}
					else if(dataArray.get(i)[1].equalsIgnoreCase("Democratic People's Republic of Korea")){
						table.setValue(y, 0, "North Korea");
						table.setValue(y, 1, value);
					}
					else if(dataArray.get(i)[1].equalsIgnoreCase("Republic of Korea")){
						table.setValue(y, 0, "South Korea");
						table.setValue(y, 1, value);
					}
					else if(dataArray.get(i)[1].equalsIgnoreCase("Viet Nam")){
						table.setValue(y, 0, "Vietnam");
						table.setValue(y, 1, value);
					}
					else if(dataArray.get(i)[1].equalsIgnoreCase("Lao People's Democratic Republic")){
						table.setValue(y, 0, "Laos");
						table.setValue(y, 1, value);
					}
					
					else if(dataArray.get(i)[1].equalsIgnoreCase("Democratic Republic of the Congo")){
						table.setValue(y, 0, "CD");
						table.setValue(y, 1, value);
					}
					else if(dataArray.get(i)[1].equalsIgnoreCase("South Sudan")){
						table.setValue(y, 0, "SS");
						table.setValue(y, 1, value);
					}
					else if(dataArray.get(i)[1].equalsIgnoreCase("C�te d'Ivoire")){
						table.setValue(y, 0, "CIV");
						table.setValue(y, 1, value);
					}
					else if(dataArray.get(i)[1].equalsIgnoreCase("Republic of Moldova")){
						table.setValue(y, 0, "Moldova");
						table.setValue(y, 1, value);
					}
					else if(dataArray.get(i)[1].equalsIgnoreCase("The former Yugoslav Republic of Macedonia")){
						table.setValue(y, 0, "Macedonia");
						table.setValue(y, 1, value);
					}
					else if(dataArray.get(i)[1].equalsIgnoreCase("Falkland")){
						table.setValue(y, 0, "Falkland Islands (Malvinas)");
						table.setValue(y, 1, value);
					}
					
					
					
					y++;
			}
		}
		Runnable onLoadCallbackMap = new Runnable(){
			public void run(){
				VisualizationMap map = new VisualizationMap();
				GeoMap newMap = map.getMap(table);
				    	  mapPanel.add(newMap.asWidget());
			}
		};

		VisualizationUtils.loadVisualizationApi(onLoadCallbackMap, GeoMap.PACKAGE); 
	}
	
	
	private void addLineChart(){	
		label = new Label("Visualization: ");
		interpolationPanel.add(label);
		interpol = new ListBox();
		interpol.addItem("bla");
		interpolationPanel.add(interpol);
		SimpleRegressionServiceAsync simpleRegSvc = GWT.create(SimpleRegressionService.class);
		double[] points = new double[22];
   		for(int j=0;j<=21;j++){
   			String[] tempNumber= dataArray.get(j);
   			if(tempNumber[2].equals("-")){
   				points[j]=0;
   			}
   			else{
   				points[j]=Double.parseDouble(tempNumber[2]);
   			}
   		}
			
			double[][] data = new double[points.length+1][2];
			for(int k=0;k<=21;k++){
				data[k][0]=k;
				data[k][1]=points[k];
			}
		final double rpcPoints[] = points;	
		simpleRegSvc.getSimpleReg(data, new AsyncCallback<double[]>() {
			

				
				public void onFailure(Throwable caught) {
					// Show the RPC error message to the user
					log.warning("failure creating async callback");	
				}
				public void onSuccess(double[] resultTemp) {
					double[] resultReg = new double[2];
					resultReg=resultTemp;
					Chart chart = vLineChart.getLineChart(dataArray, rpcPoints, resultReg);
					interpolationPanel.add(chart.asWidget());						
				}
		});
	}
	
	private void addBarChart()
	{
		vBarChart = new VisualizationBarChart(dataArray, year);
		
		vBarChart.setTitle("Histogram for ...");
		vBarChart.setTitleX("Range in 10 000");
		vBarChart.setTitleY("Amount");
		
		histogramPanel.add(sliderHisto);
		histogramPanel.add(vBarChart.draw(year, 10));
		
		
		
	}
	
	public void createBarChartFromSlider(){
		vBarChart = new VisualizationBarChart(dataArray, year);
		int cols = vBarChart.getNumColumns();
		histogramPanel.remove(2);
		
		year = Integer.toString((int) sliderHisto.getCurrentValue());
		
		histogramPanel.add(vBarChart.draw(year, cols));
	}
	
	
}
