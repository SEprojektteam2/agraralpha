package com.gwt.client;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;


/**This test is executed with the class VisualizationBarChart_v2 which is equavalent to the class VisualizationBarChart
 * all its methods and attributes are public and static, to be able to executed this test, without having to change it every time.
 * 
 * @author Romi
 */
public class VisualizationBarChartTest{

	ArrayList<String[]> data;
	ArrayList<ArrayList<Double>> convertedData;
	
	@Before
	public void setUp()
	{
		data = new ArrayList<String[]>();
		convertedData = new ArrayList<ArrayList<Double>>();
		
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
			VisualizationBarChart_v2.setNumColumns(2);
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
		VisualizationBarChart_v2.setNumColumns(2);
		assertEquals(2, VisualizationBarChart_v2.numColumns);
		//TODO Check the ranges with the data - the expected values!!
		VisualizationBarChart_v2.setYearIndex(0);
		String[] results = VisualizationBarChart_v2.calculateRanges();
		//assert
		assertEquals("to 0", results[0]);
		assertEquals("to 0", results[1]);
		
		VisualizationBarChart_v2.setYearIndex(1);
		results = VisualizationBarChart_v2.calculateRanges();
		
		//assertEquals((4-1)/2, Integer.parseInt(results[1].substring(3))-Integer.parseInt(results[0].substring(3)));
		assertEquals("to 2", results[0]);
		assertEquals("to 4", results[1]);
		
		VisualizationBarChart_v2.setYearIndex(2);
		results = VisualizationBarChart_v2.calculateRanges();
		//assert
		assertEquals("to 5", results[0]);
		assertEquals("to 8", results[1]);
		
	}
	
	@Test
	public void testCount()
	{
		//TODO Check the ranges with the data - the expected values!!
		VisualizationBarChart_v2.setYearIndex(0);
		String[] results = VisualizationBarChart_v2.calculateRanges();
		//assert
		assertEquals(0, VisualizationBarChart_v2.count(0, Double.parseDouble(results[0].substring(3))));
		assertEquals(0, VisualizationBarChart_v2.count(Double.parseDouble(results[0].substring(3)), Double.parseDouble(results[1].substring(3))));
		
		VisualizationBarChart_v2.setYearIndex(1);
		results = VisualizationBarChart_v2.calculateRanges();
		//assert
		assertEquals(2, VisualizationBarChart_v2.count(0, Double.parseDouble(results[0].substring(3))));
		assertEquals(2, VisualizationBarChart_v2.count(Double.parseDouble(results[0].substring(3)), Double.parseDouble(results[1].substring(3))));
		
		VisualizationBarChart_v2.setYearIndex(2);
		results = VisualizationBarChart_v2.calculateRanges();
		//assert
		assertEquals(2, VisualizationBarChart_v2.count(0, Double.parseDouble(results[0].substring(3))));
		assertEquals(2, VisualizationBarChart_v2.count(Double.parseDouble(results[0].substring(3)), Double.parseDouble(results[1].substring(3))));
		
	}

		
	
	

}
