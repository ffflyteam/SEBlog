package com.user.info.client;

import com.DAO.User;
import com.google.gwt.user.client.rpc.RemoteService;

public interface UserInfoService extends RemoteService{
	public User getUserInfo();
}
