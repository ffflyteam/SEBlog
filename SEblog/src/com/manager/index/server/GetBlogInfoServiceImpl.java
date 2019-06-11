package com.manager.index.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.manager.index.client.Blog;
import com.manager.index.client.GetBlogInfoService;
import com.manager.index.shared.BlogDAO;

@SuppressWarnings("serial")
public class GetBlogInfoServiceImpl extends RemoteServiceServlet implements GetBlogInfoService{

	@Override
	public Blog getBlog(int blogId) {
		Blog blog = BlogDAO.instance.getBlogById(blogId);
		return blog;
	}

}
