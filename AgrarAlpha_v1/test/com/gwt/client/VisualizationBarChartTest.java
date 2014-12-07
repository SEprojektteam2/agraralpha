package com.gwt.client;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Test;


//To be able to execute this test and for the errors to desapear, the class VisualizationBarChart must have all of its variables and methods on public and static!!
public class VisualizationBarChartTest{

	ArrayList<String[]> data;
	ArrayList<ArrayList<Double>> convertedData;
	
	
	public void setUp()
	{
		data = new ArrayList<String[]>();
		convertedData = new ArrayList<ArrayList<Double>>();
		
		Random random = new Random();
		
		for(int i = 0; i < 3; i++)
		{
			convertedData.add(i, new ArrayList<Double>());
			
			double r1 = i*1.0;
			String[] s1 = {Integer.toString(2011-i), "Switzerland", Double.toString(r1)};
			data.add(s1);
			convertedData.get(i).add(r1);
			
			
			double r2 = i*2.0;
			String[] s2 = {Integer.toString(2011-i), "Germany", Double.toString(r2)};
			data.add(s2);
			convertedData.get(i).add(r2);
			
			double r3 = i*3.0;
			String[] s3 = {Integer.toString(2011-i), "France", Double.toString(r3)};
			data.add(s3);
			convertedData.get(i).add(r3);
			
			double r4 = i*4.0;
			String[] s4 = {Integer.toString(2011-i), "Italy", Double.toString(r4)};
			data.add(s4);
			convertedData.get(i).add(r4);
		}	
	}
	
	@Test
	public void testFindMin() {
		VisualizationBarChart.setYearIndex(0);
		assertEquals(0.0, VisualizationBarChart.findMin());
		VisualizationBarChart.setYearIndex(1);
		assertEquals(1.0, VisualizationBarChart.findMin());
		VisualizationBarChart.setYearIndex(2);
		assertEquals(2.0, VisualizationBarChart.findMin());
	}
	
	public void testFindMax() {
		VisualizationBarChart.setYearIndex(0);
		assertEquals(0.0, VisualizationBarChart.findMax());
		VisualizationBarChart.setYearIndex(1);
		assertEquals(4.0, VisualizationBarChart.findMax());
		VisualizationBarChart.setYearIndex(2);
		assertEquals(8.0, VisualizationBarChart.findMax());
	}
	
	public void testCalculateYearIndex() {
		assertEquals(0, VisualizationBarChart.calculateYearIndex("2011"));
		assertEquals(1, VisualizationBarChart.calculateYearIndex("2010"));
		assertEquals(-1, VisualizationBarChart.calculateYearIndex("2012"));
		assertEquals(-1, VisualizationBarChart.calculateYearIndex("1989"));
	}
	
	public void testConvertData()
	{
		VisualizationBarChart.convertData(data);
		
		for(int i= 0; i < convertedData.size(); i++)
		{
			for(int j = 0; i < convertedData.get(i).size(); i++)
			{
				assertEquals(convertedData.get(i).get(j), VisualizationBarChart.data.get(i).get(i));
			}
		}
		
	}
	

}
