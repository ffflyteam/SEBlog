package com.detail.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("relation")
public interface CollectAndRelationService extends RemoteService{
	/*
	 * 返回的boolean数组中，第一个为收藏标志，第二个为关注标志，第三个为转载标志
	 */
	public boolean[] getStatus(int blogId, int otherId) throws IllegalArgumentException ;
}
