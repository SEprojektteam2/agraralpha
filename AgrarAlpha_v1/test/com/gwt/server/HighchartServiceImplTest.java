package com.gwt.server;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;

public class HighchartServiceImplTest {
	@Test
	public void calcPerCapitaTest() {
		//create new HighchartServiceImpl
		HighchartServiceImpl_test test = new HighchartServiceImpl_test();
		ArrayList<String[]> arraylist = new ArrayList<String[]>();
		
		//add a string array to arraylist
		String[] content = {"Software", "Engineering"};
		arraylist.add(content);
		
		//get calc per capita
		ArrayList<String[]> answer = test.getPopulation();
		
		//test if calcPerCapita returns the same arraylist
		if(!answer.equals(arraylist))
			fail("Returned array doesn't equal sent array");
		else
			//print out result
			System.out.println(answer.get(0)[0] + " " + answer.get(0)[1]);
		
	}
	
	@Test
	public void ConnectToDatabaseTest(){
		//generate a new HighChartServiceImpl
		HighchartServiceImpl_test test = new HighchartServiceImpl_test();
		
		//Get the value of the connection before the method call
		Connection connection1 = test.conn;
		
		//call the connect method
		test.connectToDatabase();
		
		//Get the value of the connection after the method call
		Connection connection2 = test.conn;
		
		try {
			//check if connection before method call was null.
			if(connection1 != null)
				fail("Connection already exists before method call!");
			
			//check if connection after method call is valid.
			if(!connection2.isValid(500))
				fail("Connection failed!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			fail(e.toString());
		}	
	}
	
	@Test
	public void getCounterTest(){
		//generate a new HighChartServiceImpl
		HighchartServiceImpl_test test = new HighchartServiceImpl_test();
		test.connectToDatabase();
		int number = test.getCounter("Select AreaName, Year, Value from records where ItemName = 'Apple' Order by year ASC");
		
		Connection conn = test.conn;
		System.out.println(number);
	}
	
	@Test
	public void getDataTest(){
		//generate a new HighChartServiceImpl
		HighchartServiceImpl test = new HighchartServiceImpl();
	}
	
	@Test
	public void readDatabaseTest(){
		//generate a new HighChartServiceImpl
		HighchartServiceImpl_test test = new HighchartServiceImpl_test();
		test.connectToDatabase();
		
		ArrayList<String[]> arraylist = test.readDatabase("Select AreaName, ItemName, Value from records where ElementName = 'Import Quantity' AND ItemName = 'Tea' And AreaName = 'Sri Lanka' Order by year ASC", "ItemName", "Value",false);
		System.out.println(arraylist.get(0)[0]);
		System.out.println(arraylist.get(0)[1]);
		System.out.println(arraylist.get(0)[2]);
		System.out.println(arraylist.get(1)[0]);
		System.out.println(arraylist.get(1)[1]);
		System.out.println(arraylist.get(1)[2]);
	}

}
