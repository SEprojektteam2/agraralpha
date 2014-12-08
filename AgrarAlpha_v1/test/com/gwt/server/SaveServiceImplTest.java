package com.gwt.server;

import java.sql.SQLException;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * JUnit Test to test the Class SaveServcie
 * 
 * @author Fabian Weber
 *
 */
public class SaveServiceImplTest extends TestCase {

	/**
	 * Tests the method Constructor of Class SaveServiceImpl
	 * 
	 * @author Fabian Weber
	 */
	@Test
	public void testConstructor() {
		// Create new SaveServiceImpl_Test
		SaveServiceImpl_Test saveService = null;
		// Call the Constructor
		saveService = new SaveServiceImpl_Test();
		// Check if Constructor worked
		if (saveService.equals(null))
			fail("Constructor did not work");
	}

	/**
	 * Tests the method connectToDatabase of Class SaveServiceImpl
	 * 
	 * @author Fabian Weber
	 */
	@Test
	public void testConnectToDatabase() {
		// Create new SaveServiceImpl_Test
		SaveServiceImpl_Test saveService = null;
		// Call the Constructor
		saveService = new SaveServiceImpl_Test();
		// Check if Constructor worked
		if (saveService.equals(null))
			fail("Constructor did not work");
		// Call method connectToDatabase and if id did not succeed fail
		if (!saveService.connectToDatabase())
			fail("Could not connect to database");
	}

	/**
	 * Tests the method getSavedData of Class SaveServiceImpl
	 * @author Fabian Weber
	 */
	@Test
	public void testGetSavedData() {
		// Create new SaveServiceImpl_Test
		SaveServiceImpl_Test saveService = null;
		// Call the Constructor
		saveService = new SaveServiceImpl_Test();
		// Check if Constructor worked
		if (saveService.equals(null))
			fail("Constructor did not work");
		// Call method connectToDatabase and if id did not succeed fail
		if (!saveService.connectToDatabase())
			fail("Could not connect to database");
		// Initialize a new ArrayList
		ArrayList<String[]> arrayList = null;
		// Get data of database
		arrayList = saveService.getSavedData();
		// Check if arrayList has been changed
		if (arrayList.equals(null))
			fail("ArrayList is still null");
		// Check if arrayList does contain Elements
		if (arrayList.size() == 0)
			fail("ArrayList is empty");
		// Check if the String array has 8 fields
		if (arrayList.get(0).length != 8)
			fail("String array does not have the correct format");
	}

	/**
	 * Tests the method saveData() of class SaveServiceImpl
	 * @throws IllegalArgumentException
	 * @throws SQLException
	 * @author Fabian Weber
	 */
	@Test
	public void testSaveData() throws IllegalArgumentException, SQLException {
		// Create new SaveServiceImpl_Test
		SaveServiceImpl_Test saveService = null;
		// Call the Constructor
		saveService = new SaveServiceImpl_Test();
		// Check if Constructor worked
		if (saveService.equals(null))
			fail("Constructor did not work");
		// Call method connectToDatabase and if id did not succeed fail
		if (!saveService.connectToDatabase())
			fail("Could not connect to database");
		// Initialize a new ArrayList
		ArrayList<String[]> arrayList = null;
		// Get data of database
		arrayList = saveService.getSavedData();
		// Get quantity of previously Stored Data
		int numberOfRowsBefore = arrayList.size();
		// Save 4 times some data and check if the values are stored correctly
		for(int i=0; i<4; i++){
			// Always increase year by one
			int year = 2000+i;
			// Set up different values for method call
			String country = "Country " + i;
			String product = "Product " + i;
			String type = "Type " + i;
			Boolean perCapita = true;
			String name = "Name " + i;
			// Save data to database
			saveService.save(year, country, product, type, perCapita, name);
			// Check if returned ArrayList has changed.
			if(arrayList.equals(saveService.getSavedData()))
				fail("ArrayList is still the same");
			if(saveService.getSavedData().size()-1 != numberOfRowsBefore)
				fail("Size of ArrayList did not increase by 1");
			// Get new ArrayList
			arrayList = saveService.getSavedData();
			// Check if year equals saved year
			if (!arrayList.get(arrayList.size() - 1)[1].equals(String.valueOf(year)))
				fail("Year does not equal inserted data: (" + year + ") vs (" + arrayList.get(arrayList.size() - 1)[1] +")");
			// Check if country equals saved country
			if (!arrayList.get(arrayList.size() - 1)[2].equals(country))
				fail("Country does not equal inserted data");
			// Check if product equals saved product
			if (!arrayList.get(arrayList.size() - 1)[3].equals(product))
				fail("Product does not equal inserted data");
			// Check if type equals saved type
			if (!arrayList.get(arrayList.size() - 1)[4].equals(type))
				fail("Type does not equal inserted data");
			// Check if perCapita equals saved perCapita
			if (!Boolean.parseBoolean(arrayList.get(arrayList.size() - 1)[5]) != perCapita)
				fail("PerCapita does not equal inserted data");
			// Check if name equals saved name
			if (!arrayList.get(arrayList.size() - 1)[6].equals(name))
				fail("Name does not equal inserted data");

			// Set actual number of Rows
			numberOfRowsBefore = arrayList.size();
			
		}
	}

}