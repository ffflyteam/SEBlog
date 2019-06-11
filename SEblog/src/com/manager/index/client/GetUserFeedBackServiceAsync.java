package com.manager.index.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GetUserFeedBackServiceAsync {
	public void getAllUserFeedBack(AsyncCallback<List<FeedBack>> callback);
}
