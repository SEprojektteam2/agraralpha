package com.gwt.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
// only placeholder at the moment
public class ChangeView extends Composite {

	 private FlowPanel fPanel= new FlowPanel();
     private Button b;	
     
     public ChangeView(){
    	 initWidget(this.fPanel);
    	 b= new Button("coming soon3");
    	 fPanel.add(b);
    	 
     }


}
