package com.user.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DeleteSelfBlogServiceAsync {
	public void deleteSelfBlog(int blogId, AsyncCallback<Integer> callback);
}
