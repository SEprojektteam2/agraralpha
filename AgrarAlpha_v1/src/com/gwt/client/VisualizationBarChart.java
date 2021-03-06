package com.gwt.client;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Series;

/**
 * @author Romana Pernischova
 *
 * This class creates a new ColumnChart with GWT Highchart. This ColumnsChart will be showing an Histogram and is not a regular ColumnChart.
 * The data for this Histogram is calculated within this chart and will be done automatically, when creating an object of this class.
 * To draw the created chart it has to be added to the Rootpanel though calling the Method, which returns the chart.
 */
public class VisualizationBarChart{

	final static int COLUMNSDEFAULT = 10;
	final static int COLUMNSMIN = 2;
	final static int COLUMNSMAX = 20;
	
	private Chart chart;
	private ArrayList<ArrayList<Double>> data;
	private int numColumns = COLUMNSDEFAULT;
	private int yearIndex;
	
	public static final Logger log = Logger.getLogger(VisualizationBarChart.class.getName());
	
	/**
	 * The constructor receives the data from the database in a String form and the year in string form.
	 * A chart is created, year Index is set and the data is converted into a form with which further modulation is possible.
	 * 
	 * @param resultData
	 * @param year
	 */
	public VisualizationBarChart(ArrayList<String[]> resultData, String year)
	{
		//create a new Chart with a Column-Series
		chart = new Chart();
		chart.setType(Series.Type.COLUMN);
		
		//set the year Index
		yearIndex = calculateYearIndex(year);
		convertData(resultData);
		
		//set some window properties
		String info[]=resultData.get(resultData.size()-1);
		chart.setSize(1050,600)
	     .setChartTitleText("Histogram "+info[2])
	        .setChartSubtitleText("Source: FAO. 2014. FAOSTAT. data.fao.org. (Accessed 1.9.2014)");
	}
	
	/**
	 * A series will be appropriately calculated and added to the chart.
	 * 
	 * @param year
	 * @param columns
	 * @return
	 */
	public Chart draw(String year, int columns)
	{
		chart.removeAllSeries();
		
		//if colums is not in range, set to default --> 10
		setNumColumns(columns);
		
		setYearIndex(year);
		
		//calculation and setting of ranges for Histogram
		String[] cols = calculateRanges();
		
		//adding the calculated ranges as categories
		chart.getXAxis().setCategories(cols).setTickInterval(1);
		
		//creating points which will be added to series of the chart, based on ranges calculated previously
		Number[] points = new Number[columns];
		points[0] = count(0, Double.parseDouble(cols[0].substring(3)));
		for(int i = 0; i < cols.length-1; i++)
		{
			points[i+1] = count(Double.parseDouble(cols[i].substring(3)), Double.parseDouble(cols[i+1].substring(3)));
		}
		
		//adding the series to the chart
		chart.addSeries(chart.createSeries().setType(Series.Type.COLUMN).setPoints(points).setName(year));
		
		//this return is important, when executing this method from outside
		return chart.redraw();
	}

	/**
	 * Calculation of ranges for the histogram based on set column number
	 * 
	 * @return a string Array of the max of each range is returned
	 */
	private String[] calculateRanges() {
		// finding the min and max for the the first (min) and last (max) range
		double min = findMin();
		double max = findMax();

		// calculating the diff between each ranges max and min
		double diff = (max - min) / numColumns;
		
		// creating the categhories for the xAchsis, which won't be added to the chart in this method!
		String[] cols = new String[numColumns];
		for (int i = 0; i < numColumns-1; i++) 
		{
			cols[i] = "to "+ Integer.toString((int)(min + (diff * (i+1))));
		}
		cols[numColumns-1] = "to "+ Integer.toString((int)Math.ceil(max));
		
		return cols;
	} 
	
	/**
	 * resultData has the form: "year" "product/producttype/country" "double"
	 * for Histogramm I am not interested in the String under Index 1!
	 * 
	 * all String will be converted into doubles for further calculation
	 * this will be stored in the local variable data
	 * 
	 * @param ArrayList<String[]>	this is the data that the database has returned
	 */
	private void convertData(ArrayList<String[]> resultData)
	{
		data = new ArrayList<ArrayList<Double>>();
		
		//generate ArrayLists for all years, so that no outOfBoundExeption will be throughn when adding data
		for(int i = calculateYearIndex("2011"); i <= calculateYearIndex("1990"); i++)
		{
			data.add(i, new ArrayList<Double>());
		}
		
		for(String[] datapart : resultData)
		{	
			//if year is not within range, we have reached the end of the data.
			//We don't want to consider this last dataline for converted data.
			if(calculateYearIndex(datapart[0]) < 0)
				break;
			if(!(datapart[2].equals("-")))
			{
				//Only add the dataline if it is an accepted country
				if(isRelevantCountry(datapart[1]))
					data.get(calculateYearIndex(datapart[0])).add(Double.parseDouble(datapart[2]));
			}
		}
	}
	
	/**
	 * Count occurences in data of the set year for a range [min, max) and return the amount
	 * 
	 * @param min
	 * @param max
	 * @return the occurances counted for the particular range [min, max)
	 */
	private int count(double min, double max)
	{
		int count = 0;
		
		for(double num : data.get(yearIndex))
		{
			//if current data is 0 - ignore
			if(num == 0.0)
				;
			//if current data is within range - increase counter
			else if(Double.compare(max, num) >= 0 && Double.compare(num, min) > 0 )
			{
				count++;
			}
		}
		
		return count;
	}
	
	/**
	 * Finds the minimal value for the set year and returns it
	 * 
	 * @return the found minimum in the set year
	 */
	private double findMin()
	{
		double curMin = data.get(yearIndex).get(0);
		
		for(int i = 1; i < data.get(yearIndex).size(); i++)
		{
			//if current Minimum is larger - set new Minimum
			if(curMin > data.get(yearIndex).get(i))
			{
				curMin = data.get(yearIndex).get(i);
			}
		}
		
		return curMin;
	}
	
	/**
	 * Finds the maximal value for the set year and returns it
	 * 
	 * @return the found minimum in the set year
	 */
	private double findMax()
	{
		double curMax = data.get(yearIndex).get(0);
		
		for(int i = 1; i < data.get(yearIndex).size(); i++)
		{
			//if current Maximum is larger - set new Maximum
			if(curMax < data.get(yearIndex).get(i))
			{
				curMax = data.get(yearIndex).get(i);
			}
		}
		
		return curMax;
	}
	
	/**
	 * The parameter year, which is a string will converted into an integer.
	 * The indices in data start with 0 being the newest year, which means that the
	 * given year needs to be converted into such an index
	 * 
	 * @param year
	 * @return index
	 */
	private int calculateYearIndex(String year)
	{
		
		int index = -1;
		
		//if not within Range - return year as a negative number. This will create an outOfBoundExeption
		int yInd = Integer.parseInt(year);
		if(yInd < 1990 || yInd > 2011)
			return -yInd;
		
		yInd -= 2011;
		
		index = Math.abs(yInd); 
		
		return index;
	}
	
	/**
	 * call this method after changing something in the Chart at runtime and it will return an updated chart.
	 * 
	 * @return the updated Chart
	 */
	public Chart redraw()
	{
		return chart.redraw();
	}
	
//get- and set-Methods for some of the attributes
	
	public void setYearIndex(int index)
	{
		yearIndex = index;
	}
	
	public void setYearIndex(String year)
	{
		setYearIndex(calculateYearIndex(year));
	}
	
	public void setNumColumns(int number)
	{
		if (number < COLUMNSMIN || number > COLUMNSMAX)
		{
			numColumns = COLUMNSDEFAULT;
			return;
		}
		numColumns = number;
	}
	
	public int getNumColumns()
	{
		return numColumns;
	} 
	
	public Chart getChart()
	{
		return chart;
	}
	
	public void setTitle(String title)
	{
		chart.setTitle(title);
	}
	
	public void setTitleX(String xAchsis)
	{
		chart.getXAxis().setAxisTitleText(xAchsis);
	}
	
	public void setTitleY(String yAchsis)
	{
		chart.getYAxis().setAxisTitleText(yAchsis);
	}
	
	  public boolean isRelevantCountry(String country){
		   if(country.equals("Central African Republic"))
			   return false;
		   if(country.equals("Central America"))
			   return false;
		   if(country.equals("Central Asia"))
			   return false;
		   if(country.equals("China ex. int"))
			   return false;
		   if(country.equals("EU(12)ex.int"))
			   return false;
		   if(country.equals("EU(15)ex.int"))
			   return false;
		   if(country.equals("EU(25)ex.int"))
			   return false;
		   if(country.equals("EU(27)ex.int"))
			   return false;
		   if(country.equals("Europe"))
			   return false;
		   if(country.equals("European Union"))
			   return false;
		   if(country.equals("Eastern Asia"))
			   return false;
		   if(country.equals("South Africa"))
			   return false;
		   if(country.equals("South America"))
			   return false;
		   if(country.equals("Southern Africa"))
			   return false;
		   if(country.equals("Southern Asia"))
			   return false;
		   if(country.equals("Southern Europe"))
			   return false;
		   if(country.equals("South-Eastern Asia"))
			   return false; 
		   if(country.equals("Small Island Developing States"))
			   return false;
		   if(country.equals("Western Africa"))
			   return false;
		   if(country.equals("Western Asia"))
			   return false;
		   if(country.equals("Western Europe"))
			   return false;
		   if(country.equals("Middle Africa"))
			   return false;
		   if(country.equals("Northern America"))
			   return false;
		   if(country.equals("Asia"))
			   return false;
		   if(country.equals("Eastern Asia"))
			   return false;
		   if(country.equals("Eastern Europe"))
			   return false;
		   if(country.equals("Low Income Food Deficit Countries"))
			   return false;
		   if(country.equals("Net Food Importing Developing Countries"))
			   return false;
		   if(country.equals("Africa"))
			   return false;
		   if(country.equals("Eastern Asia"))
			   return false;
		   if(country.equals("Least Developed Countries"))
			   return false;
		   if(country.equals("Americas"))
			   return false;
		   if(country.equals("Northern Europe"))
			   return false;
		   if(country.equals("Low Income Food Deficit Countries"))
			   return false;
		   if(country.equals("Northern Europe"))
			   return false;
		   if(country.equals("Australia & New Zealand"))
			   return false; 
		   if(country.equals("Land Locked Developing Countries"))
			   return false; 
		   if(country.equals("China, mainland"))
			   return false; 
		   if(country.equals("Eastern Africa"))
			   return false; 
		   if(country.equals("Northern Africa"))
			   return false; 
		   if(country.equals("World"))
			   return false; 
		   return true;
	   }
}
