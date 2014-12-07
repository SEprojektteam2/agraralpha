package com.gwt.client;

//package guiA.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MenuView extends Composite {

private VerticalPanel vPanel = new VerticalPanel();
	private Button openBtn;
    private MainView main;
	private Button homeBtn;
    private DialogBoxOpen openDB;
    private SaveServiceAsync saveSvc = GWT
			.create(SaveService.class);
	/*the menu on the main page to navigate through application*/
	public MenuView(final MainView main) {
		initWidget(this.vPanel);
		this.main=main;
		openBtn = new Button("Open");
		openBtn.addClickHandler(new openClickHandler());
		openBtn.addStyleName("beautifulbutton");
		
		homeBtn = new Button("AgrarAlpha");
	    homeBtn.addClickHandler(new homeClickHandler());
	    homeBtn.addStyleName("beautifulbutton");
	    
	    saveSvc.getSavedData(new AsyncCallback<ArrayList<String[]>>() {
 			public void onFailure(Throwable caught) {
 				// Show the RPC error message to the user
 
 			}

 			public void onSuccess(ArrayList<String[]> resultTemp) {

 				openDB=new DialogBoxOpen(resultTemp, main);
 				vPanel.add(openBtn);
 			}
 		});
		
		this.vPanel.add(homeBtn);
		this.vPanel.add(openBtn);
		}
	
	private class homeClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			main.openHomeView();
			}
		
	}
	private class openClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			openDB.show();
			}
		
	}
	}
