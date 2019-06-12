package com.other.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.other.client.Blog;
import com.other.client.GetAllBlogInfoService;
import com.other.shared.BlogOtherDAO;

@SuppressWarnings("serial")
public class GetAllBlogInfoServiceImpl extends RemoteServiceServlet implements GetAllBlogInfoService{

	@Override
	public List<Blog> getAllBlog(int otherId) {
		List<Blog> result = BlogOtherDAO.instance.getUserAllBlogInfo(otherId);
		return result;
	}

}
