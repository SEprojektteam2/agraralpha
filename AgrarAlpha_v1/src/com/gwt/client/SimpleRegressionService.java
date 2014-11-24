package com.gwt.client;
import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("DataManager")
public interface SimpleRegressionService extends RemoteService {
	double[] getSimpleReg(double[][] data) throws IllegalArgumentException;
}
