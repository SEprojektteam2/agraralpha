package com.gwt.client;
import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("try")
public interface DataManagerService extends RemoteService {
	ArrayList<String> getCountries() throws IllegalArgumentException;
	ArrayList<String> getProducts() throws IllegalArgumentException;
	ArrayList<String[]> getData(String country, String product, String type) throws IllegalArgumentException;
}
