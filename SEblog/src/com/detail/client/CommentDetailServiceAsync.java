package com.detail.client;

import java.util.List;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CommentDetailServiceAsync {
	void getCommentDetail(int objectId, AsyncCallback<List<Comment>> callback) throws IllegalArgumentException;
}
