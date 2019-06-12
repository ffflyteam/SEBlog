package com.other.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface OtherUserInfoServiceAsync {
	void getOtherUserInfo(int otherId, AsyncCallback<User> callback);
}
