package com.detail.server;

import com.DAO.UserDAO;
import com.detail.client.CollectAndRelationService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
@RemoteServiceRelativePath("user_blog_status")
public class CollectAndRelationServiceImpl extends RemoteServiceServlet implements CollectAndRelationService{
	
	/*
	 * 
	 * 收藏博客的转态和与该博主的关系
	 * 
	 */
	@Override
	public boolean[] getStatus(int blogId, int otherId) {
		boolean[] status = new boolean[2];
		int accountId = Integer.parseInt((String) this.getThreadLocalRequest().getSession().getAttribute("accountId"));
		status[0] = UserDAO.instance.isCollected(accountId, blogId);
		status[1] = UserDAO.instance.isRelated(accountId, otherId);
		return status;
	}

}
