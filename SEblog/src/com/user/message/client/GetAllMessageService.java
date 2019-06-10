package com.user.message.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("getAllMessage")
public interface GetAllMessageService extends RemoteService{
	public List<Message> getAllMessage();
}
