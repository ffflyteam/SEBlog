package com.other.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("getOtherInfo")
public interface OtherUserInfoService extends RemoteService{
	public User getOtherUserInfo(int otherId);
}
