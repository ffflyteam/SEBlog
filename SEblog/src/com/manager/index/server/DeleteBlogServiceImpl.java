package com.manager.index.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.manager.index.client.DeleteBlogService;
import com.manager.index.shared.ManagerIndexDAO;

@SuppressWarnings("serial")
public class DeleteBlogServiceImpl extends RemoteServiceServlet implements DeleteBlogService{

	@Override
	public int deleteBlog(int blogId) {
		int res = ManagerIndexDAO.instance.deleteBlog(blogId);
		return res;
	}

}
