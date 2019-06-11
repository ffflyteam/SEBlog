package com.detail.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("makeFeedBack")
public interface MakeFeedBackService extends RemoteService{
	/*
	 * param(反馈主体Id,反馈类型（见FeedBackType）);
	 */
	public int makeFeedBack(int objectId, int feedBackType);
}
