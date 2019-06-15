package com.manager.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DeleteFeedBackServiceAsync {
	public void deleteFeedBack(int feedBackId, AsyncCallback<Integer> callback);
}
