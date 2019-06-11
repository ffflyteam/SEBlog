package com.manager.index.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.manager.index.client.DeleteFeedBackService;
import com.manager.index.shared.ManagerIndexDAO;

@SuppressWarnings("serial")
public class DeleteFeedBackServiceImpl extends RemoteServiceServlet implements DeleteFeedBackService{

	@Override
	public int deleteFeedBack(int feedBackId) {
		int res = ManagerIndexDAO.instance.deleteFeedBack(feedBackId);
		return res;
	}

}
