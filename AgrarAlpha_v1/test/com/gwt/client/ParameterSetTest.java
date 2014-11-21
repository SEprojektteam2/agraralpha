package com.gwt.client;

import static org.junit.Assert.*;

import org.junit.Test;

public class ParameterSetTest {

	@Test
	public void test() {
		
		VisualizationManager_v2.setParameters("World", "Apple", "Export");
		assertEquals("Country", VisualizationManager_v2.param1);
		assertEquals("Apple Export", VisualizationManager_v2.param2);
		
		VisualizationManager_v2.setParameters("Switzerland", "null", "Export");
		assertEquals("Product", VisualizationManager_v2.param1);
		assertEquals("Switzerland Export", VisualizationManager_v2.param2);
		
		VisualizationManager_v2.setParameters("Switzerland", "Pie", "null");
		assertEquals("Product Type", VisualizationManager_v2.param1);
		assertEquals("Switzerland Pie", VisualizationManager_v2.param2);
		
		//Exception are being dealt with in a different class. It means that the Visualization Manager will only get valid inputs.
		
		VisualizationManager_v2.setParameters("world", "Pie", "null");
		assertEquals("Country", VisualizationManager_v2.param1);
		assertEquals("Pie Null", VisualizationManager_v2.param2);
		
		VisualizationManager_v2.setParameters("world", "Null", "Import");
		assertEquals("Country", VisualizationManager_v2.param1);
		assertEquals("Null Import", VisualizationManager_v2.param2);
		
		VisualizationManager_v2.setParameters("Germany", "Pie", "Production");
		assertEquals("invalid", VisualizationManager_v2.param1);
		assertEquals("invalid", VisualizationManager_v2.param2);
		
	}

}
