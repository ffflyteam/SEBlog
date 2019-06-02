package com.detail.client;

import com.detail.client.Blog;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("detail")
public interface DetailService extends RemoteService{
	public Blog getDetail(int blogId);
}
