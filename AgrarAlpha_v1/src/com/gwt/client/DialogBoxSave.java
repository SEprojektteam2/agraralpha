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

/*
 This class is a DialogBox witch allows the user to enter a name. And save the selected options under that name.
 */
public class DialogBoxSave extends DialogBox {
	public static Logger log = Logger.getLogger(DialogBoxSave.class.getName());
	private TextBox tb;
	private Label label;
	private VerticalPanel base;
	private HorizontalPanel btnPanel;
	private SaveServiceAsync saveSvc = GWT.create(SaveService.class);
	private SelectionView selView;
	private ArrayList<String[]> data = new ArrayList<String[]>();

	/**@author Bill
	 * @param selectionView
	 * @param data
	 */
	public DialogBoxSave(SelectionView selectionView, ArrayList<String[]> data) {
		this.data = data;
		this.selView = selectionView;

		
		// Enable animation.
		setAnimationEnabled(true);

		// Enable glass background.
		setGlassEnabled(true);
		
		base = new VerticalPanel();
		base.setPixelSize(300,150);
		label = new Label("Enter a name to save as:");
		label.setStyleName("fuerFabian");
		btnPanel = new HorizontalPanel();

		tb = new TextBox();
		// this event only allows the user to enter letter or numbers other
		// signs get canceled
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

		Button save = new Button("Save As");
		save.setStyleName("beautifulbutton2");
		/**
		 * if save button gets clicked, it will take the string from the textbox
		 * and check if there already is an item with that name in the database.
		 * If there is an error dialog pops up if there isnt the options will be
		 * saved.
		 **/
		save.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				log.info("BEFORE SAVE");
				if (!searchForValue()) //checks if there is already an entry with that name
					saveData();
				else{
					DialogBoxCreate db=new DialogBoxCreate("Name already exists!");
	            	db.center();
	            	db.show();				}
			}
		});

		Button close = new Button("close");
		close.setStyleName("beautifulbutton2");
		close.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				DialogBoxSave.this.hide();
			}
		});
		btnPanel.add(save);
		btnPanel.add(close);
        base.add(label);
		base.add(tb);
		base.add(btnPanel);

		setWidget(base);
	}

	/**@author Bill, Fabian
	 * saves the options selected in a database
	 */
	private void saveData() {

		saveSvc.save(Integer.parseInt(selView.getYear()), selView.getCountry(),
				selView.getProduct(), selView.getType(),
				selView.getPerCapita(), tb.getValue(),
				new AsyncCallback<Void>() {
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

	/**@author Bill, Fabian
	 * @return boolean whether  a string is or isn't already in the database
	 */
	private boolean searchForValue() {
		String searchingVar = tb.getValue();
		for (int i = 0; i < data.size(); i++) {
			if (searchingVar.equals(data.get(i)[6]))
				return true;
		}
		return false;
	}

}
