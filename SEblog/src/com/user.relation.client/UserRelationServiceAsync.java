package com.user.relation.client;

import java.util.List;
import java.util.Map;

import com.DAO.User;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserRelationServiceAsync {
	public void getAllRelatedUser(int accountId, AsyncCallback<Map<User, Integer>> callback);
}
