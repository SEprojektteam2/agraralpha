package com.gwt.client;
import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface HighchartServiceAsync {
	void getCharts(String country, String product, String type, Boolean perCapita, AsyncCallback<ArrayList<String[]>> callback) throws IllegalArgumentException;
}
