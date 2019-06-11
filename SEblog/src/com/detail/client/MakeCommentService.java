package com.detail.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("makeComment")
public interface MakeCommentService extends RemoteService{
	public int makeComment(int objectId, String content) throws IllegalArgumentException;
}
