package com.index.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
public interface ArticleServiceAsync {
	void articleServer(String input,AsyncCallback<String> callback) throws IllegalArgumentException;
}
