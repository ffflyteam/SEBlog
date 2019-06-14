package com.index.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.user.client.LogoutService;

@SuppressWarnings("serial")
public class LogoutServiceImpl extends RemoteServiceServlet implements LogoutService{

	@Override
	public boolean logout() throws IllegalArgumentException {
		this.getThreadLocalRequest().getSession().removeAttribute("accountId");
		return this.getThreadLocalRequest().getSession().getAttribute("accountId") == null;
	}
	
}
