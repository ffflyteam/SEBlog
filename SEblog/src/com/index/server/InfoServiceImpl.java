package com.index.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.index.client.BlogInfo;
import com.index.client.InfoService;

@SuppressWarnings("serial")
public class InfoServiceImpl extends RemoteServiceServlet implements InfoService {

	@Override
	public BlogInfo infoServer(String input) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		BlogInfo aBlogInfo = new BlogInfo();
		return aBlogInfo;
	}
	
}
