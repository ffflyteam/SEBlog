package com.manager.index.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("user_info")
public interface UserInfoService extends RemoteService{
	public User getUserInfo();
}
