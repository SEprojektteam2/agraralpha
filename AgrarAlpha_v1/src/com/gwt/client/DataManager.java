package com.gwt.client;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.visualization.client.*;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;

@SuppressWarnings("serial")
public class DataManager implements Serializable{
	private DataTable DATA;
	private ArrayList<String[]> DataArray;
	private DataManagerServiceAsync dataManagerSvc = GWT.create(DataManagerService.class);
	
	public DataTable changeFormat(ArrayList<String[]> dataArraylist){
		DataTable DataTemp = DataTable.create();
		
		/*String[] temp = dataArraylist.get(dataArraylist.size());
		int counter = Integer.parseInt(temp[0]);
		String searchingVar = temp[1];
		
		DataTemp.addColumn(ColumnType.STRING, searchingVar);
		for(int j=2011; j>=1990;j--){      //TODO Achtung Funktion zum auffuellen
			DataTemp.addColumn(ColumnType.NUMBER, Integer.toString(j));
		}
				
		DataTemp.addRows(counter);

		for(int c = 0; c < DataTemp.getNumberOfRows(); c++ )
		{
			int k;
			String[] temp2=null;
			for(k = 1; k <= DataTemp.getNumberOfColumns(); k++ )
			{
				temp2=dataArraylist.get(k-1);
				DATA.setCell(c, k, Double.parseDouble(temp2[2]), temp2[2], null);
			}
			DATA.setCell(c, 0, temp2[1],temp2[1] , null);
		}*/
		
		
		DataTemp.addColumn(ColumnType.STRING, "Country");
		DataTemp.addColumn(ColumnType.NUMBER, "2011");
		DataTemp.addColumn(ColumnType.NUMBER, "2010");
		DataTemp.addColumn(ColumnType.NUMBER, "2009");
		
		DataTemp.addRows(5);
		DataTemp.setCell(0, 0, "Switzerland", "Switzerland", null);
		DataTemp.setCell(1, 0, "Germany", "Germany", null);
		DataTemp.setCell(2, 0, "Austria", "Austria", null);
		DataTemp.setCell(3, 0, "Slovakia", "Slovakia", null);
		DataTemp.setCell(4, 0, "Czech Republic", "Czech Republic", null);
		
		
		
		for(int i=1; i<4; i++)
		{
			for(int j=0; j<5; j++)
			{
				double number = Random.nextDouble() % 1000.0;
				DataTemp.setCell(j, i, number, Double.toString(number), null);
			}
		}
		
		
		return DataTemp;
	}
	
	
	public DataTable createDataTable(String country, String product, String type){	
		
		/*dataManagerSvc.getData(country, product, type,
				new AsyncCallback<ArrayList<String[]>>() {
					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
						System.out.println("Error2!");
					}
					
					public void onSuccess(ArrayList<String[]> resultTemp) {
						DATA=changeFormat(resultTemp);
					}
		
		});*/
		
		ArrayList<String[]> resultTemp = null;
		DATA=changeFormat(resultTemp);
		return DATA;
	}
	
	/*public ArrayList<String[]> createDataTable(String country, String product, String type){
	
		dataManagerSvc.getData(country, product, type,
				new AsyncCallback<ArrayList<String[]>>() {
					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
						System.out.println("Error at DataTable!");
					}
				
					public void onSuccess(ArrayList<String[]> resultTemp) {
						DataArray=resultTemp;
						System.out.println("Nice!");
					}
	
		});
		return DataArray;
	}*/
}
