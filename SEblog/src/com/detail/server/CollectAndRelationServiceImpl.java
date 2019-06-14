package com.detail.server;

import com.detail.client.CollectAndRelationService;
import com.detail.shared.UserDetailDAO;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class CollectAndRelationServiceImpl extends RemoteServiceServlet implements CollectAndRelationService{
	
	/*
	  * 
	  * 收藏博客的转态和与该博主的关系
	  * param(博客Id,博主Id)
	  * 
	  */
	 @Override
	 public boolean[] getStatus(int blogId, int otherId) {
	  boolean[] status = new boolean[3];
	  int accountId = Integer.parseInt((String) this.getThreadLocalRequest().getSession().getAttribute("accountId"));
	  status[0] = UserDetailDAO.instance.isCollected(accountId, blogId);
	  status[1] = UserDetailDAO.instance.isRelated(accountId, otherId);
	  status[2] = UserDetailDAO.instance.isTransfered(accountId, blogId);
	  return status;
	 }
}
