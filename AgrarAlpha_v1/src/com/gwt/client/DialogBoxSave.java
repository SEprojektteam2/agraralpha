package com.gwt.client;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
// only placeholder at the moment
public class DialogBoxSave extends DialogBox {
	public static Logger log = Logger.getLogger(DialogBoxSave.class.getName());
     private TextBox tb;
     private VerticalPanel base;
     private HorizontalPanel btnPanel;
 		private SaveServiceAsync saveSvc = GWT
			.create(SaveService.class);
 		private SelectionView selView;
     public DialogBoxSave(SelectionView selectionView){
    	 this.selView = selectionView;
    	 setText("Save");
    	  // Enable animation.
         setAnimationEnabled(true);
        
         // Enable glass background.
         setGlassEnabled(true);
    	 base=new VerticalPanel();
    	 btnPanel=new HorizontalPanel();

    	 
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

       

         // DialogBox is a SimplePanel, so you have to set its widget property to
         // whatever you want its contents to be.
         Button save = new Button("Save");
         save.addClickHandler(new ClickHandler() {
           public void onClick(ClickEvent event) {
        	   log.info("BEFORE SAVE");
        	   saveData();
             
           }
         });
         
         Button close = new Button("close");
         close.addClickHandler(new ClickHandler() {
           public void onClick(ClickEvent event) {
             DialogBoxSave.this.hide();
           }
         });
        btnPanel.add(save);
        btnPanel.add(close);

   base.add(tb);
   base.add(btnPanel);

         setWidget(base);
       }
    	 
     private void saveData(){
    	  	
			saveSvc.save(Integer.parseInt(selView.getYear()), selView.getCountry(), selView.getProduct(), selView.getType(),
					selView.getPerCapita(), tb.getValue(), new AsyncCallback<Void>() {
							public void onFailure(Throwable caught) {
								// Show the RPC error message to the user
								log.warning(caught.getMessage());
							}


							@Override
							public void onSuccess(Void result) {
								// TODO Auto-generated method stub
								DialogBoxSave.this.hide();
								log.info("SUCCESS!");
							}
			    });
		
     }


}
