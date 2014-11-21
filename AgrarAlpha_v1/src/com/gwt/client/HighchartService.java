package com.gwt.client;
import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("Highchart")
public interface HighchartService extends RemoteService {
	//Arralist miz Charts
	ArrayList<String[]> getCharts(String country, String product, String type, Boolean perCapita) throws IllegalArgumentException;
}
