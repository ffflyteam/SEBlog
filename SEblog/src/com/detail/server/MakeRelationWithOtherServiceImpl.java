package com.detail.server;

import com.DAO.UserDAO;
import com.detail.client.MakeRelationWithOtherService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
@RemoteServiceRelativePath("make_relation")
public class MakeRelationWithOtherServiceImpl extends RemoteServiceServlet implements MakeRelationWithOtherService{

	/*
	 * 关注他人
	 * param(被关在人Id,类型(默认1),标志位：0为发生关系，1为取消关系)
	 */
	@Override
	public int makeRelation(int otherId, int type, int flag) {
		int res;
		int accountId = Integer.parseInt((String) this.getThreadLocalRequest().getSession().getAttribute("accountId"));
		if(flag == 0) {
			res = UserDAO.instance.happenRelation(accountId, otherId, type);
		} else {
			res = UserDAO.instance.cancleRelation(accountId, otherId, type);
		}
		return res;
	}

}
