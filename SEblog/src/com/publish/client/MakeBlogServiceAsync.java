package com.publish.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MakeBlogServiceAsync {
	public void makeBlog(String title, String content, int type, AsyncCallback<Integer> callback);
}
