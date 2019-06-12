package com.index.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("logout")
public interface LogoutService extends RemoteService{
	public boolean logout();
}
