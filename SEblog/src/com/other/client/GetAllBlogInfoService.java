package com.other.client;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("getOtherAllBlog")
public interface GetAllBlogInfoService extends RemoteService{
	/*
	 * 返回的Map的key：0为自己博客，1为转载的博客，2为收藏的博客
	 */
	public Map<Integer, List<Blog>> getAllBlog(int otherId);
}
