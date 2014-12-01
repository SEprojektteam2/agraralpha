package com.java2s.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TabPanel;

public class GWTClient implements EntryPoint{
  public void onModuleLoad() {
    TabPanel tabs = new TabPanel();
    tabs.add(new HTML("1"), "Basic Panels");
    tabs.add(new HTML("2"), "Dock Panel");
    tabs.add(new HTML("3"), "Tables");
    tabs.setWidth("100%");
    tabs.selectTab(0);

    RootPanel.get().add(tabs);
  }
}
