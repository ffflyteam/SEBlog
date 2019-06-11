package com.other.index.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.other.index.client.Blog;
import com.other.index.client.GetAllBlogInfoService;
import com.other.index.shared.BlogOtherDAO;

@SuppressWarnings("serial")
public class GetAllBlogInfoServiceImpl extends RemoteServiceServlet implements GetAllBlogInfoService{

	@Override
	public List<Blog> getAllBlog(int otherId) {
		List<Blog> result = BlogOtherDAO.instance.getUserAllBlogInfo(otherId);
		return result;
	}

}
