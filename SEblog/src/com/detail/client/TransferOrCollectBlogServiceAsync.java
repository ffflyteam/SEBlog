package com.detail.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TransferOrCollectBlogServiceAsync {
	public void transferOrCollectBlog(int blogId, int type, int flag, AsyncCallback<Integer> callback);
}
