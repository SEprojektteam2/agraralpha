package com.gwt.client;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import com.gwt.client.VisualizationMap;

import org.junit.Test;

/**
 * JUnit Test to test the Class VisualizatonMap
 * 
 * @author Fabian Weber
 *
 */
public class VisualizationMapTest extends TestCase {
	
	@Test
	public void TestConstructor(){
		// Create new Object VisualizationMap and assign it null
		VisualizationMap map = null;
		// Call constructor
		map = new VisualizationMap();
		// Check if map doesn't anymore equal null
		if(map.equals(null))
			fail("Map still equals null");
	}

	@Test
	public void createValidCountryTest(){
		// Create new Object VisualizationMap and assign it null
				VisualizationMap map = null;
				// Call constructor
				map = new VisualizationMap();
	}


}