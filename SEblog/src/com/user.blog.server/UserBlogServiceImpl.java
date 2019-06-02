package com.user.blog.server;

import java.util.List;

import com.DAO.Blog;
import com.DAO.UserDAO;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.user.blog.client.UserBlogService;

@SuppressWarnings("serial")
@RemoteServiceRelativePath("user_blog")
public class UserBlogServiceImpl extends RemoteServiceServlet implements UserBlogService{

	@Override
	public List<Blog> getAllBlog(int accountId, int type) {
		if(type == 0) {
			return UserDAO.instance.getAllBlog(accountId);
		} else if (type == 1) {
			return UserDAO.instance.getAllCollectBlog(accountId);
		} else {
			return UserDAO.instance.getAllTransferBlog(accountId);
		}
	}

}
