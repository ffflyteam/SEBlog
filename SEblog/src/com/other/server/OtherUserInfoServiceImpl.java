package com.other.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.other.client.OtherUserInfoService;
import com.other.client.User;
import com.other.shared.UserOtherDAO;

@SuppressWarnings("serial")
public class OtherUserInfoServiceImpl extends RemoteServiceServlet implements OtherUserInfoService{

	public User getOtherUserInfo(int otherId) {
		User user = UserOtherDAO.instance.getUserInfo(otherId);
		return user;
	}
}
