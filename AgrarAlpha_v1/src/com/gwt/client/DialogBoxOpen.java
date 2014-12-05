package com.gwt.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
// only placeholder at the moment
public class DialogBoxOpen extends DialogBox {

	 private VerticalPanel vPanel= new  VerticalPanel();
     private TextBox tb;
     
     
     public DialogBoxOpen(){

    	 
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
    	setWidget(tb);
    	 // Set the dialog box's caption.
         setText("Invalid selection");

         // Enable animation.
         setAnimationEnabled(true);

         // Enable glass background.
         setGlassEnabled(true);

         // DialogBox is a SimplePanel, so you have to set its widget property to
         // whatever you want its contents to be.
         Button open = new Button("Open");
         open.addClickHandler(new ClickHandler() {
           public void onClick(ClickEvent event) {
             DialogBoxOpen.this.hide();
           }
         });
         setWidget(open);
       }
    	 
     


}
