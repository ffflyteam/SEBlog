package com.detail.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;

public interface CommentDetailService extends RemoteService{
	public List<Comment> getCommentDetail(int objectId);
}
