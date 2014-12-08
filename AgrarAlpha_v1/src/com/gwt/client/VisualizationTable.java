package com.gwt.client;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.HTML;

/**
 */
public class VisualizationTable {

	private String tr = "<tr>";
	private String tr2 = "</tr>";
	private String th = "<th>";
	private String th2 = "</th>";
	private String td = "<td>";
	private String td2 = "</td>";
	private String htmlString = new String();
	ArrayList<String[]> arraylist;

	/**
	 * @param a
	 */
	public VisualizationTable(ArrayList<String[]> a) {
		this.arraylist = a;
	}

	/**
	 * @return HTML object with the table in it
	 */
	public HTML create() {
		// if world is selected in the arraylist this class get there is a String areaName
		String isCountry = "AreaName";
		
		//String with the html tags to create an table
		htmlString += "<table id=\"tdisplay\" cellspacing=\"0\" cellpadding=\"0\"";

		
		int start = Integer.parseInt(arraylist.get(0)[0]); //get starting year
		int end = Integer.parseInt(arraylist.get(arraylist.size() - 2)[0]); // get last year

		HTML html = new HTML();
		htmlString += "<thead>";
		htmlString += tr;
		//add table head
		if (arraylist.get(arraylist.size() - 1)[1].equals(isCountry)) { //checks if world is selected
			htmlString += th + "Country" + th2;      
		} else {
			htmlString += th + arraylist.get(arraylist.size() - 1)[2] + th2;
		}
		//add the years in the table head
		for (int a = start; a <= end; a++) {

			htmlString += th + String.valueOf(a) + th2;
		}
		htmlString += "</tr>";
		htmlString += "</thead>";
		htmlString += "<tbody>";
		String checkCountry = arraylist.get(0)[1];
		int i = 0;
		int k = 0;
		
		while (i < arraylist.size() - 1) {
			htmlString += tr;
			htmlString += th + arraylist.get(i)[1] + th2; //add either country name or data type (import export)

		

			while (i < arraylist.size() - 1
					&& checkCountry.equals(arraylist.get(k)[1])) { //checks if its still the same country and add data if it is
				htmlString += td + arraylist.get(i)[2] + td2; // add the data 
				i++;
				if (i < arraylist.size() - 2)
					k = i;

			}
			if (i < arraylist.size())
				checkCountry = arraylist.get(i)[1];

			htmlString += tr2;
		}
		htmlString += "</tbody>";

		htmlString += "</table>";
		html.setHTML(htmlString);
		return html;
	}
}
