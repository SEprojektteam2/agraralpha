package com.gwt.client;

import static org.junit.Assert.*;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.gwt.client.VisualizationRanking;
import com.gwt.client.VisualizationTable;

public class VisualizationRankingTest extends GWTTestCase {
	
	VisualizationRanking vrTest;

	/**
	 * Tests the method selectionSort of class VisualizationRanking
	 * @author Bill
	 * @author Fabian Weber
	 */
	@Test
	public void testSelectionSort() {
		// Create new ArrayList
		ArrayList<String[]> a = new ArrayList<String[]>();
		
		// Fill ArrayList with some values
		a.add(new String[] { "Schweiz", 	"100", 	"0" });
		a.add(new String[] { "Deutschland", "90", 	"0" });
		a.add(new String[] { "Frankreich", 	"80", 	"23" });
		a.add(new String[] { "England", 	"99", 	"33" });
		a.add(new String[] { "USA",			"2", 	"3" });
		a.add(new String[] { "Belgien", 	"12", 	"13" });
		a.add(new String[] { "Russland", 	"22", 	"23" });
		a.add(new String[] { "World", 		"32",	"33" });
		a.add(new String[] { "Schweiz", 	"2", 	"3" });
		a.add(new String[] { "Austria", 	"12", 	"13" });
		a.add(new String[] { "Australien",	"22", 	"23" });
		a.add(new String[] { "Canada", 		"32", 	"33" });
		a.add(new String[] { "China", 		"2", 	"3" });
		a.add(new String[] { "Thailand", 	"12", 	"13" });
		a.add(new String[] { "Indien", 		"22", 	"23" });
		a.add(new String[] { "Mars", 		"32", 	"33" });
		
		// Create second ArrayList
		ArrayList<String[]> b = new ArrayList<String[]>();
		
		// Create new Object of type VisualizationRanking
		vrTest= new VisualizationRanking(a,6);
		
		// Selection sort the ArrayList and store it to value b
		b = vrTest.selectionSort(a);
		
		// If Size of the ArrayLists don't equal, selectionSort has added or removed some String-Arrays
		if(b.size() != a.size())
			fail("There got some values lost!");
		
		// Check if Values are ordered descending. Otherwise fail.
		for(int i = 0; i<b.size()-1; i++){
			if(Integer.parseInt(b.get(i)[2]) < Integer.parseInt(b.get(i+1)[2]))
				fail("Values aren't correctly ordered");
		}
		
	}
	
	@Test
	public void testCreate(){
		// Create new ArrayList
		ArrayList<String[]> a = new ArrayList<String[]>();
		
		// Fill ArrayList with some values
		a.add(new String[] { "Schweiz", 	"100", 	"0" });
		a.add(new String[] { "Deutschland", "90", 	"0" });
		a.add(new String[] { "Frankreich", 	"80", 	"23" });
		a.add(new String[] { "England", 	"99", 	"33" });
		a.add(new String[] { "USA",			"2", 	"3" });
		a.add(new String[] { "Belgien", 	"12", 	"13" });
		a.add(new String[] { "Russland", 	"22", 	"23" });
		a.add(new String[] { "World", 		"32",	"33" });
		a.add(new String[] { "Schweiz", 	"2", 	"3" });
		a.add(new String[] { "Austria", 	"12", 	"13" });
		a.add(new String[] { "Australien",	"22", 	"23" });
		a.add(new String[] { "Canada", 		"32", 	"33" });
		a.add(new String[] { "China", 		"2", 	"3" });
		a.add(new String[] { "Thailand", 	"12", 	"13" });
		a.add(new String[] { "Indien", 		"22", 	"23" });
		a.add(new String[] { "Mars", 		"32", 	"33" });
		
		// Create second ArrayList
		ArrayList<String[]> b = new ArrayList<String[]>();
		
		// Create new Object of type VisualizationRanking
		vrTest= new VisualizationRanking(a,6);
		
		// Selection sort the ArrayList and store it to value b
		b = vrTest.selectionSort(a);
		
		// If Size of the ArrayLists don't equal, selectionSort has added or removed some String-Arrays
		if(b.size() != a.size())
			fail("There got some values lost!");
		
		// Check if Values are ordered descending. Otherwise fail.
		for(int i = 0; i<b.size()-1; i++){
			if(Integer.parseInt(b.get(i)[2]) < Integer.parseInt(b.get(i+1)[2]))
				fail("Values aren't correctly ordered");
		}
		// Create new HTML object
		HTML html = null;
		// Call create method
		html = vrTest.create();
		// Check if HTML object is null and if so, fail.
		if(html.equals(null))
			fail("There wasn't any return");
		
		}

	@Override
	public String getModuleName() {
		// TODO Auto-generated method stub
		return null;
	}
   
}