package com.publish.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LogoutServiceAsync {
	public void logout(AsyncCallback<Boolean> callback);
}
