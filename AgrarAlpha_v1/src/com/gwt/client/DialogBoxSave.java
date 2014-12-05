package com.gwt.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
// only placeholder at the moment
public class DialogBoxSave extends DialogBox {
	 private TextBox tb;
     private FlexTable fTable;
     
     public DialogBoxSave(){

    	 fTable=new FlexTable();
    	 tb=new TextBox();
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
    	 // Set the dialog box's caption.

         // Enable animation.
         setAnimationEnabled(true);

         // Enable glass background.
         setGlassEnabled(true);

         // DialogBox is a SimplePanel, so you have to set its widget property to
         // whatever you want its contents to be.
         Button open = new Button("Open");
         open.addClickHandler(new ClickHandler() {
           public void onClick(ClickEvent event) {
             DialogBoxSave.this.hide();
           }
         });
         
         Button cancel = new Button("cancel");
         open.addClickHandler(new ClickHandler() {
           public void onClick(ClickEvent event) {
             DialogBoxSave.this.hide();
           }
         });
        
     	fTable.setWidget(0, 0, tb);
        fTable.setWidget(1, 0, open);
        fTable.setWidget(1, 0, cancel);

         setWidget(fTable);
       }
    	 

}