package com.other.index.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GetAllBlogInfoServiceAsync {
	public void getAllBlog(int otherId, AsyncCallback<List<Blog>> callback);
}
