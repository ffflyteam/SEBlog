package com.manager.index.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("deleteBlog")
public interface DeleteBlogService extends RemoteService{
	public int deleteBlog(int blogId);
}
