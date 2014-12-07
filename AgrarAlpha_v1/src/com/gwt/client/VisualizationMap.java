package com.gwt.client;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.visualizations.GeoMap;

/**
 * Class to create a new GeoMap to add to the mapPanel
 * 
 * @author Fabian Weber
 */
public class VisualizationMap {
	ArrayList<String[]> data = null;
	private GeoMap.Options options = GeoMap.Options.create();
	public static final Logger log = Logger.getLogger(VisualizationMap.class
			.getName());

	/**
	 * Constructor. Set up the map Options.
	 * 
	 * @author Fabian Weber
	 */
	public VisualizationMap() {
		setOptions();
	}

	/**
	 * Set the map Options
	 * 
	 * @author Fabian Weber
	 */
	public void setOptions() {
		// Set the GeoMap mode to REGIONS to see regions view
		options.setDataMode(GeoMap.DataMode.REGIONS);
		// Set height of Map to 600
		options.setHeight(600);
		// Set width of the Map to 1050
		options.setWidth(1050);
		// Show legend in the left bottom corner
		options.setShowLegend(true);
		// Display color should be blue
		options.setColors(0xDEEBF7, 0x000080);
	}

	/**
	 * Returns the GeoMap
	 * 
	 * @author Fabian Weber
	 * @param dataTable
	 *            containing the values for the DataMap
	 * @return GeoMap
	 */
	public GeoMap getMap(DataTable dataTable) {
		// Initialize new GeoMap
		final GeoMap map = new GeoMap(dataTable, options);
		// Return the GeoMap
		return map;
	}

}
