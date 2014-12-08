package com.gwt.client;


import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

//the menu of the starting page
public class MenuView extends Composite {

private VerticalPanel vPanel = new VerticalPanel();
	private Button openBtn;
    private MainView main;
	private Button homeBtn;
    private DialogBoxOpen openDB;
    private SaveServiceAsync saveSvc = GWT
			.create(SaveService.class);
	/**
	 * @param main
	 * @author Bill
	 * menu on the main page to navigate through application
	 */
	public MenuView(final MainView main) {
		initWidget(this.vPanel);
		this.main=main;
		//add buttons, style and clickhandler
		openBtn = new Button("Open");
		openBtn.addClickHandler(new openClickHandler());
		openBtn.addStyleName("beautifulbutton");
		
		homeBtn = new Button("AgrarAlpha");
	    homeBtn.addClickHandler(new homeClickHandler());
	    homeBtn.addStyleName("beautifulbutton");
	    
	    //get the ArrayList with data from the saved options
	    saveSvc.getSavedData(new AsyncCallback<ArrayList<String[]>>() {
 			public void onFailure(Throwable caught) {
 				// Show the RPC error message to the user
 
 			}

 			public void onSuccess(ArrayList<String[]> resultTemp) {
                //only initialize DialogBox if the service was successful and the show the open button
 				openDB=new DialogBoxOpen(resultTemp, main);
 				vPanel.add(openBtn);
 			}
 		});
		
		this.vPanel.add(homeBtn);
		this.vPanel.add(openBtn);
		}
	
	/**@author Bill
	 *on click call the method from mainView to open the start page  
	 */
	private class homeClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			main.openHomeView();
			}
		
	}
	/**@author Bill
	 *on click show the DialogBox "DialogBoxOpen"
	 */
	private class openClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			openDB.center();
			openDB.show();
			}
		
	}
	}
