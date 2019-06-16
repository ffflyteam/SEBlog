package com.manager.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.manager.client.User;
import com.manager.client.UserInfoService;
import com.manager.shared.ManagerIndexDAO;

@SuppressWarnings("serial")
public class UserInfoServiceImpl extends RemoteServiceServlet implements UserInfoService{

	@Override
	public User getUserInfo(int accountId) {
		User user = ManagerIndexDAO.instance.getUserInfo(accountId);
		return user;
	}

}
