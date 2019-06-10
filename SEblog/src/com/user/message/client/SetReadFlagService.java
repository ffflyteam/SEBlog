package com.user.message.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("setReadFlag")
public interface SetReadFlagService extends RemoteService{
	/*
	 * param(消息Id)
	 */
	public int setReadFlag(int messageId);
}
