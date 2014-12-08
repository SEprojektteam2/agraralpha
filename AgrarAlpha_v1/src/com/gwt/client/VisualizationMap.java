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
	
	/**
	 * Replaces invalid ISO-Country names
	 * @param country a country name to check
	 * @return String country in ISO-Format
	 * @author Fabian Weber
	 */
	public String createValidCountry(String country){
		// If / else tree to replace incorrect country names with ISO name
		if (country.equalsIgnoreCase("United States of America"))
			country = "United States";
		
		else if (country.equalsIgnoreCase("Russian Federation"))
			country = "Russia";
		
		else if (country.equalsIgnoreCase("Iran (Islamic Republic of)"))
			country = "Iran";
		
		else if (country.equalsIgnoreCase("Venezuela (Bolivarian Republic of)"))
			country = "Venezuela";

		else if (country.equalsIgnoreCase("Bolivia (Plurinational State of)"))
			country = "Bolivia";

		else if (country.equalsIgnoreCase("United Republic of Tanzania"))
			country = "Tanzania";

		else if (country.equalsIgnoreCase("Congo"))
			country = "CG";

		else if (country.equalsIgnoreCase("Syrian Arab Republic"))
			country = "Syria";

		else if (country.equalsIgnoreCase("Democratic People's Republic of Korea"))
			country = "North Korea";

		else if (country.equalsIgnoreCase("Republic of Korea"))
			country = "South Korea";

		else if (country.equalsIgnoreCase("Viet Nam"))
			country = "Vietnam";

		else if (country.equalsIgnoreCase("Lao People's Democratic Republic"))
			country = "Laos";

		else if (country.equalsIgnoreCase("Democratic Republic of the Congo"))
			country = "CD";

		else if (country.equalsIgnoreCase("South Sudan"))
			country = "SS";

		else if (country.substring(7).equalsIgnoreCase("Ivoire")){
			country = "CI";
			log.warning(country.substring(7));}

		else if (country.equalsIgnoreCase("Republic of Moldova"))
			country = "Moldova";

		else if (country.equalsIgnoreCase("The former Yugoslav Republic of Macedonia"))
			country = "Macedonia";

		else if (country.equalsIgnoreCase("Falkland"))
			country = "Falkland Islands (Malvinas)";
		
		// Return country name. Either changed or not.
		return country;
	}

}
