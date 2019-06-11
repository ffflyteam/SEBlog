package com.manager.index.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("getAllFeedBack")
public interface GetUserFeedBackService extends RemoteService{
	public List<FeedBack> getAllUserFeedBack();
}
