package com.other.index.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserInfoServiceAsync {
	void getUserInfo(int otherId, AsyncCallback<User> callback);
}
