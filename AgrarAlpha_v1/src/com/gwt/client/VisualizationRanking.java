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
	public VisualizationRanking(ArrayList<String[]> a, int year) {
		this.year = year;
		this.arraylist = getTopEleven(a);
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

		//adding head of table
		htmlString += th + "Rank" + th2;
		htmlString += th + arraylist.get(0)[1] + th2;
		htmlString += th + arraylist.get(0)[2] + th2;



		htmlString += "</tr>";
		htmlString += "</thead>";
		htmlString += "<tbody>";
		String isWorld = "World";
		//adding elements of table
		  for (int i = 1; i < arraylist.size(); i++) {
		   if (i == 1 && isWorld.equals(arraylist.get(1)[1])) {//if world is selected it is world is always rank 1
		    htmlString += tr;       //new row                            
		    htmlString += td + "Year: "+ arraylist.get(1)[0] + td2; //add the year instead of rank

		   } else {
		    htmlString += tr; //new row
		    if (isWorld.equals(arraylist.get(1)[1])) // check if world is in the arraylist
		     htmlString += td + String.valueOf(i - 1) + td2; //add rank
		    else
		     htmlString += td + String.valueOf(i) + td2;

		   }
			for(int j=1; j<3;j++){
				htmlString += td + arraylist.get(i)[j] + td2; //add table item

			}
			htmlString += tr2;

		}
		htmlString += "</tbody>";

		htmlString += "</table>";
		html.setHTML(htmlString);
		return html;
	}
	
	/**
	 * Returns Top 11 contents of a data ArrayList
	 * @param dataArray containing all the data
	 * @return ArrayList<String[]> containing top 11 contents
	 * @author Fabian Weber
	 */
	public ArrayList<String[]> getTopEleven(ArrayList<String[]> dataArray) {
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
		int pointerBefore, pointerAfter, maxIndex;
		String[] tmp = new String[3];
		int n = arr.size();
		for (pointerBefore = 0; pointerBefore < n - 1; pointerBefore++) {
			maxIndex = pointerBefore;
			for (pointerAfter = pointerBefore + 1; pointerAfter < n; pointerAfter++)
				if (Double.valueOf(arr.get(pointerAfter)[2]) > Double.valueOf(arr.get(maxIndex)[2]))
					maxIndex = pointerAfter;
			if (maxIndex != pointerBefore) {
				tmp = arr.get(pointerBefore);
				arr.set(pointerBefore, arr.get(maxIndex));
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
		   

		   return true;
	   }

}
