package com.user.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.user.client.Blog;
import com.user.client.UserBlogService;
import com.user.shared.UserDAO;

@SuppressWarnings("serial")
public class UserBlogServiceImpl extends RemoteServiceServlet implements UserBlogService{

	/*
	 * 拿指定类型的博客
	 * param(博客类型，见BlogType)
	 */
	@Override
	public List<Blog> getAllBlog(int type) {
		int accountId = Integer.parseInt((String) this.getThreadLocalRequest().getSession().getAttribute("accountId"));
		if(type == 0) {
			return UserDAO.instance.getAllBlog(accountId);
		} else if (type == 1) {
			return UserDAO.instance.getAllCollectBlog(accountId);
		} else {
			return UserDAO.instance.getAllTransferBlog(accountId);
		}
	}

}
