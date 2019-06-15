package com.manager.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.manager.client.Blog;
import com.manager.client.GetBlogInfoService;
import com.manager.shared.ManagerBlogDAO;

@SuppressWarnings("serial")
public class GetBlogInfoServiceImpl extends RemoteServiceServlet implements GetBlogInfoService{

	@Override
	public Blog getBlog(int blogId) {
		Blog blog = ManagerBlogDAO.instance.getBlogById(blogId);
		return blog;
	}

}
