package com.gwt.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SaveServiceAsync {

	void save(int year, String country, String product, String type,
			boolean perCapita, String name, AsyncCallback<Void> callback);
	void getSavedData(AsyncCallback<ArrayList<String[]>> callback);

}
