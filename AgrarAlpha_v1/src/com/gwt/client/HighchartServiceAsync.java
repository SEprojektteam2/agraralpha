package com.gwt.client;
import java.util.ArrayList;

import org.moxieapps.gwt.highcharts.client.Chart;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface HighchartServiceAsync {
	void getData(String country, String product, String type, Boolean perCapita, AsyncCallback<ArrayList<String[]>> callback) throws IllegalArgumentException;
}
