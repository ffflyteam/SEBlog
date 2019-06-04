package com.user.info.client;

import com.google.gwt.user.client.rpc.RemoteService;

public interface ChangeInfoService extends RemoteService{
	public boolean changeInfo(String info);
}
