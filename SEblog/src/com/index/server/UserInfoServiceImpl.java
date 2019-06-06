package com.index.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.index.client.User;
import com.index.client.UserInfoService;
import com.index.shared.UserIndexDAO;

@SuppressWarnings("serial")
public class UserInfoServiceImpl extends RemoteServiceServlet implements UserInfoService{

	@Override
	public User getUserInfo() {
		int accountId = Integer.parseInt((String) this.getThreadLocalRequest().getSession().getAttribute("accountId"));
		User user = UserIndexDAO.instance.getUserInfo(accountId);
		return user;
	}

}
