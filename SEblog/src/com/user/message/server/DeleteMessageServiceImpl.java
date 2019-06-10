package com.user.message.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.user.message.client.DeleteMessageService;
import com.user.message.shared.UserMessageDAO;

@SuppressWarnings("serial")
public class DeleteMessageServiceImpl extends RemoteServiceServlet implements DeleteMessageService{

	@Override
	public int deleteMessage(int messageId) {
		int rs = UserMessageDAO.instance.deleteMessage(messageId);
		return rs;
	}

}
