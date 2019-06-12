package com.user.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.user.client.DeleteSelfBlogService;
import com.user.shared.UserDAO;

@SuppressWarnings("serial")
public class DeleteSelfBlogServiceImpl extends RemoteServiceServlet implements DeleteSelfBlogService{

	@Override
	public int deleteSelfBlog(int blogId) {
		int accountId =  Integer.parseInt((String) this.getThreadLocalRequest().getSession().getAttribute("accountId"));
		int res = UserDAO.instance.deleteBlog(blogId, accountId);
		return res;
	}

}
