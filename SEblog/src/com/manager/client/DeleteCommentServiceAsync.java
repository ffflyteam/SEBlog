package com.manager.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DeleteCommentServiceAsync {
	public void deleteComment(int commentId, AsyncCallback<Integer> callback);
}
