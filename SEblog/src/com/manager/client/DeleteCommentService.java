package com.manager.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("deleteComment")
public interface DeleteCommentService extends RemoteService{
	public int deleteComment(int commentId);
}
