package com.detail.client;

import com.google.gwt.user.client.rpc.RemoteService;

public interface MakeCommentService extends RemoteService{
	public int makeComment(int objectId, String content);
}
