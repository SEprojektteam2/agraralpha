package com.gwt.client;

//package guiA.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.Table;

import java.io.Serializable;

import org.moxieapps.gwt.highcharts.client.Chart;

public class SelectionView extends Composite implements Serializable {

	private Label yearLabel;
	private Label countryLabel;
	private Label test1; // only to test if the getter functions works
	private Label test2;
	private Label test3;
	private Label test4;

	private CheckBox productCB;
	private CheckBox typeCB;

	private ListBox yearLB;
	private FlexTable fTable = new FlexTable(); // size is flexible

	private ListBox countryLB;
	private ListBox productLB;
	private ListBox typeLB;

	private Button createBtn;
	private Label informationL; // will appear if world is selected and inform
								// the user that he can only choose a product or
								// a producttype but not both

	private MainView main;

	private int lastyear = 2011; // last year we got data
	private int CBcounter = 0; // counter how many checkboxes are checked
	// private DataManager data;

	private DataManagerServiceAsync dataManagerSvc = GWT
			.create(DataManagerService.class);
	private HighchartServiceAsync highchartSvc = GWT
			.create(HighchartService.class);

	/*
	 * This class is drawing the options, the user can choose from. The
	 * RootPanel is a FlexTable (Table with flexible size)
	 */
	public SelectionView(MainView main) {

		initWidget(this.fTable);
		this.main = main;

		informationL = new Label("placeholder"); // informs the user that he can
													// only select a product or
													// a producttyp but not both
		yearLabel = new Label("Year");
		countryLabel = new Label("Country");

		productCB = new CheckBox("Product");
		productCB.addClickHandler(new checkBoxClickHandler(productCB));
		typeCB = new CheckBox("Product Type");
		typeCB.addClickHandler(new checkBoxClickHandler(typeCB));

		yearLB = new ListBox();
		yearLB.addItem(" "); // Adding blank option

		/* fills listbox with years */
		String year = null;
		for (int i = 1990; i <= lastyear; i++) // i is the first year user can
												// select
		{
			year = year.valueOf(i);
			yearLB.addItem(year);
		}

		countryLB = new ListBox();
		countryLB.addChangeHandler(new countryLBChangeHandler());
		dataManagerSvc.getCountries(new AsyncCallback<ArrayList<String>>() {
			public void onFailure(Throwable caught) {
				// Show the RPC error message to the user
				System.out.println("Error getCountry!");
			}

			public void onSuccess(ArrayList<String> resultTemp) {
				for (int j = 0; j < resultTemp.size(); j++) {
					String country = (String) resultTemp.get(j);
					countryLB.addItem(country);
				}
			}
		});

		productLB = new ListBox();
		dataManagerSvc.getProducts(new AsyncCallback<ArrayList<String>>() {
			public void onFailure(Throwable caught) {
				// Show the RPC error message to the user
				System.out.println("Error getProduct!");
			}

			public void onSuccess(ArrayList<String> resultTemp) {
				for (int j = 0; j < resultTemp.size(); j++) {
					String product = (String) resultTemp.get(j);
					productLB.addItem(product);
				}
			}
		});
		productLB.addChangeHandler(new listBoxChangeHandler(productLB,
				productCB));

		typeLB = new ListBox();
		typeLB.addItem(" ");
		typeLB.addItem("Import Quantity");
		typeLB.addItem("Export Quantity");
		typeLB.addItem("Production");
		typeLB.addChangeHandler(new listBoxChangeHandler(typeLB, typeCB));

		createBtn = new Button("Create");
		createBtn.addClickHandler(new createClickHandler());

		/* Adding every component to the FlexTable */
		fTable.setWidget(0, 0, yearLabel);
		fTable.setWidget(0, 1, yearLB);

		fTable.setWidget(1, 0, countryLabel);
		fTable.setWidget(1, 1, countryLB);
		// fTable.setWidget(1,2,informationL);

		fTable.setWidget(2, 0, productCB);
		fTable.setWidget(2, 1, productLB);

		fTable.setWidget(3, 0, typeCB);
		fTable.setWidget(3, 1, typeLB);

		fTable.setWidget(4, 0, createBtn);

	}

	public String getYear() {

		String s = yearLB.getValue(yearLB.getSelectedIndex());
		return s;
	}

	public String getCountry() {
		String s = countryLB.getValue(countryLB.getSelectedIndex());
		return s;
	}

	public String getProduct() {
		String s;
		if (productCB.getValue()) // checks if checkbox is selected
			s = productLB.getValue(productLB.getSelectedIndex());
		else
			s = "null";
		return s;
	}

	public String getType() {
		String s;
		if (typeCB.getValue()) // checks if checkbox is selected
			s = typeLB.getValue(typeLB.getSelectedIndex());
		else
			s = "null";
		return s;
	}

	/*
	 * handels the event when createBtn will get clicked checks if requirements
	 * for creating are fullfilled if they are, open the createView if not open
	 * an Dialog
	 */
	private class createClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// dialog appears when user wants to create an invalid selection
			/*
			 * if(yearLB.isItemSelected(0) || CBcounter==0){ new
			 * DialogBoxCreate().show(); }
			 * 
			 * else{
			 */
			// DataManager_test manager = new DataManager_test();
			// VisualizationManager vis= new
			// VisualizationManager(manager.setUpStaticData(), "world", "apple",
			// "consumption", "2010");
			// main.openCreateView(vis);
			// }

			// an william: muesch no apasse mit dene ufruef wo du bruchsch!
			// inklusive datamanager erstelle und datelle mache!!
			// bi "world" muen country Ã¼bergeh werde, wo vo de uswahl gno
			// worde isch
			// bi "aplle" s produkt und bi "pie" de Produkttyp, und no sjahr
			// De visualizatonManager muen da erstellt werde und Ã¼bergeh
			// werde!!
			/*Runnable onLoadCallback = new Runnable() {
				public void run() {
					DataManager2 data = new DataManager2();
					VisualizationManager vis = new VisualizationManager(
							data.createDataTable("India", "Tea", "null"),
							"World", "Tea", "consumption", "2010");
					main.openCreateView(vis);
				}
			};

			VisualizationUtils.loadVisualizationApi(onLoadCallback,
					Table.PACKAGE);*/

			
		    highchartSvc.getData("Namibia", "Tea", "Export Quantity",
					false, new AsyncCallback<ArrayList<String[]>>() {
						public void onFailure(Throwable caught) {
							// Show the RPC error message to the user
							System.out.println("Error Arraylist!");
						}

						public void onSuccess(ArrayList<String[]> resultTemp) {
							main.openCreateView(true,resultTemp);
						}
		    });
		    
			/*highchartSvc.getCharts("India", "Tea", "Import Quantity", "1990", false,
					false, new AsyncCallback<ArrayList<Chart>>() {
						public void onFailure(Throwable caught) {
							// Show the RPC error message to the user
							System.out.println("Error Highchart!");
						}

						public void onSuccess(ArrayList<Chart> resultTemp) {
								RootPanel.get().add(resultTemp.get(0));
							
						}
					});*/

			// DataManager data = new DataManager();
			// data.createDataTable("India", "Tea", "null");
			// VisualizationManager visMan= new
			// VisualizationManager(getCountry(),getProduct(),
			// getType(),getYear());
			// main.openCreateView(visMan);

			/*
			 * //DataManager data = new DataManager();
			 * //data.createDataTable(getCountry(), getProduct(), getType());
			 * //VisualizationManager visMan= new
			 * VisualizationManager(data,getCountry(),getProduct(),
			 * getType(),getYear()); //main.openCreateView(visMan);
			 * 
			 * 
			 * 
			 * dataManagerSvc.getDataTable(getCountry(), getProduct(),
			 * getType(), new AsyncCallback<DataTable>() { public void
			 * onFailure(Throwable caught) { // Show the RPC error message to
			 * the user System.out.println("Error!"); }
			 * 
			 * public void onSuccess(DataTable resultTemp) {
			 * VisualizationManager visMan= new
			 * VisualizationManager(resultTemp,getCountry(),getProduct(),
			 * getType(),getYear()); main.openCreateView(visMan); } });
			 */
			// for testing checkbox behavior its in a comment

			/*
			 * only for testing get methodes test1=new Label(getYear());
			 * test2=new Label(getCountry()); test3=new Label(getProduct());
			 * test4=new Label(getType());
			 * 
			 * fTable.setWidget(5, 0, test1); fTable.setWidget(5, 1, test2);
			 * fTable.setWidget(6, 0, test3); fTable.setWidget(6, 1, test4);
			 */
		}

	}

	// this changehandler will let a label appear when world is not selected and
	// dissapear when a country is selected
	private class countryLBChangeHandler implements ChangeHandler {

		@Override
		public void onChange(ChangeEvent event) {
			if (!countryLB.isItemSelected(0)) { // checks if world is selected
				fTable.setWidget(1, 2, informationL); // add a lable
			} else {
				fTable.remove(informationL); // remove the lable
			}
		}

	}

	private class listBoxChangeHandler implements ChangeHandler {

		private CheckBox box;
		private ListBox list;

		public listBoxChangeHandler(ListBox list, CheckBox box) {
			this.list = list;
			this.box = box;

		}

		/*
		 * this method handles the change of the selection in a listbox if the
		 * user select an option in a listbox the checkbox will adapt
		 */
		public void onChange(ChangeEvent event) {
			if (countryLB.isItemSelected(0)) { // checks if world is chosen

				if (list.isItemSelected(0)) {// checks if the user has picked an
												// option
					if (box.getValue()) { // look if the checkbox is checked
						CBcounter--;
					}

					box.setValue(false);

				} else {
					if (!box.getValue() && CBcounter < 3) { // checks if there
															// already a
															// checkbox checked
															// and if the
															// current checkbox
															// is checked
						CBcounter++;
						box.setValue(true);
					}

				}

			} else {
				if (list.isItemSelected(0)) {
					if (box.getValue()) {
						CBcounter--;
					}

					box.setValue(false);

				} else {
					if (!box.getValue() && CBcounter < 2) {
						CBcounter++;
						box.setValue(true);
					}

				}

			}
		}

	}

	/*
	 * This class handles the checkbox event. The user is allowed to mark one
	 * checkbox if world is selected. If world isn't selected the user is
	 * allowed to mark two boxes
	 */
	private class checkBoxClickHandler implements ClickHandler {

		private CheckBox box;

		public checkBoxClickHandler(CheckBox box) {

			this.box = box;

		}

		@Override
		public void onClick(ClickEvent event) {
			if (countryLB.isItemSelected(0)) { // if world is selected the user
				if (box.getValue()) { // if the box is marked user is always
										// allowed to unmark the box
					box.setValue(false); // unmark the box and subtract the
											// checkbox counter with 1
					CBcounter--;
				} else {
					if (CBcounter < 3) { // not needed now,because there are
											// only two boxes, but when extended
											// with another box will be needed
						box.setValue(true); // mark the box and add 1 to
											// checkbox counter
						CBcounter++;
					}

				}
			} else { // for the case world is not selected the user can only
						// select

				if (box.getValue()) { // if the box is marked user is always
										// allowed to unmark the box
					box.setValue(false); // unmark the box and subtract the
											// checkbox counter with 1
					CBcounter--;
				} else {
					if (CBcounter < 2) {    //user is only allowed to have one CB selected
						box.setValue(true); // mark the box and add 1 to
											// checkbox counter
						CBcounter++;
					}

				}
			}

		}
	}

}
