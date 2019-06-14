package com.message.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.message.client.User;
import com.message.client.UserInfoService;
import com.message.shared.UserMessageDAO;

@SuppressWarnings("serial")
public class UserInfoServiceImpl extends RemoteServiceServlet implements UserInfoService {

	public User getUserInfo() {
		Object id;
		if((id = this.getThreadLocalRequest().getSession().getAttribute("accountId")) == null) {
			return null;
		}
		int accountId = Integer.parseInt((String) id);
		User user = UserMessageDAO.instance.getUserInfo(accountId);
		return user;
	}

}
