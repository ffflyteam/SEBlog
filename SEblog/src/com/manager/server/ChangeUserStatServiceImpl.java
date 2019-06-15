package com.manager.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.manager.client.ChangeUserStatService;
import com.manager.shared.ManagerIndexDAO;

@SuppressWarnings("serial")
public class ChangeUserStatServiceImpl extends RemoteServiceServlet implements ChangeUserStatService{

	@Override
	public int changeUserStat(int userId, int stat) {
		int res = ManagerIndexDAO.instance.changeUserStat(userId, stat);
		return res;
	}

}
