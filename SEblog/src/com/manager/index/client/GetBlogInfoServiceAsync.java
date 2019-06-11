package com.manager.index.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GetBlogInfoServiceAsync {
	public void getBlog(int blogId, AsyncCallback<Blog> callback);
}
