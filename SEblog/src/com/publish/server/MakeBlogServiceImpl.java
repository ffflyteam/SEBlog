package com.publish.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.publish.client.MakeBlogService;
import com.publish.shared.UserDAO;

@SuppressWarnings("serial")
public class MakeBlogServiceImpl extends RemoteServiceServlet implements MakeBlogService{

	@Override
	public int makeBlog(String title, String content, int type) {
		int accountId = Integer.parseInt((String) this.getThreadLocalRequest().getSession().getAttribute("accountId"));
		int res = UserDAO.instance.makeBlog(accountId, title, content, type);
		return res;
	}

}
