package com.gwt.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author Bill
 *
 */
class DialogBoxCreate extends DialogBox {

	private Label label;
	private VerticalPanel vpanel;
	

    /**
     * @param message
     */
    public DialogBoxCreate(String message) {
      // Set the dialog box's caption.
      setText("Error message");

      // Enable animation.
      setAnimationEnabled(true);

      // Enable glass background.
      setGlassEnabled(true);
      vpanel= new VerticalPanel();
      vpanel.setPixelSize(300, 150);
      label = new Label(message);
      label.setStyleName("fuerFabian");

      // DialogBox is a SimplePanel, so you have to set its widget property to
      // whatever you want its contents to be.
      Button ok = new Button("OK");
      ok.setStyleName("beautifulbutton2");
      ok.addClickHandler(new ClickHandler() { //close dialog if ok gets clicked
        public void onClick(ClickEvent event) {
          DialogBoxCreate.this.hide();
        }
      });
      
     
      vpanel.add(label);
      vpanel.add(ok);
      vpanel.setCellHorizontalAlignment(ok,
    		  HasHorizontalAlignment.ALIGN_CENTER);
      vpanel.setCellVerticalAlignment(ok, HasVerticalAlignment.ALIGN_BOTTOM);
      setWidget(vpanel);
    }
  }
