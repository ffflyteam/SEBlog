package com.other.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.other.client.User;
import com.other.client.UserInfoService;
import com.other.shared.UserDAO;

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
