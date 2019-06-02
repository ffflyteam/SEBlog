package com.detail.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CollectAndRelationServiceAsync {
	public void getStatus(int accountId, int blogId, int otherId, AsyncCallback<boolean[]> callback);
}
