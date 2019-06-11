package com.manager.index.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.manager.index.client.SetFeedBackReadFlagService;
import com.manager.index.shared.ManagerIndexDAO;

@SuppressWarnings("serial")
public class SetFeedBackReadFlagServiceImpl extends RemoteServiceServlet implements SetFeedBackReadFlagService{

	@Override
	public int setReadFlag(int feedBackId) {
		int res = ManagerIndexDAO.instance.setReadFlag(feedBackId);
		return res;
	}

}
