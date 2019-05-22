package com.detail.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("detail")
public interface DetailService extends RemoteService{
	String detailServer(String input)throws IllegalArgumentException; 
}
