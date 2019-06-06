package com.detail.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("transferOrCollect")
public interface TransferOrCollectBlogService extends RemoteService{
	public int transferOrCollectBlog(int blogId, int type, int flag);
}
