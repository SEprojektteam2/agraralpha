package com.gwt.server;


import java.util.logging.Logger;

import org.apache.commons.math3.stat.regression.SimpleRegression;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.gwt.client.SimpleRegressionService;
import com.gwt.client.VisualizationLineChart;

@SuppressWarnings("serial")
public class SimpleRegressionServiceImpl extends RemoteServiceServlet implements 
		SimpleRegressionService {
	public double[] getSimpleReg(double[][] data){
		
		double[] resultReg = new double[2];
		//SimpleRegression regression = new SimpleRegression();
		//regression.addData(data);
		//resultReg[0]=regression.predict(0);
		//resultReg[1]=regression.predict(26);
		resultReg[0]=0.0;
		resultReg[1]=27.0;
		return resultReg;
	}
		
}	
	

