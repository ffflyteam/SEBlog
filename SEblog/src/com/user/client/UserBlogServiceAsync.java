package com.user.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserBlogServiceAsync {
	public void getAllBlog(int type, AsyncCallback<List<Blog>> callback);
}
