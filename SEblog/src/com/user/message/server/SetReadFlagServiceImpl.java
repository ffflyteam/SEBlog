package com.user.message.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.user.message.client.SetReadFlagService;
import com.user.message.shared.UserMessageDAO;

@SuppressWarnings("serial")
public class SetReadFlagServiceImpl extends RemoteServiceServlet implements SetReadFlagService{

	@Override
	public int setReadFlag(int messageId) {
		int rs = UserMessageDAO.instance.setReadFlag(messageId);
		return rs;
	}

}
