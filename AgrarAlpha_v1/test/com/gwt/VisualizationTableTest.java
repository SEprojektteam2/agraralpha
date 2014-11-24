package com.gwt;

import static org.junit.Assert.*;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Test;

import com.gwt.client.VisualizationTable;

public class VisualizationTableTest extends TestCase{


	public void test() {
		ArrayList<String[]> a = new ArrayList();
		String[] array = { "array" };
		a.add(array);
		VisualizationTable vTable= new VisualizationTable(a);
		assertEquals(a,vTable.arraylist);
		}
	}