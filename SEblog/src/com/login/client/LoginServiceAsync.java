package com.login.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {
	void login(String input, AsyncCallback<int[]> callback) throws IllegalArgumentException;
}
