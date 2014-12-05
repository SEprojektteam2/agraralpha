package com.gwt.client;

//package guiA.client;

import java.sql.SQLException;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ExtendedMenuView extends Composite {

	private VerticalPanel vPanel = new VerticalPanel();
	private Button openBtn;
	private Button saveBtn;
	private Button homeBtn;
	private MainView main;
	private DialogBoxOpen openDB;
	private DialogBoxSave saveDB;

	

	public ExtendedMenuView(MainView main) {
		initWidget(this.vPanel);
		
		this.main=main;
		
		openBtn = new Button("Open");
		openBtn.addClickHandler(new openClickHandler());
		openBtn.addStyleName("beautifulbutton open");


		saveBtn = new Button("Save");
		saveBtn.addClickHandler(new saveClickHandler());
		saveBtn.addStyleName("beautifulbutton save");

	
		
		homeBtn = new Button("Home");
		homeBtn.addClickHandler(new homeClickHandler());
		homeBtn.addStyleName("beautifulbutton spark");
		
		openDB=new DialogBoxOpen();
		saveDB=new DialogBoxSave();

		
		this.vPanel.add(homeBtn);
		this.vPanel.add(openBtn);
		this.vPanel.add(saveBtn);
		
		
	}
	
	private class openClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			
			openDB.show();
			}
		
	}
	
	private class homeClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			
			main.openHomeView();
		}
		
	}
	private class saveClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			saveDB.show();
			}
	}
	
		
	
	
}
