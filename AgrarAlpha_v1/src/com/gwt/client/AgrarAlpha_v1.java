package com.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class AgrarAlpha_v1 implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
	    MainView gui = new MainView();
	    RootPanel.get().add(gui);;

}}
