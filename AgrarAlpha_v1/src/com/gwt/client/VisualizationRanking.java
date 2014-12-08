package com.gwt.client;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 */
public class VisualizationRanking {
	
	private String tr = "<tr>";
	private String tr2 = "</tr>";
	private String th = "<th>";
	private String th2 = "</th>";
	private String td = "<td>";
	private String td2 = "</td>";
	private HTML table;
	private String htmlString = new String();
	private int year = 0;
	ArrayList<String[]> arraylist = new ArrayList<String[]>();

	/**
	 * @param a
	 */
	public VisualizationRanking(ArrayList<String[]> a, String year) {
		this.year = Integer.parseInt(year);
		this.arraylist = getTopTenCountries(a);
	}

	/**
	 * @return HTML object with the table in it
	 */
	public HTML create() {

		
		// String with the html tags to create an table
		htmlString += "<table id=\"tdisplay\" cellspacing=\"0\" cellpadding=\"0\"";

		

		HTML html = new HTML();
		htmlString += "<thead>";
		htmlString += tr;

		htmlString += th + "Rank" + th2;
		htmlString += th + arraylist.get(0)[0] + th2;
		htmlString += th + arraylist.get(0)[1] + th2;
		htmlString += th + arraylist.get(0)[2] + th2;



		htmlString += "</tr>";
		htmlString += "</thead>";
		htmlString += "<tbody>";

		for(int i=1;i<arraylist.size();i++){
			
			htmlString += tr;
			htmlString += th + String.valueOf(i) + th2;

			for(int j=0; j<3;j++){
				htmlString += th + arraylist.get(i)[j] + th2;

			}
			htmlString += tr2;

		}
		htmlString += "</tbody>";

		htmlString += "</table>";
		html.setHTML(htmlString);
		return html;
	}
	
	public ArrayList<String[]> getTopTenCountries(ArrayList<String[]> dataArray){
		ArrayList<String[]> countries = new ArrayList<String[]>();
		// get the type of data (stored in last row of ArrayList)
		String type = dataArray.get(dataArray.size()-1)[1];
		if(type.equals("AreaName"))
			type = "Country";
		if(type.equals("ItemName"))
			type = "Product";
		if(type.equals("ElementName"))
			type = "Type";

		for(int i = 0; i<dataArray.size()-1; i++){
			if(dataArray.get(i)[0].equals(String.valueOf(year))){
				if(!dataArray.get(i)[2].equals("-"))
					countries.add(dataArray.get(i));
			}
		}
		countries = selectionSort(countries);
		ArrayList<String[]> returnArray = new ArrayList<String[]>();
		String[] headRow = {"Year",type,"Value"};
		returnArray.add(headRow);
		for(int i = 0; i<10; i++)
		{ returnArray.add(countries.get(i));}
		return returnArray;
	}
	
	public ArrayList<String[]> selectionSort(ArrayList<String[]> arr) {
		int i, j, maxIndex; 
		String[] tmp = new String[3];
		int n = arr.size();
		for (i = 0; i < n - 1; i++) {
			maxIndex = i;
			for (j = i + 1; j < n; j++)
				if (Double.valueOf(arr.get(j)[2]) > Double.valueOf(arr.get(maxIndex)[2]))
					maxIndex = j;
			if (maxIndex != i) {
				tmp = arr.get(i);
				arr.set(i, arr.get(maxIndex));
				arr.set(maxIndex,tmp);
			}
		}
		return arr;
	}
	
	//public ArrayList<String[]> removeIrrelevantCountries
}
