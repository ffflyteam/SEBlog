package com.other.index.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("getOtherInfo")
public interface UserInfoService extends RemoteService{
	public User getUserInfo(int otherId);
}
