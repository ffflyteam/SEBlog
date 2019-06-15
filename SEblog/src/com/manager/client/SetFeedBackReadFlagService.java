package com.manager.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("setReadFlag")
public interface SetFeedBackReadFlagService extends RemoteService{
	public int setReadFlag(int feedBackId);
}
