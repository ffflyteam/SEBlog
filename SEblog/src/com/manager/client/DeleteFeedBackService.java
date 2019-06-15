package com.manager.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("deleteFeedBack")
public interface DeleteFeedBackService extends RemoteService{
	public int deleteFeedBack(int feedBackId);
}
