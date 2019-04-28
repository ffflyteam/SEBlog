package com.login.server;

import com.login.client.LoginService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {

	public String loginServer(String input) throws IllegalArgumentException {
		return input;
	}
	
}