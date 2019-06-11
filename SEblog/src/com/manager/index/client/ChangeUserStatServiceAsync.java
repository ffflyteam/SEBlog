package com.manager.index.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ChangeUserStatServiceAsync {
	public void changeUserStat(int userId, int stat, AsyncCallback<Integer> callback);
}
