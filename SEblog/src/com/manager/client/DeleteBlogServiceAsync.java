package com.manager.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DeleteBlogServiceAsync {
	public void deleteBlog(int blogId, AsyncCallback<Integer> callback);
}
