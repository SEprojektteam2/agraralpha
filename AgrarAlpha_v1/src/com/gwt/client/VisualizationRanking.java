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
		String isWorld = "World";
		  for (int i = 1; i < arraylist.size(); i++) {
		   if (i == 1 && isWorld.equals(arraylist.get(0)[i])) {
		    htmlString += tr;
		    htmlString += td + " " + td2;

		   } else {
		    htmlString += tr;
		    if (isWorld.equals(arraylist.get(0)[i]))
		     htmlString += td + String.valueOf(i - 1) + td2;
		    else
		     htmlString += td + String.valueOf(i) + td2;

		   }
			for(int j=0; j<3;j++){
				htmlString += td + arraylist.get(i)[j] + td2;

			}
			htmlString += tr2;

		}
		htmlString += "</tbody>";

		htmlString += "</table>";
		html.setHTML(htmlString);
		return html;
	}
	
	public ArrayList<String[]> getTopTenCountries(ArrayList<String[]> dataArray) {
		ArrayList<String[]> countries = new ArrayList<String[]>();
		// Get the type of data (stored in last row of ArrayList)
		String type = dataArray.get(dataArray.size() - 1)[1];
		if (type.equals("AreaName"))
			type = "Country";
		if (type.equals("ItemName"))
			type = "Product";
		if (type.equals("ElementName"))
			type = "Type";
		
		// Go through the dataArray
		for (int i = 0; i < dataArray.size() - 1; i++) {
			// If the year equals selected year
			if (dataArray.get(i)[0].equals(String.valueOf(year))) {
				// If value is defined
				if (!dataArray.get(i)[2].equals("-"))
					// If it is a country we don't want to exclude from ranking
					if (filter(dataArray.get(i)[1]))
						//add the country to our ArrayList
						countries.add(dataArray.get(i));
			}
		}
		// Sort ArrayList by selectionSort
		countries = selectionSort(countries);
		// Create new ArrayList in which we will store the values to return
		ArrayList<String[]> returnArray = new ArrayList<String[]>();
		// Generate a informationRow for headrow of the table 
		String[] headRow = { "Year", type, "Value" };
		// Add the informationRow at index 0
		returnArray.add(headRow);
		// Add first 11 values to ArrayList. (11 because if there is world)
		for (int i = 0; i < 11 && i < countries.size(); i++) {
			returnArray.add(countries.get(i));
		}
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
				arr.set(maxIndex, tmp);
			}
		}
		return arr;
	}
	
	//public ArrayList<String[]> removeIrrelevantCountries
	   public boolean filter(String country){
		   if(country.equals("Central African Republic"))
			   return false;
		   if(country.equals("Central America"))
			   return false;
		   if(country.equals("Central Asia"))
			   return false;
		   if(country.equals("China ex. int"))
			   return false;
		   if(country.equals("Eu(12)ex.int"))
			   return false;
		   if(country.equals("Eu(15)ex.int"))
			   return false;
		   if(country.equals("Eu(25)ex.int"))
			   return false;
		   if(country.equals("Eu(27)ex.int"))
			   return false;
		   if(country.equals("Europe"))
			   return false;
		   if(country.equals("European Union"))
			   return false;
		   if(country.equals("Eatern Asia"))
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
		   if(country.equals("Western Africa"))
			   return false;
		   if(country.equals("Western Asia"))
			   return false;
		   if(country.equals("Western Europe"))
			   return false;
		   if(country.equals("Middle Africa"))
			   return false;

		   return true;
	   }

}
