package com.manager.index.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("changeUserStat")
public interface ChangeUserStatService extends RemoteService{
	public int changeUserStat(int userId, int stat);
}
