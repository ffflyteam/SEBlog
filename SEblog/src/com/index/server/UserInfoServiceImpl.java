package com.index.server;

import com.DAO.User;
import com.DAO.UserDAO;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.user.info.client.UserInfoService;

@SuppressWarnings("serial")
@RemoteServiceRelativePath("user_info")
public class UserInfoServiceImpl extends RemoteServiceServlet implements UserInfoService{

	@Override
	public User getUserInfo() {
		int accountId = Integer.parseInt((String) this.getThreadLocalRequest().getSession().getAttribute("accountId"));
		User user = UserDAO.instance.getUserInfo(accountId);
		return user;
	}

}
