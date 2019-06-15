package com.manager.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.manager.client.FeedBack;
import com.manager.client.GetUserFeedBackService;
import com.manager.shared.ManagerIndexDAO;

@SuppressWarnings("serial")
public class GetUserFeedBackServiceImpl extends RemoteServiceServlet implements GetUserFeedBackService{

	@Override
	public List<FeedBack> getAllUserFeedBack() {
		List<FeedBack> result = ManagerIndexDAO.instance.getAllUserFeedBack();
		return result;
	}

}
