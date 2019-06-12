package com.user.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.user.client.User;
import com.user.client.UserInfoService;
import com.user.shared.UserDAO;

@SuppressWarnings("serial")
public class UserInfoServiceImpl extends RemoteServiceServlet implements UserInfoService {

	@Override
	public User getUserInfo() {
		Object id;
		if((id = this.getThreadLocalRequest().getSession().getAttribute("accountId")) == null) {
			return null;
		}
		int accountId = Integer.parseInt((String) id);
		User user = UserDAO.instance.getUserInfo(accountId);
		return user;
	}

}
