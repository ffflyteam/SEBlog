package com.manager.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserInfoServiceAsync {
	void getUserInfo(int accountId, AsyncCallback<User> callback);
}
