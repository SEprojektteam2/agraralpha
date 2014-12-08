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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/** this class is a dialog witch allows the user to enter a name of a saved option
 * if the name he enters exist in the database, createView will be generated with the saved options
 */
 
public class DialogBoxOpen extends DialogBox {
	public static Logger log = Logger.getLogger(DialogBoxOpen.class.getName());
     private TextBox tb;
     private VerticalPanel base;
     private HorizontalPanel btnPanel;
     private HighchartServiceAsync highchartSvc = GWT
 			.create(HighchartService.class);
 	private ArrayList<String[]> savedData = new ArrayList<String[]>();
 	private MainView main;
 	private Label label;
 	
 	
     /**
     * @param data
     * @param main
     */
    public DialogBoxOpen(ArrayList<String[]> data,MainView main){
    	 this.main = main;
    	 this.savedData = data;
    	 // Enable animation.
         setAnimationEnabled(true);

         // Enable glass background.
         setGlassEnabled(true);
    	
         base=new VerticalPanel();
    	 btnPanel=new HorizontalPanel();

    	 label=new Label("Enter the name you saved as:");
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
         Button open = new Button("Open");
         open.addClickHandler(new ClickHandler() {
           public void onClick(ClickEvent event) {
            if(searchForValue()){
            	updateView(getData());
                DialogBoxOpen.this.hide();
            }
            else
            {
            	new DialogBoxCreate("Name doesn't exist!").show();

            }
       
           }
         });
         
         Button close = new Button("close");
         close.addClickHandler(new ClickHandler() {
           public void onClick(ClickEvent event) {
             DialogBoxOpen.this.hide();
           }
         });
         
         
         
         
         
         
         
         btnPanel.add(open);
         btnPanel.add(close);
         
         base.setPixelSize(300, 150);
         base.add(label);
         base.add(tb);
         base.add(btnPanel);

         setWidget(base);
       }
     
     	/**
     	 * @return boolean whether the name is found in database or not
     	 */
     	private boolean searchForValue(){
	    	 String searchingVar = tb.getValue();
	    	 for(int i=0; i<savedData.size();i++){
	    		 if(searchingVar.equals(savedData.get(i)[6]))
	    			 return true;
	    	 }
	    	 return false;
	     }
    	 
		 /**
		 * @return the array with the saved options
		 */
		private String[] getData(){
			 String searchingVar = tb.getValue();
			 for(int i=0; i<savedData.size();i++){
				 if(searchingVar.equals(savedData.get(i)[6]))
					 return savedData.get(i);
			 }
		     return null;
		 }
		 
		 /**
		 * @param data
		 * opens createView with the saved options
		 */
		private void updateView(final String[] data){
			 
			 
			 highchartSvc.getData(data[2], data[3], data[4],
					 Boolean.parseBoolean(data[5]), new AsyncCallback<ArrayList<String[]>>() {
							public void onFailure(Throwable caught) {
								// Show the RPC error message to the user
								System.out.println("Error Arraylist!");
							}

							public void onSuccess(ArrayList<String[]> resultTemp) {
								if(data[2].equals("Global"))
									main.openCreateView(true,resultTemp, data[1]);
								else
									main.openCreateView(false,resultTemp, data[1]);
							}
			    });
		 }

}
