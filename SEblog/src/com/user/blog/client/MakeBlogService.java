package com.user.blog.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("makeBlog")
public interface MakeBlogService extends RemoteService{
	public int makeBlog(String title, String content, int type);
}
