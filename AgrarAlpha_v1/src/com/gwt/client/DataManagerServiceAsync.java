package com.gwt.client;
import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * RPC
 * 
 * @author William Martini
 *
 */
public interface DataManagerServiceAsync {
	void getCountries(AsyncCallback<ArrayList<String>> callback) throws IllegalArgumentException;
	void getProducts(AsyncCallback<ArrayList<String>> callback) throws IllegalArgumentException;
	void getElementNames(AsyncCallback<ArrayList<String>> callback)throws IllegalArgumentException;
}
