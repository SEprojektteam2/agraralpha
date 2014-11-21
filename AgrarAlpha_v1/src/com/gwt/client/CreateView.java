package com.gwt.client;

//package guiA.client;

import com.google.gwt.dev.asm.Label;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.GeoMap;
import com.google.gwt.visualization.client.visualizations.Table;

public class CreateView extends Composite{
	
	private TabPanel basePanel = new TabPanel();
	private VerticalPanel tablePanel = new VerticalPanel();
	private VerticalPanel graphPanel = new VerticalPanel();
	private VerticalPanel mapPanel = new VerticalPanel();
	private SourceView source;
	
	private VisualizationManager VisMan;

	/* This class present the view the user has after he clicked the create button on mainView. it contains the graphics the user wants to see
	 */
	public CreateView(final VisualizationManager visMan){
		initWidget(this.basePanel);
		
		
		
		source= new SourceView();
		source.addSource("Source:...."); //add a source
		
		tablePanel = new VerticalPanel();
		graphPanel = new VerticalPanel();
		mapPanel = new VerticalPanel();
        
		/*only placeholer until we can fill with the acutal graphics from visalisationmanager.  will be removed later*/
		Button message = new Button("To be implemented in a future sprint.");
		message.setStyleName("message");
		
		Runnable onLoadCallbackTable = new Runnable(){
			public void run(){
				VisMan = visMan;
				tablePanel.add(VisMan.graphs.get(0));
			}
		};

		VisualizationUtils.loadVisualizationApi(onLoadCallbackTable, Table.PACKAGE);
		  
		
		
		tablePanel.add(source);
		
		//adding table
		//tablePanel.add(child);
		
		
	
		graphPanel.add(message);
		//graphPanel.add(source); // adding a verticalPanel with all source to the mapPanel
		
		Runnable onLoadCallbackMap = new Runnable(){
			public void run(){
				mapPanel.add(VisMan.graphs.get(1));
			}
		};

		VisualizationUtils.loadVisualizationApi(onLoadCallbackMap, GeoMap.PACKAGE);
		  
		//mapPanel.add(message.asWidget());
		mapPanel.add(source); // adding a verticalPanel with all source to the mapPanel
		
		
		basePanel.add(tablePanel,"Table");
		basePanel.add(mapPanel,"Map");
		basePanel.add(graphPanel,"Graph");

		basePanel.selectTab(0); // first tab of the tabPanel will be open
		
	
	}

}
