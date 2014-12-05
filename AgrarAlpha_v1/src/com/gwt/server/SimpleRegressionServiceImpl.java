package com.gwt.server;


import org.apache.commons.math3.stat.regression.SimpleRegression;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.gwt.client.SimpleRegressionService;


@SuppressWarnings("serial")
public class SimpleRegressionServiceImpl extends RemoteServiceServlet implements 
		SimpleRegressionService {
	public double[] getSimpleReg(double[][] data){
		
		double[] resultReg = new double[27];
		SimpleRegression regression = new SimpleRegression();
		regression.addData(data);
		for(int i=0; i<27;i++){
		resultReg[i]=regression.predict(i);
		}
		//resultReg[0]=0.0;
		//resultReg[1]=27.0;
		return resultReg;
	}
		
}	
	

