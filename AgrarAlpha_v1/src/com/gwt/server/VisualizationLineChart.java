package com.gwt.server;
import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.i18n.client.*;  
import com.google.gwt.user.client.ui.RootPanel;  

import org.moxieapps.gwt.highcharts.client.*;  
import org.moxieapps.gwt.highcharts.client.labels.*;  
import org.moxieapps.gwt.highcharts.client.plotOptions.*; 

//public class VisualizationLineChart implements EntryPoint{
public class VisualizationLineChart{
	
	/*public void getInterpolation(){
		
	}*/
	
	private ArrayList<String[]> getExtrapolation(ArrayList<String[]> resultData){
		ArrayList<String[]> resultExtra = new ArrayList<String[]>();
		return resultData;
	}
	
	/*public void onModuleLoad(ArrayList<String[]> temp) {  
        RootPanel.get().add(createChart(temp));  
    } */
  
    public Chart createChart(ArrayList<String[]> resultData) {  
    	resultData=getExtrapolation(resultData);
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
		double[] points = new double[2016-1990+1];
		for(int j=0;j<=2016-1990;j++){
			String[] tempNumber= resultData.get(i*27+j);
			points[j]=Double.parseDouble(tempNumber[2]);
		}
		temp = resultData.get(i*27);
		chart.addSeries(chart.createSeries()  
			.setName(temp[1])  
			.setPoints(new Number[]{  
				points[0], points[1], points[2], points[3], points[4], points[5],  
				points[6], points[7], points[8], points[9], points[10], points[11], 
				points[12], points[13], points[14], points[15], points[16], points[17], 
				points[18], points[19], points[20], points[21], points[22], points[23],
				points[24], points[25], points[26] 
			})  
		);
	}
    /*chart.addSeries(chart.createSeries()  
        .setName("Export")  
        .setPoints(new Number[]{  
            4.3, 5.1, 4.3, 5.2, 5.4, 4.7,  
            7.9, 7.9, 7.5, 6.7, 7.7, 7.7, 
            8.2, 8.5, 9.4, 8.1, 10.9, 10.4, 
            7.1, 7.5, 8.1, 6.8, 3.4, 2.1,
            1.9, 2.8, 2.9 
        })  
    );  
    chart.addSeries(chart.createSeries()  
        .setName("Import")  
        .setPoints(new Number[] {  
            0.0, 0.0, 0.0, 0.0, 0.0, 0.0,  
            0.0, 0.4, 0.0, 0.1, 0.0, 0.0,  
            0.0, 0.6, 1.2, 1.7, 0.7, 2.9,  
            3.0, 3.3, 4.8, 5.0, 4.8, 5.0,
            3.2, 2.0, 0.9  
        })  
    ); 
    chart.addSeries(chart.createSeries()  
            .setName("Production")  
            .setPoints(new Number[] {  
                0.0, 0.0, 0.1, 0.0, 0.3, 0.0,  
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0,  
                4.1, 2.6, 3.7, 3.9, 1.7, 2.3,  
                3.2, 2.0, 0.9, 0.4, 0.3, 0.5,  
                5.0, 4.8, 5.0 
            })  
        ); */
        
    	/*final Chart chart = new Chart()  
            .setType(Series.Type.LINE)  
            .setMarginRight(130)  
            .setMarginBottom(25)  
            .setChartTitle(new ChartTitle()  
                .setText("Interpolation and Extrapolation")  
                .setX(-20)  // center  
            )  
        
            .setLegend(new Legend()  
                .setLayout(Legend.Layout.VERTICAL)  
                .setAlign(Legend.Align.RIGHT)  
                .setVerticalAlign(Legend.VerticalAlign.TOP)  
                .setX(-10)  
                .setY(100)  
                .setBorderWidth(0)  
            )  
            .setToolTip(new ToolTip()  
                .setFormatter(new ToolTipFormatter() {  
                    public String format(ToolTipData toolTipData) {  
                        return "<b>" + toolTipData.getSeriesName() + "</b><br/>" +  
                            toolTipData.getXAsString() + ": " + toolTipData.getYAsDouble() + "°C";  
                    }  
                })  
            );  
  
        chart.getXAxis()  
            .setCategories(  
                "1990", "1991", "1992", "1993", "1994", "1995",  
                "1996", "1997", "1998", "1999", "2000", "2001",
                "2002", "2003", "2004", "2005", "2006", "2007",
                "2008", "2009", "2010", "2011", "2012", "2013",
                "2014", "2015", "2016"
            );  
  
        chart.getYAxis()  
            .setAxisTitleText("Value in tonnes");  
  
        chart.addSeries(chart.createSeries()  
            .setName("Export")  
            .setPoints(new Number[]{  
                7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6  
            })  
        );  
        chart.addSeries(chart.createSeries()  
            .setName("Import")  
            .setPoints(new Number[]{  
                -0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5  
            })  
        );  
        chart.addSeries(chart.createSeries()  
            .setName("Production")  
            .setPoints(new Number[]{  
                -0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0  
            })  
        );  */ 
  
        return chart;  
    }  
}
