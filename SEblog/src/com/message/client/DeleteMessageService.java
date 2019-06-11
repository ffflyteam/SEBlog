package com.message.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("deleteMessage")
public interface DeleteMessageService extends RemoteService{
	/*
	 * param(消息Id)
	 */
	public int deleteMessage(int messageId);
}
