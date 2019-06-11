package com.other.index.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.other.index.client.User;
import com.other.index.client.UserInfoService;
import com.other.index.shared.UserOtherDAO;

@SuppressWarnings("serial")
public class UserInfoServiceImpl extends RemoteServiceServlet implements UserInfoService{

	@Override
	public User getUserInfo(int otherId) {
		User user = UserOtherDAO.instance.getUserInfo(otherId);
		return user;
	}

}
