package com.gwt.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * RPC
 * 
 * @author William Martini
 *
 */
@RemoteServiceRelativePath("SimpleRegression")
public interface SimpleRegressionService extends RemoteService {
	double[] getSimpleReg(double[][] data) throws IllegalArgumentException;
}
