package com.gwt.client;

import java.util.ArrayList;
import java.util.logging.Logger;
import com.google.gwt.i18n.client.*;

import org.moxieapps.gwt.highcharts.client.*;
import org.moxieapps.gwt.highcharts.client.plotOptions.*;

/**
 * @author William Martini
 *
 */
public class VisualizationLineChart {
	public static Logger log = Logger.getLogger(VisualizationLineChart.class
			.getName());

	private double[] resultReg;
	private double[] points;
	private int index;

	final Chart chart = new Chart();
	ArrayList<String[]> resultData;

	/**
	 * @param resultData
	 * @param points
	 * @param regressionPoints
	 * @return Chart
	 */
	public Chart getLineChart(ArrayList<String[]> resultData, double[] points,
			double[] regressionPoints, int Index) {
		this.resultReg = regressionPoints;
		this.resultData = resultData;
		this.points = points;
		this.index=Index;
		initializeChart();
		generatePoints();

		addRegression();

		return chart;
	}

	public void initializeChart() {
		String info[] = resultData.get(resultData.size() - 1);
		chart.setSize(1050, 600)
				.setChartTitleText("Interpolation and Extrapolation " + info[2])
				.setChartSubtitleText(
						"Source: FAO. 2014. FAOSTAT. data.fao.org. (Accessed 1.9.2014)")
				.setToolTip(new ToolTip().setFormatter(new ToolTipFormatter() {
					public String format(ToolTipData toolTipData) {
						return toolTipData.getXAsDouble()
								+ ": "
								+ NumberFormat.getFormat("#.##").format(
										toolTipData.getYAsDouble());
					}
				}))
				.setAreaPlotOptions(
						new AreaPlotOptions().setPointStart(1990).setMarker(
								new Marker()
										.setEnabled(false)
										.setSymbol(Marker.Symbol.CIRCLE)
										.setRadius(2)
										.setHoverState(
												new Marker().setEnabled(true)))
				);

		chart.getXAxis()
				.setCategories("1990", "1991", "1992", "1993", "1994", "1995",
						"1996", "1997", "1998", "1999", "2000", "2001", "2002",
						"2003", "2004", "2005", "2006", "2007", "2008", "2009",
						"2010", "2011", "2012", "2013", "2014", "2015", "2016")
				.setTickInterval(5);

		final YAxis axis = chart.getYAxis();
		axis.setAxisTitleText("Value")
				//.setMin(0)
				.setMinorGridLineWidth(0).setGridLineWidth(0)
				.setAlternateGridColor(null);

	}

	private void generatePoints() {
		String[] temp2 = resultData.get(index);
		chart.addSeries(chart
				.createSeries()
				.setName(temp2[1])
				.setType(Series.Type.SPLINE)
				.setPoints(
						new Number[] { points[0], points[1], points[2],
								points[3], points[4], points[5], points[6],
								points[7], points[8], points[9], points[10],
								points[11], points[12], points[13], points[14],
								points[15], points[16], points[17], points[18],
								points[19], points[20], points[21]
						}));

	}

	private void addRegression() {
		String[] temp2 = resultData.get(index);
		chart.addSeries(chart
				.createSeries()
				.setName("Regression Line " + temp2[1])
				.setType(Series.Type.SPLINE)
				.setPlotOptions(
						new LinePlotOptions()
								.setMarker(new Marker().setEnabled(true))
								.setHoverStateLineWidth(0)
								.setEnableMouseTracking(true))

				.setPoints(
						new Number[][] { { 0.0, resultReg[0] },
								{ 26.0, resultReg[1] }

						})

		);

	}
}