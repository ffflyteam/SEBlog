package com.manager.index.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.manager.index.client.FeedBack;
import com.manager.index.client.GetUserFeedBackService;
import com.manager.index.shared.ManagerIndexDAO;

@SuppressWarnings("serial")
public class GetUserFeedBackServiceImpl extends RemoteServiceServlet implements GetUserFeedBackService{

	@Override
	public List<FeedBack> getAllUserFeedBack() {
		List<FeedBack> result = ManagerIndexDAO.instance.getAllUserFeedBack();
		return result;
	}

}
