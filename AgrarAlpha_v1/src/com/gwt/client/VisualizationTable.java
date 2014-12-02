package com.gwt.client;

import java.lang.reflect.Array;
import java.util.ArrayList;



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
public class VisualizationTable  {

private	String tr="<tr>";
private	String tr2="</tr>";
private	String th="<th>";
private	String th2="</th>";
private	String td="<td>";
private	String td2="</td>";
private HTML table;
private String htmlString;
ArrayList<String[]> arraylist;
private String leer;
public VisualizationTable(ArrayList<String[]>a){
	this.arraylist=a;
}
	public HTML create() {
	
/*  String[] sArray= {"Schweiz","2","3"};
  String[] sArray1= {"Schweiz","12","13"};
  String[] sArray2= {"Frankreich","22","23"};
  String[] sArray3= {"Frankreich","32","33"};
  String[] sArray4= {"Schweiz","2","3"};
  String[] sArray5= {"Schweiz","12","13"};
  String[] sArray6= {"Frankreich","22","23"};
  String[] sArray7= {"Frankreich","32","33"};
  arraylist.add(sArray);
  arraylist.add(sArray1);
  arraylist.add(sArray2);
  arraylist.add(sArray3);
  arraylist.add(sArray4);
  arraylist.add(sArray5);
  arraylist.add(sArray6);
  arraylist.add(sArray7);
*/
   String isCountry="AreName";		
	htmlString+="<table id=\"tdisplay\" cellspacing=\"0\" cellpadding=\"0\"";
	int start = Integer.parseInt(arraylist.get(0)[0]);
	int end = Integer.parseInt(arraylist.get(arraylist.size() - 2)[0]);

	
		HTML html=new HTML();
		htmlString+="<thead>";
		htmlString+=tr;
		if(arraylist.get(arraylist.size()-1)[1].equals(isCountry)){
		htmlString+=th+"Country"+th2;
		}
		else{
			htmlString+=th+"Product Type"+th2;
		}
		for(int a=start;a<=end;a++){
	    
		htmlString+=th+String.valueOf(a)+th2;}
		htmlString+="</tr>";
		htmlString+="</thead>";
		htmlString+="<tbody>";
        String checkCountry=arraylist.get(0)[1];
		int i=0;
		int k=0;
		
		while(i<arraylist.size()-1){
			htmlString+=tr;
			htmlString+=td+arraylist.get(i)[1]+td2;
			while(i<arraylist.size()-1 && checkCountry.equals(arraylist.get(k)[1])){
				htmlString+=td+arraylist.get(i)[2]+td2;
		      i++;
		      if(i<arraylist.size()-2)
		    	  k=i;
		      
			}
			if(i<arraylist.size())
			checkCountry=arraylist.get(i)[1];
			
			
			htmlString+=tr2;
		}
		htmlString+="</tbody>";

		htmlString+="</table>";

		html.setHTML(htmlString);
        return html;		
		}
}
