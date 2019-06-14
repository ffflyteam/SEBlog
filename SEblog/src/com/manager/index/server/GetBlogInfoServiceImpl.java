package com.manager.index.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.manager.index.client.Blog;
import com.manager.index.client.GetBlogInfoService;
import com.manager.index.shared.ManagerBlogDAO;

@SuppressWarnings("serial")
public class GetBlogInfoServiceImpl extends RemoteServiceServlet implements GetBlogInfoService{

	@Override
	public Blog getBlog(int blogId) {
		Blog blog = ManagerBlogDAO.instance.getBlogById(blogId);
		return blog;
	}

}
