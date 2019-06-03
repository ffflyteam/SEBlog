package com.detail.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("relation")
public interface CollectAndRelationService extends RemoteService{
	public boolean[] getStatus(int accountId, int blogId, int otherId) throws IllegalArgumentException ;
}
