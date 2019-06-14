package com.other.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.other.client.Blog;
import com.other.client.GetAllBlogInfoService;
import com.other.shared.BlogOtherDAO;

@SuppressWarnings("serial")
public class GetAllBlogInfoServiceImpl extends RemoteServiceServlet implements GetAllBlogInfoService{

	@Override
	public Map<Integer, List<Blog>> getAllBlog(int otherId) {
		List<Blog> selfBlogList = BlogOtherDAO.instance.getUserAllBlogInfo(otherId);
		List<Blog> transferBlogList = BlogOtherDAO.instance.getAllTransferBlog(otherId);
		List<Blog> collectBlogList = BlogOtherDAO.instance.getAllCollectBlog(otherId);
		Map<Integer, List<Blog>> result = new HashMap<>(3);
		result.put(0, selfBlogList);
		result.put(1, transferBlogList);
		result.put(2, collectBlogList);
		return result;
	}

}
