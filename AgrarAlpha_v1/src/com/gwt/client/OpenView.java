package com.gwt.client;

import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
// only placeholder at the moment
public class OpenView extends Composite {

	 private VerticalPanel vPanel= new  VerticalPanel();
     private Button b;	
     private TextBox tb;
     
     
     public OpenView(){
    	 initWidget(this.vPanel);

    	 b= new Button("Save Options");
    	 
    	 tb=new TextBox();
    	 tb.setText("Open");
    	 tb.addKeyPressHandler(new KeyPressHandler() {
    		  @Override
    		  public void onKeyPress(KeyPressEvent event) {
    		    TextBox sender = (TextBox) event.getSource();

    		    int keyCode = event.getNativeEvent().getKeyCode();

    		    if (!(Character.isLetterOrDigit(event.getCharCode()))) {
    		      sender.cancelKey();
    		    }
    		  }
    		});
    	 vPanel.add(b);    
    	 vPanel.add(tb);
    	 
     }


}
