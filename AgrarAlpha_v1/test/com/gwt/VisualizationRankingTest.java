package com.gwt;

import static org.junit.Assert.*;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Test;

import com.google.gwt.user.client.ui.FlowPanel;
import com.gwt.client.VisualizationTable;

public class VisualizationRankingTest extends TestCase {
	VisualizationTable vTable;

	public void test() {
		ArrayList<String[]> a = new ArrayList();
		String[] sArray = { "Schweiz", "2", "3" };
		String[] sArray1 = { "Deutschland", "12", "13" };
		String[] sArray2 = { "Frankreich", "22", "23" };
		String[] sArray3 = { "England", "32", "33" };
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
		a.add(sArray);
		a.add(sArray1);
		a.add(sArray2);
		a.add(sArray3);
		a.add(sArray4);
		a.add(sArray5);
		a.add(sArray6);
		a.add(sArray7);
		vTable = new VisualizationTable(a);
		//assertEquals(a, vTable.arraylist);

	}
   
}