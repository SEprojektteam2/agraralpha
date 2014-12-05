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

public class MainView extends Composite {

	private HorizontalPanel rootPanel = new HorizontalPanel();
	private VerticalPanel contentPanel;
	private SelectionView selView;
	private OpenView openView;
	private MenuView menu;
	private ExtendedMenuView emenu;

	/*
	 * main view is the home page where the user can choose the options to create
	 * the graph. Or can open saved options
	 */
	public MainView() {
		initWidget(this.rootPanel);
		MenuView menu = new MenuView(this);
		menu.addStyleName("menu");
		rootPanel.add(menu);
		emenu = new ExtendedMenuView(this);
		emenu.addStyleName("menu");

		selView = new SelectionView(this);
		selView.addStyleName("selView");

		contentPanel = new VerticalPanel();
		contentPanel.add(selView);
		rootPanel.add(contentPanel);
		RootPanel.get().getElement().getStyle()
				.setProperty("backgroundColor", "#252530");

	}

	/* clears the panel and draw the open view */
	/*
	 * public void openOpenView() { rootPanel.clear(); contentPanel.clear();
	 * ExtendedMenuView menu = new ExtendedMenuView(this); rootPanel.add(menu);
	 * 
	 * //to be created appropriatly from database VisualizationManager VisMan =
	 * null;
	 * 
	 * CreateView cView= new CreateView(visMan);
	 * 
	 * OpenView oView = new OpenView(); contentPanel.add(oView);
	 * rootPanel.add(contentPanel);
	 * 
	 * //create openView
	 * 
	 * }
	 */

	/* clears the panel and draw the home view */
	public void openHomeView() {
		rootPanel.clear();
		contentPanel.clear();
		rootPanel.add(menu);

		selView = new SelectionView(this);

		contentPanel = new VerticalPanel();
		contentPanel.add(selView);
		rootPanel.add(contentPanel);
	}

	public void openCreateView(boolean b, ArrayList<String[]> a, String year) {
		rootPanel.clear();
		contentPanel.clear();
		rootPanel.add(emenu);

		CreateView cView = new CreateView(b, a, year);
		contentPanel.add(cView);
		rootPanel.add(contentPanel);
	}

	public void openChangeView() {
		rootPanel.clear();
		contentPanel.clear();
		rootPanel.add(emenu);

		ChangeView changeView = new ChangeView();
		contentPanel.add(changeView);
		rootPanel.add(contentPanel);
	}

	public void openSaveView() {
		rootPanel.clear();
		contentPanel.clear();
		rootPanel.add(emenu);

		SaveView saveView = new SaveView();
		contentPanel.add(saveView);
		rootPanel.add(contentPanel);
	}

}
