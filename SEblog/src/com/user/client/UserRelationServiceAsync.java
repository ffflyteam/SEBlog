package com.user.client;

import java.util.Map;

import com.DAO.User;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserRelationServiceAsync {
	public void getAllRelatedUser(AsyncCallback<Map<User, Integer>> callback);
}
