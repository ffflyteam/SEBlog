package com.publish.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.publish.client.User;
import com.publish.client.UserInfoService;
import com.publish.shared.UserDAO;

@SuppressWarnings("serial")
public class UserInfoServiceImpl extends RemoteServiceServlet implements UserInfoService {

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
