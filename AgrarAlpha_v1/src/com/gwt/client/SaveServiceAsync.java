package com.gwt.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Interface for RPC-Connection to save data entered by users to database and get saved data from the database.
 * @author Fabian Weber
 */
public interface SaveServiceAsync {

	/**
	 * Saves data in MySQL Database
	 * 
	 * @author Fabian Weber
	 * @param year
	 *            selected by the user
	 * @param country
	 *            selected by the user
	 * @param product
	 *            selected by the user
	 * @param type
	 *            selected by the user
	 * @param perCapita
	 *            selected by the user
	 * @param name
	 *            of the saved DataRow
	 * @throws IllegalArgumentException
	 */
	void save(int year, String country, String product, String type,
			boolean perCapita, String name, AsyncCallback<Void> callback);
	/**
	 * Returns Data
	 * @param callback
	 */
	void getSavedData(AsyncCallback<ArrayList<String[]>> callback);

}
