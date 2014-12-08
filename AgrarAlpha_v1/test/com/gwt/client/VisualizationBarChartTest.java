package com.gwt.client;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;


//To be able to execute this test and for the errors to desapear, the class VisualizationBarChart must have all of its variables and methods on public and static!!
public class VisualizationBarChartTest{

	ArrayList<String[]> data;
	ArrayList<ArrayList<Double>> convertedData;
	
	@Before
	public void setUp()
	{
		data = new ArrayList<String[]>();
		convertedData = new ArrayList<ArrayList<Double>>();
		
		Random random = new Random();
		System.out.println("I am here!!");
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
			
			VisualizationBarChart_v2.setYearIndex(0);
			VisualizationBarChart_v2.convertData(data);
		}	
	}
	
	@Test
	public void testConvertData()
	{
		for(int i= 0; i < convertedData.size(); i++)
		{
			for(int j = 0; j < convertedData.get(i).size(); j++)
			{
				assertEquals(convertedData.get(i).get(j), VisualizationBarChart_v2.data.get(i).get(j));
			}
		}
		
	}
	@Test
	public void testFindMin() {
		VisualizationBarChart_v2.setYearIndex(0);
		assertEquals(0.0, VisualizationBarChart_v2.findMin(), 0.00000001);
		VisualizationBarChart_v2.setYearIndex(1);
		assertEquals(1.0, VisualizationBarChart_v2.findMin(), 0.00000001);
		VisualizationBarChart_v2.setYearIndex(2);
		assertEquals(2.0, VisualizationBarChart_v2.findMin(),0.00000001);
	}
	
	@Test
	public void testFindMax() {
		VisualizationBarChart_v2.setYearIndex(0);
		assertEquals(0.0, VisualizationBarChart_v2.findMax(),0.00000001);
		VisualizationBarChart_v2.setYearIndex(1);
		assertEquals(4.0, VisualizationBarChart_v2.findMax(),0.00000001);
		VisualizationBarChart_v2.setYearIndex(2);
		assertEquals(8.0, VisualizationBarChart_v2.findMax(),0.00000001);
	}
	
	@Test
	public void testCalculateYearIndex() {
		assertEquals(0, VisualizationBarChart_v2.calculateYearIndex("2011"));
		assertEquals(1, VisualizationBarChart_v2.calculateYearIndex("2010"));
		assertEquals(2011-1990, VisualizationBarChart_v2.calculateYearIndex("1990"));
		assertEquals(-1, VisualizationBarChart_v2.calculateYearIndex("2012"));
		assertEquals(-1, VisualizationBarChart_v2.calculateYearIndex("1989"));
	}
	
	@Test
	public void testCalculateRanges()
	{
		String[] results = VisualizationBarChart_v2.calculateRanges();
		
		//year 0
		
	}
	
	@Test
	public void testCount()
	{
		//TODO
	}

		
	
	

}
