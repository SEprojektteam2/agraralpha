package com.gwt.client;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SourceView extends Composite {

	private VerticalPanel vPanel= new VerticalPanel();
	private Label label;
	public SourceView(){
		initWidget(this.vPanel);

	}
	
	public void addSource(String s){
		label=new Label(s);
		vPanel.add(label);
	}
	

}
