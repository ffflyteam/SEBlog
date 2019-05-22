package com.detail.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DetailServiceAsync {
	void detailServer(String input,AsyncCallback<String> callback) throws IllegalArgumentException;
}