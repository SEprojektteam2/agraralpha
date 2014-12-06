package com.gwt.client;

//package guiA.client;

import java.util.ArrayList;
import java.util.logging.Logger;

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
	public static Logger log = Logger.getLogger(SelectionView.class.getName());
	private Label yearLabel;
	private Label countryLabel;
	private Label productLabel;
	private Label typeLabel;
	private Label test1; // only to test if the getter functions works
	private Label test2;
	private Label test3;
	private Label test4;


	private CheckBox perCapitaCB;

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
	private SaveServiceAsync saveSvc = GWT
			.create(SaveService.class);
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

		productLabel = new Label("Product");
		typeLabel = new Label("Product Type");

		yearLB = new ListBox();
		yearLB.addItem(" "); // Adding blank option

		
		perCapitaCB=new CheckBox("Per capita");
		
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
		productLB.addChangeHandler(new listBoxChangeHandler(productLB));

		typeLB = new ListBox();
		
		dataManagerSvc.getElementNames(new AsyncCallback<ArrayList<String>>() {
			public void onFailure(Throwable caught) {
				// Show the RPC error message to the user
				log.warning("Error get ElementNames!");
			}

			public void onSuccess(ArrayList<String> resultTemp) {
				for (int j = 0; j < resultTemp.size(); j++) {
					String element = (String) resultTemp.get(j);
					typeLB.addItem(element);
				}
			}
		});
		
		
		/*typeLB.addChangeHandler(new listBoxChangeHandler(typeLB, typeCB));*/

		createBtn = new Button("Create");
		createBtn.addClickHandler(new createClickHandler());
		createBtn.addStyleName("beautifulbutton");
	

		/* Adding every component to the FlexTable */
		fTable.setWidget(0, 0, yearLabel);
		fTable.setWidget(0, 1, yearLB);

		fTable.setWidget(1, 0, countryLabel);
		fTable.setWidget(1, 1, countryLB);
		// fTable.setWidget(1,2,informationL);

		fTable.setWidget(2, 0, productLabel);
		fTable.setWidget(2, 1, productLB);

		fTable.setWidget(3, 0, typeLabel);
		fTable.setWidget(3, 1, typeLB);

		fTable.setWidget(4, 0, perCapitaCB);

		fTable.setWidget(5, 0, createBtn);

	}
	
	public boolean isValid(){
		boolean b=true;
		if(countryLB.isItemSelected(0)){
			if(productLB.isItemSelected(0)||typeLB.isItemSelected(0) ){
				b=false;
			}
			
		}
		else{
			if(productLB.isItemSelected(0) && typeLB.isItemSelected(0)){
				b=false;
			}
		}
		return b;
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
		if (!productLB.isItemSelected(0)) // checks if checkbox is selected
			s = productLB.getValue(productLB.getSelectedIndex());
		else
			s = "null";
		return s;
	}

	public String getType() {
		String s;
		if (!typeLB.isItemSelected(0)) // checks if checkbox is selected
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
			
			  if(yearLB.isItemSelected(0) || isValid()==false){ new
			  DialogBoxCreate().show(); }
			  
			  else{
			
				  
				saveSvc.save(Integer.parseInt(getYear()), getCountry(), getProduct(), getType(),
							perCapitaCB.getValue(), "Mein erster Speicherversuch",  new AsyncCallback<Void>() {
								public void onFailure(Throwable caught) {
									// Show the RPC error message to the user
									log.info("Error Saving!");
								}


								@Override
								public void onSuccess(Void result) {
									// TODO Auto-generated method stub
									log.info("Save succeeded!");
								}
				    });
			

			

			log.warning("hallo");
		 
		    highchartSvc.getData(getCountry(), getProduct(), getType(),
					true, new AsyncCallback<ArrayList<String[]>>() {
						public void onFailure(Throwable caught) {
							// Show the RPC error message to the user
							System.out.println("Error Arraylist!");
						}

						public void onSuccess(ArrayList<String[]> resultTemp) {
							if(getCountry().equals("Global"))
								main.openCreateView(true,resultTemp, getYear());
							else
								main.openCreateView(false,resultTemp, getYear());
						}
		    });
		    

			  }
			 
		}

	}

	// this changehandler will let a label appear when world is not selected and
	// dissapear when a country is selected
	private class countryLBChangeHandler implements ChangeHandler {

		@Override
		public void onChange(ChangeEvent event) {
			if (!countryLB.isItemSelected(0)) { // checks if world is selected
				if(!productLB.isItemSelected(0) && !typeLB.isItemSelected(0)){
				   fTable.getRowFormatter().setVisible(3, false);
				   productLB.setItemSelected(0, true);
				   
			    }
				
			} else {
				fTable.getRowFormatter().setVisible(3, true);
				fTable.getRowFormatter().setVisible(4, true);
			    
			
			}
		}

	}

	private class listBoxChangeHandler implements ChangeHandler {

	private ListBox list;
    private int row;
	private listBoxChangeHandler(ListBox list) {
		this.list = list;
		
        
		if(list.equals(productLB)){
			row=3;
		}
		else{
			row=2;
		}
	}

	/*
	 * this method handles the change of the selection in a listbox if the
	 * user select an option in a listbox the checkbox will adapt
	 */
	public void onChange(ChangeEvent event) {
		if (!countryLB.isItemSelected(0)) {
			if (!list.isItemSelected(0)) {
				fTable.getRowFormatter().setVisible(row, false);
				((ListBox) fTable.getWidget(row, 1)).setItemSelected(0,true);
			} else {
				fTable.getRowFormatter().setVisible(row, true);

			}
			
		}
		
		
	
		}

	}

	
	

}
