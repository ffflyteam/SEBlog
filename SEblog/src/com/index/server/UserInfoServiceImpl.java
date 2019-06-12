package com.index.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.index.client.User;
import com.index.client.UserInfoService;
import com.index.shared.UserIndexDAO;

@SuppressWarnings("serial")
public class UserInfoServiceImpl extends RemoteServiceServlet implements UserInfoService{

	@Override
	public User getUserInfo() {
		Object id;
		if((id = this.getThreadLocalRequest().getSession().getAttribute("accountId")) == null) {
			return null;
		}
		int accountId = Integer.parseInt((String) id);
		User user = UserIndexDAO.instance.getUserInfo(accountId);
		return user;
	}

}
