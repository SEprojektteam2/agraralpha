package com.gwt.client;
import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * RPC
 * 
 * @author William Martini
 *
 */
@RemoteServiceRelativePath("DataManager")
public interface DataManagerService extends RemoteService {
	ArrayList<String> getCountries() throws IllegalArgumentException;
	ArrayList<String> getProducts() throws IllegalArgumentException;
	ArrayList<String> getElementNames() throws IllegalArgumentException;
}
