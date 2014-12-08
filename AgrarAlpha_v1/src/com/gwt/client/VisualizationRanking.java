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
	ArrayList<String[]> arraylist;

	/**
	 * @param a
	 */
	public VisualizationRanking(ArrayList<String[]> a) {
		this.arraylist = a;
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

		for(int i=0;i<arraylist.size()-1;i++){
			
			htmlString += tr;
			htmlString += "th + String.valueOf(i+1) + th2";

			for(int j=0; j<4;j++){
				htmlString += th + arraylist.get(i)[j] + th2;

			}
			htmlString += tr2;

		}
		htmlString += "</tbody>";

		htmlString += "</table>";
		html.setHTML(htmlString);
		return html;
	}
}
