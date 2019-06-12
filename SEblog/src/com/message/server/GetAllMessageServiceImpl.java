package com.message.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.message.client.GetAllMessageService;
import com.message.client.Message;
import com.message.shared.UserMessageDAO;

@SuppressWarnings("serial")
public class GetAllMessageServiceImpl extends RemoteServiceServlet implements GetAllMessageService{

	@Override
	public List<Message> getAllMessage() {
		int accountId = Integer.parseInt((String) this.getThreadLocalRequest().getSession().getAttribute("accountId"));
		List<Message> result = UserMessageDAO.instance.getAllMessage(accountId);
		return result;
	}

}
