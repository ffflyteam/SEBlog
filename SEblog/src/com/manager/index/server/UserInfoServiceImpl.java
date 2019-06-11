package com.manager.index.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.manager.index.client.User;
import com.manager.index.client.UserInfoService;
import com.manager.index.shared.UserDAO;

@SuppressWarnings("serial")
public class UserInfoServiceImpl extends RemoteServiceServlet implements UserInfoService{

	@Override
	public User getUserInfo() {
		int accountId = Integer.parseInt((String) this.getThreadLocalRequest().getSession().getAttribute("accountId"));
		User user = UserDAO.instance.getUserInfo(accountId);
		return user;
	}

}
