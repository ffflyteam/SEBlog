package com.user.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("deleteSelfBlog")
public interface DeleteSelfBlogService extends RemoteService{
	public int deleteSelfBlog(int blogId);
}
