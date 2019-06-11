package com.detail.server;

import com.detail.client.MakeFeedBackService;
import com.detail.shared.UserDetailDAO;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class MakeFeedBackServiceImpl extends RemoteServiceServlet implements MakeFeedBackService{

	@Override
	public int makeFeedBack(int objectId, int feedBackType) {
		int accountId = Integer.parseInt((String) this.getThreadLocalRequest().getSession().getAttribute("accountId"));
		int res = UserDetailDAO.instance.makeFeedBack(objectId, accountId, feedBackType);
		return res;
	}

}
