package com.other.client;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GetAllBlogInfoServiceAsync {
	public void getAllBlog(int otherId, AsyncCallback<Map<Integer, List<Blog>>> callback);
}
