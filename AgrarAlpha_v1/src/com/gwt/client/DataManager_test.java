package com.gwt.client;

import com.google.gwt.user.client.Random;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
//import java.util.ArrayList;
//import com.google.gwt.core.client.GWT;
//import com.google.gwt.user.client.rpc.AsyncCallback;
//import java.io.Serializable;

public class DataManager_test{

	DataManager_test()
	{
		
	}
	
	public DataTable setUpStaticData()
	{
		 
		DataTable data =  DataTable.create();
		data.addColumn(ColumnType.STRING, "Country");
		data.addColumn(ColumnType.NUMBER, "2011");
		data.addColumn(ColumnType.NUMBER, "2010");
		data.addColumn(ColumnType.NUMBER, "2009");
		
		data.addRows(5);
		data.setCell(0, 0, "Switzerland", "Switzerland", null);
		data.setCell(1, 0, "Germany", "Germany", null);
		data.setCell(2, 0, "Austria", "Austria", null);
		data.setCell(3, 0, "Slovakia", "Slovakia", null);
		data.setCell(4, 0, "Czech Republic", "Czech Republic", null);
		
		
		
		for(int i=1; i<4; i++)
		{
			for(int j=0; j<5; j++)
			{
				double number = Math.round((Random.nextDouble() % 1000.0 )* 1000);
				data.setCell(j, i, number, Double.toString(number), null);
			}
		}
		return data;
	}
}
