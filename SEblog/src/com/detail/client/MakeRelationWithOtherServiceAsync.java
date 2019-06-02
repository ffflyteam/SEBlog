package com.detail.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MakeRelationWithOtherServiceAsync {
	public void makeRelation(int accountId, int otherId, int type, int flag, AsyncCallback<Integer> callback);
}
