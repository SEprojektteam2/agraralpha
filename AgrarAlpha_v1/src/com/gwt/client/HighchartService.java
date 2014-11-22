package com.gwt.client;
import java.util.ArrayList;

import org.moxieapps.gwt.highcharts.client.Chart;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("Highchart")
public interface HighchartService extends RemoteService {
	//Arralist miz Charts
	ArrayList<Chart[]> getCharts(String country, String product, String type, Boolean perCapita, Boolean interpolation) throws IllegalArgumentException;
}
