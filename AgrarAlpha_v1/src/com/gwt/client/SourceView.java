package com.gwt.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
/*
 Simple VerticalPanel with label witch contains the sources
 */
public class SourceView extends Composite {

	private VerticalPanel vPanel= new VerticalPanel();
	private Label label;
	public SourceView(){
		initWidget(this.vPanel);
		addSource("Source: FAO. 2014. FAOSTAT. data.fao.org. (Accessed 1.9.2014)");

	}
	//add source to the panel
	public void addSource(String s){
		label=new Label(s);
		label.addStyleName("label3");
		vPanel.add(label);
	}
	

}
