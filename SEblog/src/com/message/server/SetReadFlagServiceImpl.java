package com.message.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.message.client.SetReadFlagService;
import com.message.shared.UserMessageDAO;

@SuppressWarnings("serial")
public class SetReadFlagServiceImpl extends RemoteServiceServlet implements SetReadFlagService{

	@Override
	public int setReadFlag(int messageId) {
		int rs = UserMessageDAO.instance.setReadFlag(messageId);
		return rs;
	}

}
