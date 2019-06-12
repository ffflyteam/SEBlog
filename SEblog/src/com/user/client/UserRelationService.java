package com.user.client;

import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("UserRelation")
public interface UserRelationService extends RemoteService{
	public Map<User, Integer> getAllRelatedUser();
}
