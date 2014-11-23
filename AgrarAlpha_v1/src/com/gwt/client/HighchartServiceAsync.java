package com.gwt.client;
import java.util.ArrayList;

import org.moxieapps.gwt.highcharts.client.Chart;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface HighchartServiceAsync {
	void getCharts(String country, String product, String type, String year, Boolean perCapita, Boolean interpolation, AsyncCallback<ArrayList<Chart>> callback) throws IllegalArgumentException;
}
