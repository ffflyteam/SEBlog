package com.detail.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MakeCommentServiceAsync {
	public void makeComment(int objectId, int userId, String content, AsyncCallback<Integer> callback);
}