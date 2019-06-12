package com.user.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("userInfo")
public interface UserInfoService extends RemoteService {
	public User getUserInfo();
}
