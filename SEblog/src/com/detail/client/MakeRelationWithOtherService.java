package com.detail.client;

import com.google.gwt.user.client.rpc.RemoteService;

public interface MakeRelationWithOtherService extends RemoteService{
	public int makeRelation(int accountId, int otherId, int type, int flag);
}
