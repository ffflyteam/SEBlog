package com.detail.client;

import com.detail.client.Blog;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DetailServiceAsync {
	void getDetail(int blogId, AsyncCallback<Blog> callback);
}
