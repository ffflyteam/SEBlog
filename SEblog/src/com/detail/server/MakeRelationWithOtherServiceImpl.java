package com.detail.server;

import com.detail.client.MakeRelationWithOtherService;
import com.detail.shared.UserDetailDAO;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class MakeRelationWithOtherServiceImpl extends RemoteServiceServlet implements MakeRelationWithOtherService{

	@Override
	public int makeRelation(int otherId, int type, int flag) {
		int res;
		int accountId = Integer.parseInt((String) this.getThreadLocalRequest().getSession().getAttribute("accountId"));
		if(flag == 0) {
			res = UserDetailDAO.instance.happenRelation(accountId, otherId, type);
		} else {
			res = UserDetailDAO.instance.cancleRelation(accountId, otherId, type);
		}
		return res;
	}

}
