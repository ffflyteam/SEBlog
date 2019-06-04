package com.detail.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("focuse")
public interface MakeRelationWithOtherService extends RemoteService{
	public int makeRelation(int otherId, int type, int flag);
}
