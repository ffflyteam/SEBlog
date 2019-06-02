package com.detail.client;

import com.google.gwt.user.client.rpc.RemoteService;

public interface TransferOrCollectBlogService extends RemoteService{
	public int transferOrCollectBlog(int accountId, int blogId, int type, int flag);
}
