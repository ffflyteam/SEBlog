package com.userBlog.client;

import java.util.List;

import com.DAO.Blog;
import com.google.gwt.user.client.rpc.RemoteService;

public interface UserBlogService extends RemoteService{
	public List<Blog> getAllBlog(int type);
}
