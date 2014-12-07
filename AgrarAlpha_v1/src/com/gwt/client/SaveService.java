package com.gwt.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Interface for RPC-Connection to save data entered by users to database and
 * get saved data from the database.
 * 
 * @author Fabian Weber
 */
@RemoteServiceRelativePath("SaveService")
public interface SaveService extends RemoteService {
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
	 * @throws SQLException 
	 */
	void save(int year, String country, String product, String type,
			boolean perCapita, String name) throws IllegalArgumentException;

	/**
	 * @author Fabian Weber
	 * @return Data saved in the database. Returns ArrayList containing String
	 *         arrays in following format: <br>
	 *         String[0] = ID <br>
	 *         String[1] = Year <br>
	 *         String[2] = Country <br>
	 *         String[3] = Product <br>
	 *         String[4] = Type <br>
	 *         String[5] = PerCapita <br>
	 *         String[6] = Name <br>
	 *         String[7] = Date <br>
	 * @throws IllegalArgumentException
	 */
	ArrayList<String[]> getSavedData() throws IllegalArgumentException;
}
