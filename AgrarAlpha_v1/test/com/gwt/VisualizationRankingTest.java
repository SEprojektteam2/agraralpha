package com.gwt;

import static org.junit.Assert.*;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Test;

import com.google.gwt.user.client.ui.FlowPanel;
import com.gwt.client.VisualizationRanking;
import com.gwt.client.VisualizationTable;

public class VisualizationRankingTest extends TestCase {
	
	VisualizationRanking vrTest;

	public void test() {
		String[] sArray = { "Schweiz", "100", " " };
		String[] sArray1 = { "Deutschland", "90", " " };
		String[] sArray2 = { "Frankreich", "80", "23" };
		String[] sArray3 = { "England", "99", "33" };
		String[] sArray4 = { "USA", "2", "3" };
		String[] sArray5 = { "Belgien", "12", "13" };
		String[] sArray6 = { "Russland", "22", "23" };
		String[] sArray7 = { "World", "32", "33" };
		String[] sArray8 = { "Schweiz", "2", "3" };
		String[] sArray9 = { "Austria", "12", "13" };
		String[] sArray10 = { "Australien", "22", "23" };
		String[] sArray11 = { "Canada", "32", "33" };
		String[] sArray12 = { "China", "2", "3" };
		String[] sArray13 = { "Thailand", "12", "13" };
		String[] sArray14 = { "Indien", "22", "23" };
		String[] sArray15 = { "Mars", "32", "33" };
		ArrayList<String[]> a = new ArrayList();
		ArrayList<String[]> b = new ArrayList();
		b.add(sArray1);
		b.add(sArray);
		b.add(sArray2);
		
		vrTest= new VisualizationRanking(b,0);
		vrTest.selectionSort(b);
		
		assertEquals(sArray,b.get(0));
		assertEquals(sArray1,b.get(1));
		assertEquals(sArray2,b.get(2));

		

		
	}
   
}