package com.manager.index.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("getBlogInfo")
public interface GetBlogInfoService extends RemoteService{
	public Blog getBlog(int blogId);
}
