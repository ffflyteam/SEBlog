package com.other.index.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("getOtherAllBlog")
public interface GetAllBlogInfoService extends RemoteService{
	public List<Blog> getAllBlog(int otherId);
}
