package com.gwt.client;

//package guiA.client;

import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/*
 * main view is the home page where the user can choose the options to create
 * the graph(it uses selectionview for this purpose). Or can open saved options
 *@author Bill
 */
public class MainView extends Composite {

	private HorizontalPanel rootPanel = new HorizontalPanel();
	private VerticalPanel contentPanel;
	private SelectionView selView;
	private MenuView menu;
	private ExtendedMenuView emenu;

	
	/**
	 * @author Bill
	 */
	public MainView() {
		initWidget(this.rootPanel);
		

		this.selView = new SelectionView(this);
		this.selView.addStyleName("selView");
		
		
		this.menu = new MenuView(this);
		menu.addStyleName("menu");
		rootPanel.add(menu);
		emenu = new ExtendedMenuView(this, selView);
		emenu.addStyleName("menu");

		contentPanel = new VerticalPanel();
		contentPanel.add(selView);
		rootPanel.add(contentPanel);
		RootPanel.get().getElement().getStyle()
				.setProperty("backgroundColor", "#252530");

	}

	/* clears the panel and add the basic menu  and the selectionView*/
	
		public void openHomeView() {
		rootPanel.clear();
		contentPanel.clear();
		rootPanel.add(menu);

		

		contentPanel = new VerticalPanel();
		contentPanel.add(selView);
		rootPanel.add(contentPanel);
	}
	
	
	/**@author Bill
	 * @param b
	 * @param a
	 * @param year
	 * clears the panel and add the extended menu  and the createView
	   CreateView needs the data to (Arraylist) to generate the charts
	 */
	public void openCreateView(boolean b, ArrayList<String[]> a, String year) {
		rootPanel.clear();
		contentPanel.clear();
		rootPanel.add(emenu);

		CreateView cView = new CreateView(b, a, year);
		contentPanel.add(cView);
		rootPanel.add(contentPanel);
	}
	}
