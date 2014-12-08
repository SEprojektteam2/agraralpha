package com.gwt.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * RPC
 * 
 * @author William Martini
 *
 */
public interface SimpleRegressionServiceAsync {
	void getSimpleReg(double[][] data, AsyncCallback<double[]> callback) throws IllegalArgumentException;
}
