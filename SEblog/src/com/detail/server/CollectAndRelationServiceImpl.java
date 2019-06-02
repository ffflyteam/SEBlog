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
	 * �Ƿ��ע�������Ƿ��ղز��ͣ�������־λ
	 * 
	 */
	@Override
	public boolean[] getStatus(int accountId, int blogId, int otherId) {
		boolean[] status = new boolean[2];
		status[0] = UserDAO.instance.isCollected(accountId, blogId);
		status[1] = UserDAO.instance.isRelated(accountId, otherId);
		return status;
	}

}
