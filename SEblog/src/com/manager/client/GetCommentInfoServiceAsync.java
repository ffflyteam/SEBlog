package com.manager.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GetCommentInfoServiceAsync {
	public void getComment(int commentId, AsyncCallback<Comment> callback);
}
