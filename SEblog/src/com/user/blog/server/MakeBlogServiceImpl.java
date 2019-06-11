package com.user.blog.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.user.blog.client.MakeBlogService;
import com.user.blog.shared.UserDAO;

@SuppressWarnings("serial")
public class MakeBlogServiceImpl extends RemoteServiceServlet implements MakeBlogService{

	@Override
	public int makeBlog(String title, String content, int type) {
		int accountId = Integer.parseInt((String) this.getThreadLocalRequest().getSession().getAttribute("accountId"));
		int res = UserDAO.instance.makeBlog(accountId, title, content, type);
		return res;
	}

}
