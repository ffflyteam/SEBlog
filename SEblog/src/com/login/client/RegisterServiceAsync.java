package com.login.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RegisterServiceAsync {
	void registerServer(String input, AsyncCallback<Integer> callback);
}
