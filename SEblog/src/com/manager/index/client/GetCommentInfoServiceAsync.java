package com.manager.index.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GetCommentInfoServiceAsync {
	public void getComment(int commentId, AsyncCallback<Comment> callback);
}
