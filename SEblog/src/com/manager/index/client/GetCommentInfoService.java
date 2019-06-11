package com.manager.index.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("getCommentInfo")
public interface GetCommentInfoService extends RemoteService{
	public Comment getComment(int commentId);
}
