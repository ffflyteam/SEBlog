package com.index.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface InfoServiceAsync {
	void infoServer(String input,AsyncCallback<BlogInfo> callback) throws IllegalArgumentException;
}
