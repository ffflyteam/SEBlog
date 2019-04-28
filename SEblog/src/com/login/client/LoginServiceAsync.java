package com.login.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface LoginServiceAsync {
	void loginServer(String input, AsyncCallback<String> callback) throws IllegalArgumentException;
}
