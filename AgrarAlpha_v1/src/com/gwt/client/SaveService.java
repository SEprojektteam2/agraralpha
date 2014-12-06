package com.gwt.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("SaveService")
public interface SaveService extends RemoteService {
	void save(int year, String country, String product, String type, boolean perCapita, String name) throws IllegalArgumentException;
	ArrayList<String[]> getSavedData() throws IllegalArgumentException;
}
