package com.gwt.client;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;

public class MessageTest extends GWTTestCase{

	@Test
	public void test() {
		
	//TODO implement the test for the method motavailableMessage(int index);
		Label tested = new Label("message");
		tested.setStyleName("message");
		
		VisualizationManager_v2.notavailableMessage(0);
		assertEquals(tested.getStyleName(), VisualizationManager_v2.graphWidgets[0].getStyleName());
		assertEquals(tested.getClass(), VisualizationManager_v2.graphWidgets[0].getClass());
		assertEquals(tested.getTitle(), VisualizationManager_v2.graphWidgets[0].getTitle());
		
		VisualizationManager_v2.notavailableMessage(1);
		assertEquals(tested.getStyleName(), VisualizationManager_v2.graphWidgets[0].getStyleName());
		assertEquals(tested.getClass(), VisualizationManager_v2.graphWidgets[0].getClass());
		assertEquals(tested.getTitle(), VisualizationManager_v2.graphWidgets[0].getTitle());
		
	}

	@Override
	public String getModuleName() {
		return "com.gwt.AgrarAlpha_v1";
	}

}
