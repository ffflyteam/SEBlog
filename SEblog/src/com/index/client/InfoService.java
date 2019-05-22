package com.index.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("info")
public interface InfoService extends RemoteService {
	BlogInfo infoServer(String input)throws IllegalArgumentException; 
}
