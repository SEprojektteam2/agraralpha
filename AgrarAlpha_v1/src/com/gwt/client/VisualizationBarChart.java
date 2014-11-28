package com.gwt.client;

import java.util.ArrayList;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Series;

public class VisualizationBarChart{

	final static int COLUMNSDEFAULT = 10;
	final static int COLUMNSMIN = 2;
	final static int COLUMNSMAX = 20;
	
	private Chart chart;
	private Number[][] data;
	private int numColumns;
	
	public VisualizationBarChart(ArrayList<String[]> resultData)
	{
		chart = new Chart();
		chart.setType(Series.Type.COLUMN);
		
		prepareData(resultData);
		
		numColumns = 10;
	}
	
	public Chart draw(String year, int columns)
	{
		//if colums is not in range, set to default --> 10
		if (columns < 2 || columns > 20)
		{
			columns = 10;
		}
		
		return chart;
	}
	
	private void prepareData(ArrayList<String[]> resultData)
	{
		
		
	}
	
	private void updateData()
	{
		
	}
	
	private Number[] getDataForYear(String year)
	{
		return data[calculateYearIndex(year)];
	}
	
	private int calculateYearIndex(String year)
	{
		
		int index = -1;
		
		int yInd = Integer.parseInt(year);
		if(yInd < 1990 || yInd > 2011)
			return -1;
		
		yInd -= 2011;
		
		index = Math.abs(yInd); 
		
		return index;
	}
	
	private void setNumColumns(int number)
	{
		numColumns = number;
		updateData();
	}
	
	int getNumColumns()
	{
		return numColumns;
	}
	
	/*
	//beispiel
	Chart chart = new Chart()
   	.setType(Series.Type.SPLINE)
   	.setChartTitleText("Lawn Tunnels")
   	.setMarginRight(10);
   	Series series = chart.createSeries()
   	.setName("Moles per Yard")
   	.setPoints(new Number[] { 163, 203, 276, 408, 547, 729, 628 });
	chart.addSeries(series);
	 */ 
}
