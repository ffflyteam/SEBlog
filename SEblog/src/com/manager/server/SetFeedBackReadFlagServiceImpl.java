package com.manager.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.manager.client.SetFeedBackReadFlagService;
import com.manager.shared.ManagerIndexDAO;

@SuppressWarnings("serial")
public class SetFeedBackReadFlagServiceImpl extends RemoteServiceServlet implements SetFeedBackReadFlagService{

	@Override
	public int setReadFlag(int feedBackId) {
		int res = ManagerIndexDAO.instance.setReadFlag(feedBackId);
		return res;
	}

}
