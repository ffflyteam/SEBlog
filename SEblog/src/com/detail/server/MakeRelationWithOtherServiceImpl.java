package com.detail.server;

import com.DAO.UserDAO;
import com.detail.client.MakeRelationWithOtherService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
@RemoteServiceRelativePath("make_relation")
public class MakeRelationWithOtherServiceImpl extends RemoteServiceServlet implements MakeRelationWithOtherService{

	@Override
	public int makeRelation(int accountId, int otherId, int type, int flag) {
		int res;
		if(flag == 0) {
			res = UserDAO.instance.happenRelation(accountId, otherId, type);
		} else {
			res = UserDAO.instance.cancleRelation(accountId, otherId, type);
		}
		return res;
	}

}
