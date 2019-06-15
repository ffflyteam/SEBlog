package com.manager.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.manager.client.DeleteBlogService;
import com.manager.shared.ManagerIndexDAO;

@SuppressWarnings("serial")
public class DeleteBlogServiceImpl extends RemoteServiceServlet implements DeleteBlogService{

	@Override
	public int deleteBlog(int blogId) {
		int res = ManagerIndexDAO.instance.deleteBlog(blogId);
		return res;
	}

}
