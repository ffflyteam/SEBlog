package com.user.message.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.user.message.client.GetAllMessageService;
import com.user.message.client.Message;
import com.user.message.shared.UserMessageDAO;

@SuppressWarnings("serial")
public class GetAllMessageServiceImpl extends RemoteServiceServlet implements GetAllMessageService{

	@Override
	public List<Message> getAllMessage() {
		int accountId = Integer.parseInt((String) this.getThreadLocalRequest().getSession().getAttribute("accountId"));
		List<Message> result = UserMessageDAO.instance.getAllMessage(accountId);
		return result;
	}

}
