package com.other.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RelationStatusServiceAsync {
	public void getRelationstatus(int otherId, AsyncCallback<Boolean> callback);
}
