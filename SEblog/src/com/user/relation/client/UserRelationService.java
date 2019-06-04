package com.user.relation.client;

import java.util.Map;

import com.DAO.User;
import com.google.gwt.user.client.rpc.RemoteService;

public interface UserRelationService extends RemoteService{
	public Map<User, Integer> getAllRelatedUser();
}
