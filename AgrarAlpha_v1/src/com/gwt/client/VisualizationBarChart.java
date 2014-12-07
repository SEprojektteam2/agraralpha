package com.gwt.client;

import java.util.ArrayList;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Series;

public class VisualizationBarChart{

	final static int COLUMNSDEFAULT = 10;
	final static int COLUMNSMIN = 2;
	final static int COLUMNSMAX = 20;
	
	private Chart chart;
	private ArrayList<ArrayList<Double>> data;
	private int numColumns = COLUMNSDEFAULT;
	private int yearIndex;
	
	public VisualizationBarChart(ArrayList<String[]> resultData, String year)
	{
		chart = new Chart();
		chart.setType(Series.Type.COLUMN);
		
		yearIndex = calculateYearIndex(year);
		convertData(resultData);
	}
	
	public Chart draw(String year, int columns)
	{
		//if colums is not in range, set to default --> 10
		if (columns < 2 || columns > 20)
		{
			columns = 10;
		}
		
		setYearIndex(year);
		
		double min = findMin();
		double max = findMax();
		
		double diff = (max-min)/columns;
		String[] cols = new String[columns+1];
		for(int i = 0; i < columns; i++)
		{
			cols[i] = Double.toString(min+diff*(i));
		}
		cols[columns] = Double.toString(max);
		
		
		chart.getXAxis()
		//auf diese Art können nur eine bestimmte anzahl von collonen hinzugefuegt werden
		//andere moeglichkeit um es allgemeiner zu machen?
        .setCategories(cols)
         .setTickInterval(1);
		
		Number[] points = new Number[columns];
		
		for(int i = 0; i < cols.length-1; i++)
		{
			points[i] = count(Double.parseDouble(cols[i]), Double.parseDouble(cols[i+1]));
		}
		
		chart.addSeries(chart.createSeries()  
	   			.setType(Series.Type.COLUMN)
	   			.setPoints(points)  
	   		);
		
		return chart;
	}
	
	//resultData has the form: "year" "product/producttype/country" "double"
	//for Histogramm I am not interested in the String under Index 1!
	private void convertData(ArrayList<String[]> resultData)
	{
		data = new ArrayList<ArrayList<Double>>();
		for(String[] datapart : resultData)
		{
			
			if(data.get(calculateYearIndex(datapart[0])).equals(null))
				data.add(calculateYearIndex(datapart[0]), new ArrayList<Double>());
			
			data.get(calculateYearIndex(datapart[0])).add(Double.parseDouble(datapart[2]));
		}

		//TODO: Cound occourences, make datastructure
		//TODO: add all points to a current series, which will be added to graph!
		
	}
	
	private int count(double min, double max)
	{
		int count = 0;
		
		for(double num : data.get(yearIndex))
		{
			if(num < max || num >= min)
			{
				count++;
			}
		}
		
		return count;
		
	}
	
	private double findMin()
	{
		double curMin = data.get(yearIndex).get(0);
		
		for(int i = 1; i < data.get(yearIndex).size(); i++)
		{
			if(curMin > data.get(yearIndex).get(i))
			{
				curMin = data.get(yearIndex).get(i);
			}
		}
		
		return curMin;
	}
	
	private double findMax()
	{
		double curMax = data.get(yearIndex).get(0);
		
		for(int i = 1; i < data.get(yearIndex).size(); i++)
		{
			if(curMax < data.get(yearIndex).get(i))
			{
				curMax = data.get(yearIndex).get(i);
			}
		}
		
		return curMax;
	}
	
	private void updateData()
	{
		//TODO
	}
	
	private void setYearIndex(int index)
	{
		yearIndex = index;
	}
	
	public void setYearIndex(String year)
	{
		setYearIndex(calculateYearIndex(year));
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
	
	public void setNumColumns(int number)
	{
		numColumns = number;
		updateData();
	}
	
	public int getNumColumns()
	{
		return numColumns;
	} 
	
	public Chart getChart()
	{
		return chart;
	}
}
