package com.index.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.index.client.TryService;

@SuppressWarnings("serial")
public class TryServiceImpl extends RemoteServiceServlet implements TryService {

	@Override
	public String tryServer(String input) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return input;
	}
	
}
