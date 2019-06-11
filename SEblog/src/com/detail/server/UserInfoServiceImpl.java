package com.detail.server;

import com.detail.client.User;
import com.detail.client.UserInfoService;
import com.detail.shared.UserDetailDAO;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class UserInfoServiceImpl extends RemoteServiceServlet implements UserInfoService{

	public User getUserInfo() {
		int	accountId = Integer.parseInt((String) this.getThreadLocalRequest().getSession().getAttribute("accountId"));
		User user = UserDetailDAO.instance.getUserInfo(accountId);
		return user;
	}

}
