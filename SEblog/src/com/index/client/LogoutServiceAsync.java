package com.index.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LogoutServiceAsync {
	public void logout(AsyncCallback<Boolean> callback) throws IllegalArgumentException;
}
