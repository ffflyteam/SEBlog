package com.manager.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.manager.client.DeleteFeedBackService;
import com.manager.shared.ManagerIndexDAO;

@SuppressWarnings("serial")
public class DeleteFeedBackServiceImpl extends RemoteServiceServlet implements DeleteFeedBackService{

	@Override
	public int deleteFeedBack(int feedBackId) {
		int res = ManagerIndexDAO.instance.deleteFeedBack(feedBackId);
		return res;
	}

}
