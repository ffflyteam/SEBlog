package com.detail.server;

import com.detail.client.User;
import com.detail.client.UserInfoService;
import com.detail.shared.UserDetailDAO;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class UserInfoServiceImpl extends RemoteServiceServlet implements UserInfoService{

	public User getUserInfo() {
		Object id;
		if((id = this.getThreadLocalRequest().getSession().getAttribute("accountId")) == null) {
			return null;
		}
		int accountId = Integer.parseInt((String) id);
		User user = UserDetailDAO.instance.getUserInfo(accountId);
		return user;
	}

}
