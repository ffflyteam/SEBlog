package com.user.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MakeRelationWithOtherServiceAsync {
	public void makeRelation(int otherId, int type, int flag, AsyncCallback<Integer> callback);
}
