package com.detail.server;

import com.detail.client.CollectAndRelationService;
import com.detail.shared.UserDetailDAO;
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
		status[0] = UserDetailDAO.instance.isCollected(accountId, blogId);
		status[1] = UserDetailDAO.instance.isRelated(accountId, otherId);
		return status;
	}

}
