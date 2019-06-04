package com.detail.server;

import com.detail.client.Blog;
import com.detail.shared.BlogDetailDAO;
import com.detail.client.DetailService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
@RemoteServiceRelativePath("detail")
public class DetailServiceImpl extends RemoteServiceServlet implements DetailService{

	@Override
	public Blog getDetail(int blogId) {
		Blog blog = BlogDetailDAO.instance.getBlogWithIncreaseReadNum(blogId);
		return blog;
	}
}