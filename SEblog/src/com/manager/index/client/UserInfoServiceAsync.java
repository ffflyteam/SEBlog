package com.manager.index.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserInfoServiceAsync {
	void getUserInfo(AsyncCallback<User> callback);
}
