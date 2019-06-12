package com.user.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("UserBlog")
public interface UserBlogService extends RemoteService{
	public List<Blog> getAllBlog(int type);
}
