package com.gwt.client;
import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DataManagerServiceAsync {
	void getCountries(AsyncCallback<ArrayList<String>> callback) throws IllegalArgumentException;
	void getProducts(AsyncCallback<ArrayList<String>> callback) throws IllegalArgumentException;
	void getData(String country, String product, String type, AsyncCallback<ArrayList<String[]>> callback) throws IllegalArgumentException;
}
