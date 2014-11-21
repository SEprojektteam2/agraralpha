package com.gwt.client;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;

//This test has to be in the com.gwt.client package to run, and has to be run with GWJ Junit Test..
//this test doesn't run beyond the point of crashing at line 22... I don't know why...
public class DataPreparationTest extends GWTTestCase {

	 DataTable data;
	
	public void gwtSetUp()
	{
		 
		data =  DataTable.create();
		data.addColumn(ColumnType.STRING, "Country");
		data.addColumn(ColumnType.NUMBER, "2011");
		data.addColumn(ColumnType.NUMBER, "2010");
		data.addColumn(ColumnType.NUMBER, "2009");
		
		data.addRows(5);
		data.setCell(0, 0, "Switzerland", "Switzerland", null);
		data.setCell(0, 0, "Germany", "Germany", null);
		data.setCell(0, 0, "Austria", "Austria", null);
		data.setCell(0, 0, "Slovakia", "Slovakia", null);
		data.setCell(0, 0, "Czech Republic", "Czech Republic", null);
		
		
		Random random = new Random();
		for(int i=1; i<4; i++)
		{
			for(int j=0; j<5; j++)
			{
				double number = random.nextDouble() % 1000.0;
				data.setCell(j, i, number, Double.toString(number), null);
			}
		}
	}
	
	@Test
	public void testPrepareData() {
		
		VisualizationManager_v2.TableDATA = data;
		VisualizationManager_v2.prepareData();
		
		assertEquals(data.getNumberOfColumns(), VisualizationManager_v2.DATA.size());
		assertEquals(data.getNumberOfRows(), VisualizationManager_v2.DATA.get(VisualizationManager_v2.DATA.size()-1).getNumberOfRows());
		
		//check all Cells for equality
		for(int i = 0; i < data.getNumberOfColumns()-1; i++)
		{
			for(int j = 0; j < data.getNumberOfRows(); j++)
			{
				String test1 = VisualizationManager_v2.DATA.get(i).getFormattedValue(j, 0);
				String tested1 = data.getFormattedValue(j, i+1);
				assertEquals(tested1, test1);
			}
		}
	}

	@Override
	public String getModuleName() {
		return "com.gwt.AgrarAlpha_v1";
		//return null;
	}

}
