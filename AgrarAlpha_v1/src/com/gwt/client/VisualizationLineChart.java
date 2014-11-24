package com.gwt.client;
import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.*;  
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;  

import org.moxieapps.gwt.highcharts.client.*;  
import org.moxieapps.gwt.highcharts.client.labels.*;  
import org.moxieapps.gwt.highcharts.client.plotOptions.*;
import org.apache.commons.math3.stat.regression.SimpleRegression;

//public class VisualizationLineChart implements EntryPoint{
public class VisualizationLineChart{
	
	private SimpleRegressionServiceAsync simpleRegSvc = GWT.create(SimpleRegressionService.class);
	private double[] resultReg = new double[2];
	
	private double[] getSimpleRegression(double[] points){
		double[][] data = new double[points.length+1][2];
		for(int k=0;k<=2011-1990;k++){
			data[k][0]=k;
			data[k][1]=points[k];
		}
		
		simpleRegSvc.getSimpleReg(data, new AsyncCallback<double[]>() {
					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
						System.out.println("Error Arraylist!");
					}

					public void onSuccess(double[] resultTemp) {
						resultReg=resultTemp;
					}
	    });
		return resultReg;
	}
	
	/*public void onModuleLoad(ArrayList<String[]> temp) {  
        RootPanel.get().add(createChart(temp));  
    } */
  
    public Chart createChart(ArrayList<String[]> resultData) {  
    	final Chart chart = new Chart()  
        .setType(Series.Type.SPLINE)  
        .setChartTitleText("Interpolation and Extrapolation")  
        .setToolTip(new ToolTip()  
        .setFormatter(new ToolTipFormatter() {  
            public String format(ToolTipData toolTipData) {  
                return "<b>" + toolTipData.getSeriesName() + "</b><br/>" +  
                    toolTipData.getXAsString() + ": " + toolTipData.getYAsDouble() + "°C";  
            }  
            })  
        )  
        .setSplinePlotOptions(new SplinePlotOptions()  
            .setLineWidth(4)  
            .setHoverStateLineWidth(5)  
            .setMarker(new Marker()  
                .setEnabled(false)  
                .setHoverState(new Marker()  
                    .setEnabled(true)  
                    .setSymbol(Marker.Symbol.CIRCLE)  
                    .setRadius(5)  
                    .setLineWidth(1)  
                )  
            )  
            .setPointInterval(3600000)  // one hour   
        );  

    chart.getXAxis()  
        .setCategories(  
                "1990", "1991", "1992", "1993", "1994", "1995",  
                "1996", "1997", "1998", "1999", "2000", "2001",
                "2002", "2003", "2004", "2005", "2006", "2007",
                "2008", "2009", "2010", "2011", "2012", "2013",
                "2014", "2015", "2016"
            ); 

    final YAxis axis = chart.getYAxis();   
    axis.setAxisTitleText("Value in tonnes")  
        .setMin(0)  
        .setMinorGridLineWidth(0)  
        .setGridLineWidth(0)  
        .setAlternateGridColor(null);  
    
    
    String[] temp = resultData.get(resultData.size());
	int counterMax = Integer.parseInt(temp[0]);
	String searchingVar = temp[1];
	
	for(int i=0; i<counterMax; i++){
		double[] points = new double[2011-1990+1];
		for(int j=0;j<=2011-1990;j++){
			String[] tempNumber= resultData.get(i*22+j);
			points[j]=Double.parseDouble(tempNumber[2]);
		}
		temp = resultData.get(i*22);
		chart.addSeries(chart.createSeries()  
			.setName(temp[1])  
			.setPoints(new Number[]{  
				points[0], points[1], points[2], points[3], points[4], points[5],  
				points[6], points[7], points[8], points[9], points[10], points[11], 
				points[12], points[13], points[14], points[15], points[16], points[17], 
				points[18], points[19], points[20], points[21] 
			})  
		);
		double[] resultReg = getSimpleRegression(points);
		chart.addSeries(chart.createSeries()  
		            .setName("Regression Line "+temp[1])  
		            .setType(Series.Type.LINE)  
		            .setPlotOptions(new LinePlotOptions()  
		                .setMarker(new Marker()  
		                    .setEnabled(false)  
		                )  
		                .setHoverStateLineWidth(0)  
		                .setEnableMouseTracking(false)  
		            )  
		            .setPoints(new Number[][]{  
		                {1990, resultReg[0]}, {2016, resultReg[1]}  
		            })  
				);
	}
  
        return chart;  
    }  
}
