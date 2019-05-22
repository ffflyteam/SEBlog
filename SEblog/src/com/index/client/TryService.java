package com.index.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("try")
public interface TryService extends RemoteService{
	String tryServer(String input)throws IllegalArgumentException; 
}
