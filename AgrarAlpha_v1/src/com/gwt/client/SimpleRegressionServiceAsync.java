package com.gwt.client;
import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SimpleRegressionServiceAsync {
	void getSimpleReg(double[][] data, AsyncCallback<double[]> callback) throws IllegalArgumentException;
}
