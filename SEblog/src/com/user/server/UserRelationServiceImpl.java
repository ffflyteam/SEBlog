package com.user.server;

import java.util.Map;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.user.client.User;
import com.user.client.UserRelationService;
import com.user.shared.UserDAO;

@SuppressWarnings("serial")
public class UserRelationServiceImpl extends RemoteServiceServlet implements UserRelationService{

	@Override
	public Map<User, Integer> getAllRelatedUser() {
		int accountId = Integer.parseInt((String) this.getThreadLocalRequest().getSession().getAttribute("accountId"));
		Map<User, Integer> allRelatedUsers = UserDAO.instance.getAllRelationWithOthers(accountId);
		return allRelatedUsers;
	}

}
