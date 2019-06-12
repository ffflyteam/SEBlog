package com.user.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("changeInfo")
public interface ChangeInfoService extends RemoteService{
	public boolean changeInfo(String info) throws IllegalArgumentException;
}
