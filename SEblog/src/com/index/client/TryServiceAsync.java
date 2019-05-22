package com.index.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TryServiceAsync {
	void tryServer(String input,AsyncCallback<String> callback) throws IllegalArgumentException;
}
