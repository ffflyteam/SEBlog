package com.user.relation.server;

import java.util.List;
import java.util.Map;

import com.DAO.User;
import com.DAO.UserDAO;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.user.relation.client.UserRelationService;

@SuppressWarnings("serial")
@RemoteServiceRelativePath("user_relation")
public class UserRelationServiceImpl extends RemoteServiceServlet implements UserRelationService{

	@Override
	public Map<User, Integer> getAllRelatedUser(int accountId) {
		Map<User, Integer> allRelatedUsers = UserDAO.instance.getAllRelationWithOthers(accountId);
		return allRelatedUsers;
	}

}
