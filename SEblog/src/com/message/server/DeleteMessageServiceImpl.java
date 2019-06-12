package com.message.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.message.client.DeleteMessageService;
import com.message.shared.UserMessageDAO;

@SuppressWarnings("serial")
public class DeleteMessageServiceImpl extends RemoteServiceServlet implements DeleteMessageService{

	@Override
	public int deleteMessage(int messageId) {
		int rs = UserMessageDAO.instance.deleteMessage(messageId);
		return rs;
	}

}
