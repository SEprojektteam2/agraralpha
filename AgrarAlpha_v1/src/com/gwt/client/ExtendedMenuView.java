package com.gwt.client;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author Bill
 * This class generate the extended menu for the application
 */
public class ExtendedMenuView extends Composite {

	private VerticalPanel vPanel = new VerticalPanel();
	private Button openBtn;
	private Button saveBtn;
	private Button homeBtn;
	private MainView main;
	private DialogBoxOpen openDB;
	private DialogBoxSave saveDB;
	private SaveServiceAsync saveSvc = GWT
			.create(SaveService.class);
	public static Logger log = Logger.getLogger(ExtendedMenuView.class.getName());

	/**@author Bill
	 * @param main
	 * @param selectionView
	 */
	public ExtendedMenuView(final MainView main, final SelectionView selectionView) {
		initWidget(this.vPanel);
		
		this.main=main;
		//create the buttons and add styles and clickHandlers
		openBtn = new Button("Open");
		openBtn.addClickHandler(new openClickHandler());
		openBtn.addStyleName("beautifulbutton");


		saveBtn = new Button("Save");
		saveBtn.addClickHandler(new saveClickHandler());
		saveBtn.addStyleName("beautifulbutton");

	
		
		homeBtn = new Button("Home");
		homeBtn.addClickHandler(new homeClickHandler());
		homeBtn.addStyleName("beautifulbutton");
		
		
		
        //get an arraylist with all stored data
		saveSvc.getSavedData(new AsyncCallback<ArrayList<String[]>>() {
 			public void onFailure(Throwable caught) {
 				// Show the RPC error message to the user
 				log.warning("Error get ElementNames!");
 			}

 			public void onSuccess(ArrayList<String[]> resultTemp) {
 				log.info("success");
 				openDB=new DialogBoxOpen(resultTemp, main);
 				vPanel.add(openBtn);
 				
 				saveDB=new DialogBoxSave(selectionView, resultTemp);
 				vPanel.add(saveBtn);
 			}
 		});
		this.vPanel.add(homeBtn);
		
	
		
		
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
	
	/**
	 * @author Bill
	 * on click call the method from mainView to open the start page
	 */
	private class homeClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			
			main.openHomeView();
		}
		
	}
	/**
	 *on click show the DialogBox "DialogBoxSave"
	 *
	 */
	private class saveClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			saveDB.center();
			saveDB.show();
			}
	}
	
		
	
	
}
